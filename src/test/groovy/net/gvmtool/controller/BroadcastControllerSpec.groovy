package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.service.TextRenderService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

class BroadcastControllerSpec extends Specification {

    BroadcastController controller
    TextRenderService renderService = new TextRenderService()
    BroadcastRepository repository = Mock()

    void setup() {
        controller = new BroadcastController(renderService: renderService, repository: repository)
    }

    void "should successfully identify the latest broadcast message"() {
        given:
        def id = 12345
        def broadcast = new Broadcast(id: id)
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast]

        when:
        ResponseEntity<String> response = controller.latestId()

        then:
        1 * repository.findAll({it.pageSize == 1}) >> broadcastPage

        and:
        response.statusCode == HttpStatus.OK
        response.body == id.toString()
    }

    void "should successfully return the current broadcast message from the repo"() {
        given:
        def message = "Welcome to GVM!"
        def broadcast = new Broadcast(text: message, date: new Date())
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast]

        when:
        ResponseEntity<String> result = controller.get(1)

        then:
        1 * repository.findAll({ it.pageSize == 1 }) >> broadcastPage

        and:
        result.statusCode == OK
        result.body.contains message
    }

    void "should return a given number of latest broadcasts from the repo"() {
        given:
        def limit = 2
        def broadcast1 = new Broadcast(text: "broadcast 1", date: new Date())
        def broadcast2 = new Broadcast(text: "broadcast 2", date: new Date())
        def broadcast3 = new Broadcast(text: "broadcast 3", date: new Date())
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast1, broadcast2]

        when:
        ResponseEntity<String> result = controller.get(limit)
        def lines = result.body.readLines()

        then:
        1 * repository.findAll({ it.pageSize == limit }) >> broadcastPage

        and:
        lines[1] == broadcast1.text
        lines[2] == broadcast2.text

        and:
        !lines.contains(broadcast3.text)
    }

}


