package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.domain.BroadcastId
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.service.TextRenderer
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
    TextRenderer renderer

    @RequestMapping(value = "/announce", method = POST)
    @ResponseBody
    ResponseEntity<BroadcastId> structured(@RequestBody StructuredAnnounceRequest request) {
        def message = renderer.composeStructuredMessage(request.candidate, request.version)
        def broadcast = repository.save(new Broadcast(text: message, date: new Date()))
        new ResponseEntity<BroadcastId>(broadcast.toBroadcastId(), OK)
    }
}
