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
package io.sdkman.service

import io.sdkman.domain.Broadcast
import spock.lang.Specification

import static io.sdkman.service.TextService.getFOOTER
import static io.sdkman.service.TextService.getHEADER

class TextServiceSpec extends Specification {

    TextService service

    Broadcast broadcast1 = new Broadcast(id: 1, text: "text1", date: new Date())
    Broadcast broadcast2 = new Broadcast(id: 2, text: "text2", date: new Date())


    Date iso8601Date = new Date().copyWith(
            year: 2019,
            month: Calendar.FEBRUARY,
            dayOfMonth: 11)
    Broadcast iso8601Broadcast = new Broadcast(id: "3",text: "Groovy 2.7.1 released!", date: iso8601Date)

    void setup() {
        service = new TextService()
    }

    void "should add header line when preparing broadcast text"() {
        given:
        def broadcasts = [broadcast1, broadcast2]

        when:
        def text = service.prepare(broadcasts)
        def firstLine = text.readLines().first()

        then:
        firstLine == '==== BROADCAST ================================================================='
    }

    void "should add footer line when preparing broadcast text"() {
        given:
        def broadcasts = [broadcast1, broadcast2]

        when:
        def text = service.prepare(broadcasts)
        def lastLine = text.readLines().last()

        then:
        lastLine == '================================================================================'
    }

    void "should place each string on a new line"() {
        given:
        def broadcasts = [broadcast1, broadcast2]

        when:
        def text = service.prepare(broadcasts)
        def readLines = text.readLines()

        then:
        readLines.size() == 4
        readLines[0] == HEADER
        readLines[1].contains(broadcast1.text)
        readLines[2].contains(broadcast2.text)
        readLines[3] == FOOTER
    }

    void "should allow conversion of single broadcasts"() {
        when:
        def text = service.prepare(broadcast1)
        def readLines = text.readLines()

        then:
        readLines.size() == 3
        readLines[0] == HEADER
        readLines[1].contains(broadcast1.text)
        readLines[2] == FOOTER
    }

    void "should render a friendly message if no broadcasts are passed in"() {
        when:
        def text = service.prepare([])
        def readLines = text.readLines()

        then:
        readLines[0] == HEADER
        readLines[1] == "No broadcasts available at present."
        readLines[2] == FOOTER
    }

    void "should compose a structured release message line"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"

        when:
        String message = service.composeStructuredMessage(candidate, version, hashtag)

        then:
        message == "Groovy 2.3.0 released on SDKMAN! #groovylang"
    }

    void "should compose a structured release message ignoring a # in the hashtag"() {
        given:
        def hashtag = "#groovylang"

        when:
        String message = service.composeStructuredMessage("", "", hashtag)

        then:
        message.contains " #groovylang"
    }

    void "should compose a structured release message including a # in the hashtag"() {
        given:
        def hashtag = "groovylang"

        when:
        String message = service.composeStructuredMessage("", "", hashtag)

        then:
        message.contains " #groovylang"
    }

    void "should compose a structured release message with default hashtag if not provided"() {
        when:
        String message = service.composeStructuredMessage("groovy", "2.0.4")

        then:
        message.contains " #groovy"
    }

    void "should format the date in iso 8601 format"(){
        when:
        String message = service.prepare(iso8601Broadcast)

        then:
        message.contains "* 2019-02-11: Groovy 2.7.1 released!"+ System.lineSeparator()

    }
}
