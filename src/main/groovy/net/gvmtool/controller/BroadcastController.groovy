package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.exception.BroadcastException
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.service.TextRenderService
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
    TextRenderService renderService

    @Autowired
    BroadcastRepository repository

    @RequestMapping(value = "/broadcast/id", produces = [TEXT_PLAIN_VALUE, APPLICATION_JSON_VALUE], method = GET)
    @ResponseBody
    ResponseEntity<Broadcast> latestId() {
        def pageRequest = new PageRequest(0, 1, DESC, "date")
        def page = repository.findAll(pageRequest)
        def broadcast = valid(page.content.collect { it }).first()
        new ResponseEntity<Broadcast>(broadcast, OK)
    }

    @RequestMapping(value = "/broadcast", produces = "text/plain", method = GET)
    @ResponseBody
    ResponseEntity<String> get(@RequestParam(value = "limit", defaultValue = "1") Integer limit) {
        def pageRequest = new PageRequest(0, limit.intValue(), DESC, "date")
        def page = repository.findAll(pageRequest)
        def text = renderService.prepare(page.content.collect { it.text })
        new ResponseEntity<String>(text, OK)
    }

    @RequestMapping(value = "/broadcast/{id}", produces = "text/plain", method = GET)
    @ResponseBody
    ResponseEntity<String> byId(@PathVariable int id) {
        def broadcast = valid(repository.findOne(id))
        new ResponseEntity<String>(broadcast.text, OK)
    }

    private valid(def broadcast) {
        if (!broadcast) throw new BroadcastException("Not Found.")
        else broadcast
    }

    @ExceptionHandler(BroadcastException)
    @ResponseBody
    ResponseEntity handle(BroadcastException be) {
        new ResponseEntity(be.message, NOT_FOUND)
    }
}
