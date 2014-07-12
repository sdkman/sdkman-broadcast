package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.domain.BroadcastId
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.FreeFormAnnounceRequest
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.service.TextService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class AnnounceControllerSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextService textService = Mock()

    void setup(){
        controller = new AnnounceController(repository: repository, textService: textService)
    }

    void "announce structured should render and save a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version)

        and:
        def structuredMessage = "Groovy 2.3.0 has been released."

        when:
        controller.structured(request)

        then:
        1 * textService.composeStructuredMessage(candidate, version) >> structuredMessage
        1 * repository.save({it.text == structuredMessage}) >> new Broadcast(id: "1234")
    }

    void "announce structured should respond with a broadcast id after saving"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version)

        and:
        def broadcastId = "1234"
        textService.composeStructuredMessage(_, _) >> "some message"
        repository.save(_ as Broadcast) >> new Broadcast(id: broadcastId, text: "some message")

        when:
        def response = controller.structured(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.value == broadcastId
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


}
