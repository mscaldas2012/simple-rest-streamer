package simple.rest.streamer

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("simple.rest.streamer")
                .mainClass(Application.javaClass)
                .start()
    }
}