package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.domain.BroadcastId
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.FreeFormAnnounceRequest
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.service.TextService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.POST

@Controller
class AnnounceController {

    @Autowired
    BroadcastRepository repository

    @Autowired
    TextService textService

    @RequestMapping(value = "/announce/struct", method = POST)
    @ResponseBody
    ResponseEntity<BroadcastId> structured(@RequestBody StructuredAnnounceRequest request) {
        def message = textService.composeStructuredMessage(request.candidate, request.version)
        def broadcast = repository.save(new Broadcast(text: message, date: new Date()))
        new ResponseEntity<BroadcastId>(broadcast.toBroadcastId(), OK)
    }

    @RequestMapping(value = "/announce/freeform", method = POST)
    @ResponseBody
    ResponseEntity<BroadcastId> freeForm(@RequestBody FreeFormAnnounceRequest request) {
        def broadcast = repository.save(new Broadcast(text: request.text, date: new Date()))
        new ResponseEntity<BroadcastId>(broadcast.toBroadcastId(), OK)
    }
}
