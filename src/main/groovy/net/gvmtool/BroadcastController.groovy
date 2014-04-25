package net.gvmtool

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class BroadcastController {

    @RequestMapping("/broadcast")
    @ResponseBody ResponseEntity<String> get(){
        new ResponseEntity<String>("Welcome to GVM!", HttpStatus.OK)
    }
}
