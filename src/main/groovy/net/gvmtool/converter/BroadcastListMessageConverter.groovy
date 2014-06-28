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
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import static org.springframework.http.MediaType.TEXT_PLAIN

class BroadcastListMessageConverter implements HttpMessageConverter<List<Broadcast>> {

    @Autowired
    TextRenderer renderer

    @Override
    boolean canRead(Class<?> clazz, MediaType mediaType) {
        false
    }

    @Override
    boolean canWrite(Class<?> clazz, MediaType mediaType) {
        ArrayList == clazz && mediaType == TEXT_PLAIN
    }

    @Override
    List<MediaType> getSupportedMediaTypes() {
        [TEXT_PLAIN]
    }

    @Override
    List<Broadcast> read(Class<? extends List<Broadcast>> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new NotImplementedException("Broadcast List read conversions not implemented.")
    }

    @Override
    void write(List<Broadcast> broadcasts, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        def os = outputMessage.body
        os << renderer.prepare(broadcasts)
        os.flush()
        os.close()
    }
}
