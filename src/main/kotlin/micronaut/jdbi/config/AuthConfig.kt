package micronaut.jdbi.config

import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.reactivex.Flowable
import micronaut.jdbi.data.UsersService
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class AuthConfig(private val userSvc: UsersService) : AuthenticationProvider {
    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        val username = authenticationRequest.identity.toString()
        val secret = authenticationRequest.secret.toString()

        val userDetails = userSvc.authenticate(username, secret)
        if (userDetails != null) return Flowable.just(userDetails)
        return Flowable.just(AuthenticationFailed())
    }
}