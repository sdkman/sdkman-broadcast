package net.gvmtool.controller

import net.gvmtool.repo.BroadcastRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.data.domain.Sort.Direction.DESC
import static org.springframework.web.bind.annotation.RequestMethod.GET

@Controller
class BroadcastController {

    final HEADER = '==== BROADCAST ================================================================='
    final FOOTER = '================================================================================'

    @Autowired
    BroadcastRepository repository

    @RequestMapping(value = ["/broadcast"], produces = "text/plain", method = GET)
    @ResponseBody
    ResponseEntity<String> get(@RequestParam(value = "limit", defaultValue = "1") Integer limit) {
        def pageRequest = new PageRequest(0, limit.intValue(), DESC, "date")
        def page = repository.findAll(pageRequest)
        def broadcasts = page.content.collect({ it.text }).join('\n')
        new ResponseEntity<String>(decorate(broadcasts), HttpStatus.OK)
    }

    private String decorate(String broadcasts) {
        "$HEADER\n$broadcasts\n$FOOTER"
    }
}
