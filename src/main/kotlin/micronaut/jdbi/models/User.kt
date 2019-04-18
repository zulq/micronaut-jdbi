package micronaut.jdbi.models

import javax.validation.constraints.NotBlank

data class User (
    var id: Long? = null,
    @get:NotBlank
    var username: String = "",
    @get:NotBlank
    var password: String = ""
)