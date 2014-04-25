package net.gvmtool

import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

class BroadcastControllerSpec extends Specification {

    def controller

    void setup() {
        controller = new BroadcastController()
    }

    void "should return a successful response"() {
        when:
        ResponseEntity<String> result = controller.get()

        then:
        result.statusCode == OK
    }

    void "should return the current broadcast message"() {
        given:
        def message = "Welcome to GVM!"

        when:
        ResponseEntity<String> result = controller.get()

        then:
        result.body == message
    }

}
