package net.gvmtool.converter

import net.gvmtool.domain.Broadcast
import net.gvmtool.service.TextService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import spock.lang.Specification

import static net.gvmtool.converter.BroadcastMessageConverter.CONTENT_TYPE_HEADER
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE

class BroadcastListMessageConverterSpec extends Specification {

    BroadcastListMessageConverter converter

    void setup() {
        converter = new BroadcastListMessageConverter()
        converter.textService = Stub(TextService)
    }

    void "should add plain text content header"() {
        given:
        def broadcast = new Broadcast()
        def broadcasts = [broadcast]
        HttpOutputMessage outputMessage = Stub()

        and:
        OutputStream outputStream = Stub()
        outputMessage.getBody() >> outputStream

        and:
        HttpHeaders headers = Mock()
        outputMessage.getHeaders() >> headers

        when:
        converter.write(broadcasts, MediaType.TEXT_PLAIN, outputMessage)

        then:
        1 * headers.add(CONTENT_TYPE_HEADER, TEXT_PLAIN_VALUE)
    }


}
