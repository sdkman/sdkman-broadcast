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

class BroadcastIdMessageConverter implements HttpMessageConverter<BroadcastId> {

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
        def os = outputMessage.body
        if (contentType == MediaType.TEXT_PLAIN)
            writeBroadcastText(os, broadcastId)
        else
            writeBroadcastObject(os, broadcastId)
        os.close()
        os.flush()
    }

    private writeBroadcastText(OutputStream os, BroadcastId broadcastId) {
        os << "$broadcastId.value"
    }

    private writeBroadcastObject(OutputStream os, BroadcastId broadcastId) {
        objectMapper.writeValue(os, broadcastId)
    }
}
