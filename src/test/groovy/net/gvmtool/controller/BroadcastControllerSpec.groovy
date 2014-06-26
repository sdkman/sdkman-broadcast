package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.domain.BroadcastId
import net.gvmtool.exception.BroadcastException
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.service.TextRenderService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class BroadcastControllerSpec extends Specification {

    BroadcastController controller
    TextRenderService renderService = new TextRenderService()
    BroadcastRepository repository = Mock()

    void setup() {
        controller = new BroadcastController(renderService: renderService, repository: repository)
    }

    void "broadcast id for latest should successfully identify the latest broadcast message"() {
        given:
        def id = 12345
        def broadcast = new Broadcast(id: id)
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast]

        when:
        ResponseEntity<BroadcastId> response = controller.latestId()

        then:
        1 * repository.findAll({it.pageSize == 1}) >> broadcastPage

        and:
        response.statusCode == HttpStatus.OK
        response.body.value == id
    }

    void "broadcast id for latest should return a broadcast exception if no latest broadcast message is available"() {
        given:
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> []

        when:
        controller.latestId()

        then:
        1 * repository.findAll({it.pageSize == 1}) >> broadcastPage

        and:
        thrown BroadcastException
    }

    void "broadcast by id should return a single broadcast for a valid id"() {
        given:
        def id = 1234
        def text = "some message"
        def broadcast = new Broadcast(id: id, text: text)

        when:
        ResponseEntity<Broadcast> response = controller.byId(id)

        then:
        1 * repository.findOne(id) >> broadcast

        and:
        response.statusCode == HttpStatus.OK
        response.body.text == text
    }

    void "broadcast by id should throw a broadcast exception for an invalid id"() {
        given:
        def invalidId = 0
        repository.findOne(invalidId) >> null

        when:
        controller.byId(invalidId)

        then:
        thrown BroadcastException
    }

    void "broadcast latest should successfully return the current broadcast message from the repo"() {
        given:
        def message = "Welcome to GVM!"
        def broadcast = new Broadcast(text: message, date: new Date())
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast]

        when:
        ResponseEntity<String> result = controller.latest(1)

        then:
        1 * repository.findAll({ it.pageSize == 1 }) >> broadcastPage

        and:
        result.statusCode == OK
        result.body.contains message
    }

    void "broadcast latest should return a given number of latest broadcasts from the repo"() {
        given:
        def limit = 2
        def broadcast1 = new Broadcast(text: "broadcast 1", date: new Date())
        def broadcast2 = new Broadcast(text: "broadcast 2", date: new Date())
        def broadcast3 = new Broadcast(text: "broadcast 3", date: new Date())
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast1, broadcast2]

        when:
        ResponseEntity<String> result = controller.latest(limit)
        def lines = result.body.readLines()

        then:
        1 * repository.findAll({ it.pageSize == limit }) >> broadcastPage

        and:
        lines[1] == broadcast1.text
        lines[2] == broadcast2.text

        and:
        !lines.contains(broadcast3.text)
    }

    void "should respond with not found in case of broadcast exception"() {
        given:
        def message = "Some message"
        def be = new BroadcastException(message)

        when:
        ResponseEntity response = controller.handle(be)

        then:
        response.statusCode == NOT_FOUND
        response.body == message
    }

}


