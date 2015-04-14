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

import net.gvmtool.request.FreeFormAnnounceRequest
import net.gvmtool.security.AccessToken
import net.gvmtool.domain.Broadcast
import net.gvmtool.exception.ForbiddenException
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.service.TextService
import net.gvmtool.service.TwitterService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AuthorisedAnnouncementSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextService textService = Mock()
    TwitterService twitterService = Mock()

    void setup(){
        controller = new AnnounceController(repository: repository, textService: textService, twitterService: twitterService)
    }

    void "should authorise a structured announcement on valid token and consumer headers"() {
        given:
        controller.accessToken = new AccessToken(value: "valid_token")

        and:
        def request = new StructuredAnnounceRequest(candidate: "groovy")
        Broadcast broadcast = new Broadcast(id: "1", text: "text", date: new Date())

        when:
        def response = controller.structured(request, "valid_token", "groovy")

        then:
        repository.save(_) >> broadcast

        and:
        response.statusCode == HttpStatus.OK
    }

    void "should not authorise a structured request on an invalid token header"() {
        given:
        controller.accessToken = new AccessToken(value: "valid_token")

        and:
        def request = new StructuredAnnounceRequest(candidate: "groovy")
        def token = "invalid_token"
        def consumer = "groovy"

        when:
        controller.structured(request, token, consumer)

        then:
        def e = thrown(ForbiddenException)
        e.message == "Not authorised to access this service."
    }

    void "should not authorise a structured request on an invalid consumer header"() {
        given:
        controller.accessToken = new AccessToken(value: "valid_token")

        and:
        def request = new StructuredAnnounceRequest(candidate: "groovy")
        def token = "valid_token"
        def consumer = "grails"

        when:
        controller.structured(request, token, consumer)

        then:
        def e = thrown(ForbiddenException)
        e.message == "Not authorised to access this service."
    }


    void "should authorise a freeform announcement on a valid token header"() {
        given:
        controller.accessToken = new AccessToken(value: "valid_token")

        and:
        FreeFormAnnounceRequest request = Mock()
        Broadcast broadcast = new Broadcast(id: "1", text: "text", date: new Date())

        when:
        def response = controller.freeForm(request, "valid_token")

        then:
        repository.save(_) >> broadcast

        and:
        response.statusCode == HttpStatus.OK
    }

    void "should not authorise a freeform request on an invalid token header"() {
        given:
        controller.accessToken = new AccessToken(value: "valid_token")

        and:
        FreeFormAnnounceRequest request = Mock()
        String token = "invalid_token"

        when:
        controller.freeForm(request, token)

        then:
        def e = thrown(ForbiddenException)
        e.message == "Not authorised to access this service."
    }
}
