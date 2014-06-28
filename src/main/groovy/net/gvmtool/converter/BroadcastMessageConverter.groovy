package net.gvmtool.converter

import net.gvmtool.domain.Broadcast
import net.gvmtool.service.TextRenderer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException

import static org.springframework.http.MediaType.TEXT_PLAIN

class BroadcastMessageConverter implements HttpMessageConverter<Broadcast> {

    @Autowired
    TextRenderer renderer

    @Override
    boolean canRead(Class<?> clazz, MediaType mediaType) {
        false
    }

    @Override
    boolean canWrite(Class<?> clazz, MediaType mediaType) {
        (clazz == Broadcast) && (mediaType == TEXT_PLAIN)
    }

    @Override
    List<MediaType> getSupportedMediaTypes() {
        [TEXT_PLAIN]
    }

    @Override
    Broadcast read(Class<? extends Broadcast> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new RuntimeException("BroadcastMessage read conversions not implemented.")
    }

    @Override
    void write(Broadcast broadcast, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        def os = outputMessage.body
        os << renderer.prepare(broadcast)
        os.close()
        os.flush()
    }
}