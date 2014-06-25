package net.gvmtool.converter

import com.fasterxml.jackson.databind.ObjectMapper
import net.gvmtool.domain.Broadcast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.TEXT_PLAIN

class BroadcastMessageConverter implements HttpMessageConverter<Broadcast> {

    @Autowired
    ObjectMapper objectMapper

    @Override
    boolean canRead(Class<?> clazz, MediaType mediaType) {
        false
    }

    @Override
    boolean canWrite(Class<?> clazz, MediaType mediaType) {
        true
    }

    @Override
    List<MediaType> getSupportedMediaTypes() {
        [TEXT_PLAIN, APPLICATION_JSON]
    }

    @Override
    Broadcast read(Class<? extends Broadcast> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new RuntimeException("Not implemented.")
    }

    @Override
    void write(Broadcast broadcast, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        def os = outputMessage.body
        if (contentType == MediaType.TEXT_PLAIN)
            writeBroadcastText(os, broadcast)
        else
            writeBroadcastObject(os, broadcast)
        os.close()
        os.flush()
    }

    private writeBroadcastText(OutputStream os, Broadcast broadcast) {
        os << broadcast.text
    }

    private writeBroadcastObject(OutputStream os, Broadcast broadcast) {
        objectMapper.writeValue(os, broadcast)
    }
}