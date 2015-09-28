/*
 * Copyright 2014 Marco Vermeulen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sdkman.converter

import io.sdkman.domain.Broadcast
import io.sdkman.service.TextService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpOutputMessage
import spock.lang.Specification

import static io.sdkman.converter.BroadcastMessageConverter.CONTENT_TYPE_HEADER
import static org.springframework.http.MediaType.TEXT_PLAIN
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE

class BroadcastMessageConverterSpec extends Specification {

    BroadcastMessageConverter converter

    void setup() {
        converter = new BroadcastMessageConverter()
        converter.textService = Stub(TextService)
    }

    void "should add plain text content header"() {
        given:
        def broadcast = new Broadcast()
        HttpOutputMessage outputMessage = Stub()

        and:
        OutputStream outputStream = Stub()
        outputMessage.getBody() >> outputStream

        and:
        HttpHeaders headers = Mock()
        outputMessage.getHeaders() >> headers

        when:
        converter.write(broadcast, TEXT_PLAIN, outputMessage)

        then:
        1 * headers.add(CONTENT_TYPE_HEADER, TEXT_PLAIN_VALUE)
    }

}
