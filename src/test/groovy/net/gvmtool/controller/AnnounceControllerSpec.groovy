package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.BroadcastRequest
import net.gvmtool.service.TextRenderer
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AnnounceControllerSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextRenderer renderer = Mock()

    void setup(){
        controller = new AnnounceController(repository: repository, renderer: renderer)
    }

    void "should render and save a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def request = new BroadcastRequest(candidate: candidate, version: version)

        and:
        def structuredMessage = "Groovy 2.3.0 has been released."

        when:
        controller.structured(request)

        then:
        1 * renderer.composeStructuredMessage(candidate, version) >> structuredMessage
        1 * repository.save({it.text == structuredMessage}) >> new Broadcast(id: "1234")
    }

    void "should respond with a broadcast id after saving a structured message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def request = new BroadcastRequest(candidate: candidate, version: version)

        and:
        def broadcastId = "1234"
        renderer.composeStructuredMessage(_, _) >> "some message"
        repository.save(_ as Broadcast) >> new Broadcast(id: broadcastId, text: "some message")

        when:
        def response = controller.structured(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.value == broadcastId
    }


}
