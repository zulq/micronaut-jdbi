package micronaut.jdbi

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("micronaut.jdbi")
                .mainClass(Application.javaClass)
                .start()
    }
}