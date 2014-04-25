package net.gvmtool

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class BroadcastController {
    ResponseEntity<String> get(){
        new ResponseEntity<String>("Welcome to GVM!", HttpStatus.OK)
    }
}
