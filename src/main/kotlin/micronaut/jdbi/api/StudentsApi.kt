package micronaut.jdbi.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import micronaut.jdbi.data.StudentsService
import micronaut.jdbi.models.Student
import javax.validation.Valid

@Validated
@Controller("/students")
@Secured(SecurityRule.IS_AUTHENTICATED)
class StudentsApi(private val studentsSvc: StudentsService) {

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun students(): HttpResponse<List<Student>> {
        return HttpResponse.ok(studentsSvc.findAll())
    }

    /*@Get("/count")
    @Produces(MediaType.TEXT_PLAIN)
    fun studentsCount(): HttpResponse<Long> {
        return HttpResponse.ok(studentsSvc.count())
    }*/

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun student(id: Long): HttpResponse<Student> {
        val student = studentsSvc.findById(id)
        return if (student != null) {
            HttpResponse.ok(student)
        } else HttpResponse.notFound()
    }

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(@Body @Valid student: Student): HttpResponse<String> {
        val result = studentsSvc.save(student)
        return if (result != null) {
            HttpResponse.created("created $result")
        } else HttpResponse.badRequest()
    }

    @Delete("/{id}")
    fun remove(id: Long): HttpResponse<Any> {
        val student = studentsSvc.findById(id) ?: return HttpResponse.notFound()

        studentsSvc.delete(student.id!!)
        return HttpResponse.ok()
    }
}