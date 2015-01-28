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
import spock.lang.Specification

class AnnounceFreeFormMessageSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextService textService = Mock()
    TwitterService twitterService = Mock()

    void setup(){
        controller = new AnnounceController(repository: repository, textService: textService, twitterService: twitterService)
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
