package net.gvmtool.controller

import net.gvmtool.exception.BroadcastException
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.service.TextRenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

import static org.springframework.data.domain.Sort.Direction.DESC
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET

@Controller
class BroadcastController {

    @Autowired
    TextRenderService renderService

    @Autowired
    BroadcastRepository repository

    @RequestMapping(value = "/broadcast/id", produces = "text/plain", method = GET)
    @ResponseBody
    ResponseEntity<String> latestId() {
        def pageRequest = new PageRequest(0, 1, DESC, "date")
        def page = repository.findAll(pageRequest)
        def id = page.content.collect { it.id }.first()
        new ResponseEntity<String>(id.toString(), OK)
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
        def broadcast = repository.findOne(id)
        if (!broadcast) throw new BroadcastException("Not found")
        new ResponseEntity<String>(broadcast.text, OK)
    }

    @ExceptionHandler(BroadcastException)
    @ResponseBody
    ResponseEntity handle(BroadcastException be) {
        new ResponseEntity(be.message, NOT_FOUND)
    }
}
