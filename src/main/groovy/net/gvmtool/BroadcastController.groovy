package net.gvmtool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class BroadcastController {

    @Autowired
    BroadcastRepository repository

    @RequestMapping(value = "/broadcast", produces = "text/plain")
    @ResponseBody
    ResponseEntity<String> get() {
        def broadcasts = repository.findAll()
        new ResponseEntity<String>(broadcasts.first().text, HttpStatus.OK)
    }
}
