package simple.rest.streamer

import com.fasterxml.jackson.databind.JsonNode
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestProxy(@Inject val appConfig: AppConfig) {

    fun callRestService(payload: String): String {
        var newValue = payload
//        val json = jacksonObjectMapper()
        runBlocking {
            val client = HttpClient(Apache).use { client ->
                newValue = client.post {
                    url(appConfig.serviceurl )
//                     body = payload
                    body = TextContent(payload, contentType = ContentType.Application.Json)
                }
                return@runBlocking newValue
            }
        }
        return newValue
    }

    fun callRestService(payload: JsonNode): JsonNode {
        var newValue = payload
        runBlocking {
            val client = HttpClient(Apache).use { client ->
                newValue = client.post {
                    url(appConfig.serviceurl )
                    body = payload
                }
                return@runBlocking newValue
            }
        }
        return newValue
    }
}