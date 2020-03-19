package simple.rest.streamer

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestProxy(@Inject val appConfig: AppConfig) {


    fun callRestService(payload: String): String {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(appConfig.serviceurl))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

//    fun callRestService(payload: String): String {
//        var newValue = payload
////        val json = jacksonObjectMapper()
//        runBlocking {
//            HttpClient(Apache).use { client ->
//                newValue = client.post {
//                    url(appConfig.serviceurl )
////                     body = payload
//                    body = TextContent(payload, contentType = ContentType.Application.Json)
//                }
//                return@runBlocking newValue
//            }
//        }
//        return newValue
//    }
//
//    fun callRestService(payload: JsonNode): JsonNode {
//        var newValue = payload
//        runBlocking {
//            val client = HttpClient(Apache).use { client ->
//                newValue = client.post {
//                    url(appConfig.serviceurl )
//                    body = payload
//                }
//                return@runBlocking newValue
//            }
//        }
//        return newValue
//    }
}