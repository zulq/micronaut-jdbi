package micronaut.jdbi.models

import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class Student(
    var id: Long? = null,
    @get:NotBlank(message = "First Name is mandatory")
    @get:Size(min = 3, max = 25)
    var firstName: String = "",
    var initials: String? = null,
    @get:NotEmpty(message = "Last Name is Mandatory")
    @get:Size(min = 1, max = 25)
    var lastName: String = "",
    var birthDate: LocalDate? = null
)