package net.gvmtool

import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

class BroadcastControllerSpec extends Specification {

    BroadcastController controller
    BroadcastRepository repository = Mock()

    void setup() {
        controller = new BroadcastController(repository:repository)
    }

    void "should return a successful response"() {
        when:
        ResponseEntity<String> result = controller.get()

        then:
        repository.findAll() >> [new Broadcast()]

        and:
        result.statusCode == OK
    }

    void "should return the current broadcast message"() {
        given:
        def message = "Welcome to GVM!"
        def broadcast = new Broadcast(text: message)

        when:
        ResponseEntity<String> result = controller.get()

        then:
        1 * repository.findAll() >> [broadcast]

        and:
        result.body == message
    }

}
