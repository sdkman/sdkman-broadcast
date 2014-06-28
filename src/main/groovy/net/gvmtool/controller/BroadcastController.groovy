package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.domain.BroadcastId
import net.gvmtool.exception.BroadcastException
import net.gvmtool.repo.BroadcastRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

import static org.springframework.data.domain.Sort.Direction.DESC
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.GET

@Controller
class BroadcastController {

    @Autowired
    BroadcastRepository repository

    @RequestMapping(value = "/broadcast/latest/id", produces = [TEXT_PLAIN_VALUE, APPLICATION_JSON_VALUE], method = GET)
    @ResponseBody
    ResponseEntity<BroadcastId> latestId() {
        def pageRequest = new PageRequest(0, 1, DESC, "date")
        def page = repository.findAll(pageRequest)
        def broadcast = valid(page.content.collect { it }).first()
        new ResponseEntity<BroadcastId>(broadcast.toBroadcastId(), OK)
    }

    @RequestMapping(value = "/broadcast/latest", produces = [TEXT_PLAIN_VALUE, APPLICATION_JSON_VALUE], method = GET)
    @ResponseBody
    ResponseEntity<List<Broadcast>> latest(@RequestParam(value = "limit", defaultValue = "1") Integer limit) {
        def pageRequest = new PageRequest(0, limit.intValue(), DESC, "date")
        def page = repository.findAll(pageRequest)
        def broadcasts = page.content.collect { it }
        new ResponseEntity<List<Broadcast>>(broadcasts, OK)
    }

    @RequestMapping(value = "/broadcast/{id}", produces = [TEXT_PLAIN_VALUE, APPLICATION_JSON_VALUE], method = GET)
    @ResponseBody
    ResponseEntity<Broadcast> byId(@PathVariable int id) {
        def broadcast = valid(repository.findOne(id))
        new ResponseEntity<Broadcast>(broadcast, OK)
    }

    private valid(broadcast) {
        if (!broadcast) throw new BroadcastException("Not Found.")
        else broadcast
    }

    @ExceptionHandler(BroadcastException)
    @ResponseBody
    ResponseEntity handle(BroadcastException be) {
        new ResponseEntity(be.message, NOT_FOUND)
    }
}
