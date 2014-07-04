package net.gvmtool.converter

import net.gvmtool.domain.BroadcastId
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import static org.springframework.http.MediaType.TEXT_PLAIN
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE

class BroadcastIdMessageConverter implements HttpMessageConverter<BroadcastId> {

    static final String CONTENT_TYPE_HEADER = "Content-Type"

    @Override
    boolean canRead(Class<?> clazz, MediaType mediaType) {
        false
    }

    @Override
    boolean canWrite(Class<?> clazz, MediaType mediaType) {
        (clazz == BroadcastId) && (mediaType == TEXT_PLAIN)
    }

    @Override
    List<MediaType> getSupportedMediaTypes() {
        [TEXT_PLAIN]
    }

    @Override
    BroadcastId read(Class<? extends BroadcastId> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new NotImplementedException("BroadcastId read conversions not implemented.")
    }

    @Override
    void write(BroadcastId broadcastId, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.headers.add CONTENT_TYPE_HEADER, TEXT_PLAIN_VALUE
        def os = outputMessage.body
        os << "$broadcastId.value"
        os.close()
        os.flush()
    }
}
