package net.gvmtool.converter

import com.fasterxml.jackson.databind.ObjectMapper
import net.gvmtool.domain.BroadcastId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.TEXT_PLAIN
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE

class BroadcastIdMessageConverter implements HttpMessageConverter<BroadcastId> {

    static final String CONTENT_TYPE_HEADER = "Content-Type"

    @Autowired
    ObjectMapper objectMapper

    @Override
    boolean canRead(Class<?> clazz, MediaType mediaType) {
        false
    }

    @Override
    boolean canWrite(Class<?> clazz, MediaType mediaType) {
        clazz == BroadcastId
    }

    @Override
    List<MediaType> getSupportedMediaTypes() {
        [TEXT_PLAIN, APPLICATION_JSON]
    }

    @Override
    BroadcastId read(Class<? extends BroadcastId> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new NotImplementedException("BroadcastId write conversions not implemented.")
    }

    @Override
    void write(BroadcastId broadcastId, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (contentType == TEXT_PLAIN)
            writeBroadcastText(outputMessage, broadcastId)
        else
            writeBroadcastObject(outputMessage, broadcastId)
        os.close()
        os.flush()
    }

    private writeBroadcastText(HttpOutputMessage message, BroadcastId broadcastId) {
        message.headers.add CONTENT_TYPE_HEADER, TEXT_PLAIN_VALUE
        message.body << "$broadcastId.value"
    }

    private writeBroadcastObject(HttpOutputMessage message, BroadcastId broadcastId) {
        message.headers.add CONTENT_TYPE_HEADER, APPLICATION_JSON
        objectMapper.writeValue(message.body, broadcastId)
    }
}
