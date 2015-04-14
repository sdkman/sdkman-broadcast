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
package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.security.AccessToken
import net.gvmtool.service.TextService
import net.gvmtool.service.TwitterService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AnnounceStructuredMessageSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextService textService = Mock()
    TwitterService twitterService = Mock()

    AccessToken accessToken = new AccessToken(value: "default_token")

    void setup(){
        controller = new AnnounceController(
                repository: repository,
                textService: textService,
                twitterService: twitterService,
                accessToken: accessToken)
    }

    void "announce structured should compose a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version, hashtag: hashtag)

        def header = "default_token"
        def consumer = "groovy"

        and:
        def structuredMessage = "Groovy 2.3.0 has been released on GVM. #groovylang"

        and:
        repository.save(_) >> new Broadcast(id: "1234")

        when:
        controller.structured(request, header, consumer)

        then:
        1 * textService.composeStructuredMessage(candidate, version, hashtag) >> structuredMessage
    }

    void "announce structured should save a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version, hashtag: hashtag)

        def header = "default_token"
        def consumer = "groovy"

        and:
        def structuredMessage = "Groovy 2.3.0 has been released on GVM. #groovylang"
        textService.composeStructuredMessage(_, _, _) >> structuredMessage

        when:
        controller.structured(request, header, consumer)

        then:
        1 * repository.save({it.text == structuredMessage}) >> new Broadcast(id: "1234")
    }

    void "announce structured should respond with a broadcast id after saving"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version, hashtag: hashtag)

        def header = "default_token"
        def consumer = "groovy"

        and:
        def broadcastId = "1234"
        textService.composeStructuredMessage(_, _, _) >> "some message"
        repository.save(_ as Broadcast) >> new Broadcast(id: broadcastId, text: "some message")

        when:
        def response = controller.structured(request, header, consumer)

        then:
        response.statusCode == HttpStatus.OK
        response.body.id == broadcastId
    }

    void "announce structured should post a structured message to twitter"() {
        given:
        def request = new StructuredAnnounceRequest(candidate: "groovy", version: "2.4.0", hashtag: "groovylang")
        def status = "Groovy 2.4.0 has been released on GVM. #groovylang"
        def broadcast = new Broadcast(id: "1234", text: status, date: new Date())

        def header = "default_token"
        def consumer = "groovy"

        and:
        repository.save(_) >> broadcast
        textService.composeStructuredMessage(_, _, _) >> status

        when:
        controller.structured(request, header, consumer)

        then:
        1 * twitterService.update(status)
    }
}
