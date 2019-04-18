package micronaut.jdbi.data

import micronaut.jdbi.config.DbConfig
import micronaut.jdbi.models.Student
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import javax.inject.Singleton

interface StudentsRepository {
    @SqlUpdate("insert into student (first_name, initials,last_name,birth_date) values(:firstName,:initials,:lastName,:birthDate)")
    @GetGeneratedKeys
    fun save(@BindBean student: Student): Long

    @SqlQuery("select * from student")
    @RegisterBeanMapper(Student::class)
    fun findAll(): List<Student>

    @SqlQuery("select * from student where id=:id")
    fun findById(@Bind("id") id: Long): Student?

    @SqlUpdate("delete from student where id=:id")
    fun remove(@Bind("id") id: Long)
}

@Singleton
class StudentsService {
    private val db = DbConfig.getInstance()

    fun save(student: Student): Long? {
        return try {
            db.onDemand<StudentsRepository>().save(student)
        } catch (e: Exception) {
            null
        }
    }

    fun findAll(): List<Student> {
        return db.onDemand<StudentsRepository>().findAll()
    }

    fun findById(id: Long): Student? {
        return try {
            db.onDemand<StudentsRepository>().findById(id)
        } catch (e: Exception) {
            null
        }
    }

    fun delete(id: Long): Boolean {
        return try {
            db.onDemand<StudentsRepository>().remove(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}