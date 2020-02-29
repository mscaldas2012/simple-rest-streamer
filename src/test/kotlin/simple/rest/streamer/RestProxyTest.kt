package simple.rest.streamer

import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject

@MicronautTest
internal class RestProxyTest {

    @Inject
    lateinit var restProxy: RestProxy
    @Test
    fun callRestService() {
        restProxy.callRestService("abc")
    }
}