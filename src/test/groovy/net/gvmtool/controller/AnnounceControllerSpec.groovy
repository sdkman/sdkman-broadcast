package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.domain.BroadcastId
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.FreeFormAnnounceRequest
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.service.TextService
import net.gvmtool.service.TwitterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.social.twitter.api.TimelineOperations
import org.springframework.social.twitter.api.impl.TwitterTemplate
import spock.lang.Specification

class AnnounceControllerSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextService textService = Mock()
    TwitterService twitterService = Mock()

    void setup(){
        controller = new AnnounceController(repository: repository, textService: textService, twitterService: twitterService)
    }

    void "announce structured should compose a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version, hashtag: hashtag)

        and:
        def structuredMessage = "Groovy 2.3.0 has been released on GVM. #groovylang"

        and:
        repository.save(_) >> new Broadcast(id: "1234")

        when:
        controller.structured(request)

        then:
        1 * textService.composeStructuredMessage(candidate, version, hashtag) >> structuredMessage
    }

    void "announce structured should save a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version, hashtag: hashtag)

        and:
        def structuredMessage = "Groovy 2.3.0 has been released on GVM. #groovylang"
        textService.composeStructuredMessage(_, _, _) >> structuredMessage

        when:
        controller.structured(request)

        then:
        1 * repository.save({it.text == structuredMessage}) >> new Broadcast(id: "1234")
    }

    void "announce structured should respond with a broadcast id after saving"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version, hashtag: hashtag)

        and:
        def broadcastId = "1234"
        textService.composeStructuredMessage(_, _, _) >> "some message"
        repository.save(_ as Broadcast) >> new Broadcast(id: broadcastId, text: "some message")

        when:
        def response = controller.structured(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.value == broadcastId
    }

    void "announce structured should post a structured message to twitter"() {
        given:
        def request = new StructuredAnnounceRequest(candidate: "groovy", version: "2.4.0", hashtag: "groovylang")
        def status = "Groovy 2.4.0 has been released on GVM. #groovylang"
        def broadcast = new Broadcast(id: "1234", text: status, date: new Date())

        and:
        repository.save(_) >> broadcast
        textService.composeStructuredMessage(_, _, _) >> status

        when:
        controller.structured(request)

        then:
        1 * twitterService.update(status)
    }

    void "announce free form should save a free form message"() {
        given:
        def text = "message"
        def request = new FreeFormAnnounceRequest(text: text)
        def broadcast = new Broadcast(id: "1234", text: text)

        when:
        controller.freeForm(request)

        then:
        1 * repository.save({it.text == text}) >> broadcast
    }

    void "announce free form should return a broadcast id after saving"() {
        given:
        def text = "message"
        def request = new FreeFormAnnounceRequest(text: text)

        and:
        def broadcastId = "1234"
        def broadcast = new Broadcast(id: broadcastId, text: text, date: new Date())

        and:
        repository.save(_) >> broadcast

        when:
        ResponseEntity<BroadcastId> response = controller.freeForm(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.value == broadcastId
    }

    void "announce free form should post a free-form message to twitter"() {
        given:
        def status = "status"
        def request = new FreeFormAnnounceRequest(text: status)
        def broadcast = new Broadcast(id: "1234", text: status, date: new Date())

        and:
        repository.save(_) >> broadcast

        when:
        controller.freeForm(request)

        then:
        1 * twitterService.update(status)
    }

}
