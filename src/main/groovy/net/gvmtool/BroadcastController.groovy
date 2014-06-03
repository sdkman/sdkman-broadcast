package net.gvmtool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.data.domain.Sort.Direction.DESC

@Controller
class BroadcastController {

    @Autowired
    BroadcastRepository repository

    @RequestMapping(value = "/broadcast/{limit}", produces = "text/plain")
    @ResponseBody ResponseEntity<String> get(@PathVariable int limit) {
        def pageRequest = new PageRequest(0, limit, DESC, "date")
        def page = repository.findAll(pageRequest)

        def broadcasts = page.content.collect({ it.text }).join('\n')
        new ResponseEntity<String>(broadcasts, HttpStatus.OK)
    }

    @RequestMapping(value = "/broadcast", produces = "text/plain")
    @ResponseBody ResponseEntity<String> get() {
        def pageRequest = new PageRequest(0, 1, DESC, "date")
        def page = repository.findAll(pageRequest)

        def broadcasts = page.content.collect({ it.text }).join('\n')
        new ResponseEntity<String>(broadcasts, HttpStatus.OK)
    }
}
