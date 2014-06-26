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

import static org.springframework.http.MediaType.*

class BroadcastMessageConverter implements HttpMessageConverter<Broadcast> {

    @Autowired
    ObjectMapper objectMapper

    @Override
    boolean canRead(Class<?> clazz, MediaType mediaType) {
        false
    }

    @Override
    boolean canWrite(Class<?> clazz, MediaType mediaType) {
        clazz == Broadcast
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

        if (contentType == TEXT_PLAIN)
            writeBroadcastText(outputMessage, broadcast)
        else
            writeBroadcastObject(outputMessage, broadcast)
        os.close()
        os.flush()
    }

    private writeBroadcastText(HttpOutputMessage message, Broadcast broadcast) {
        message.headers.add "Content-Type", TEXT_PLAIN_VALUE
        message.body << broadcast.text
    }

    private writeBroadcastObject(HttpOutputMessage message, Broadcast broadcast) {
        message.headers.add "Content-Type", APPLICATION_JSON
        objectMapper.writeValue(message.body, broadcast)
    }
}