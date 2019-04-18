package micronaut.jdbi.models

data class Authorities(
    var id: Long? = null,
    var username: String = "",
    var authority: String = ""
)