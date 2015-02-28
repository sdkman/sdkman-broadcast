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
package net.gvmtool.converter

import net.gvmtool.response.BroadcastId
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
        addContentTypeText outputMessage

        def outputStream = outputMessage.body
        output broadcastId, outputStream
    }

    private output(BroadcastId broadcastId, OutputStream os) {
        os << "$broadcastId.value"
    }

    private addContentTypeText(message) {
        message.headers.add CONTENT_TYPE_HEADER, TEXT_PLAIN_VALUE
    }
}
