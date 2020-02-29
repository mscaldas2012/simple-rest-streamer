package simple.rest.streamer


import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder
import io.micronaut.context.annotation.Factory
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * This kafka Streamer listens to a topic; calls a REST endpoint and pushes the result to another topic..
 *
 * @Created - 2020-02-21
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Factory
class GenericStreamer(val appConfig: AppConfig ) {
    companion object {
        val LOG = Logger.getLogger(GenericStreamer::javaClass.name)
    }
    @Inject
    lateinit var restProxy: RestProxy

    @Singleton
    @Named("generic-streamer")
    fun processMessage(builder: ConfiguredStreamBuilder): KStream<String, String> {
        LOG.info("AUDIT - Generic Streamer")
        LOG.fine("Reading messages from ${appConfig.incomingtopic}")
        LOG.fine("Storing validation on ${appConfig.outgoingtopic}")

        val streamerIn: KStream<String, String> = builder.stream(appConfig.incomingtopic, Consumed.with(Serdes.String(), Serdes.String()))

        val streamerOut: KStream<String, String> = streamerIn.map { k, v ->
            val minlen = v.length.coerceAtMost(50)-1
            LOG.info("key: $k; Val: ${v.substring(0..minlen)}")
            //CALL REST Service to transform in to out...
            KeyValue(k, restProxy.callRestService(v))

        }
        streamerOut.to(appConfig.outgoingtopic);
        return streamerIn
    }
}