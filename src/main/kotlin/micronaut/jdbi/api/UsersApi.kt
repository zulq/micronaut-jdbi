package micronaut.jdbi.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import micronaut.jdbi.data.UsersService
import micronaut.jdbi.models.User
import javax.validation.Valid

@Validated
@Controller("/users")
@Secured(SecurityRule.IS_ANONYMOUS)
class UsersApi(private val usersService: UsersService) {

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(@Body @Valid user: User): HttpResponse<String> {
        if (usersService.isUsernameTaken(user.username)) {
            return HttpResponse.badRequest("Username '${user.username}' already taken.")
        }
        val result = usersService.save(user)
        return if (result != null) {
            HttpResponse.created("created $result")
        } else HttpResponse.badRequest("unable to create user!")
    }
}