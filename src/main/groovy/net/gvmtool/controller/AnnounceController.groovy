/*
 * Copyright 2014 Marco Vermeulen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.gvmtool.controller

import net.gvmtool.domain.Broadcast
import net.gvmtool.repo.BroadcastRepository
import net.gvmtool.request.FreeFormAnnounceRequest
import net.gvmtool.request.StructuredAnnounceRequest
import net.gvmtool.security.Authorisation
import net.gvmtool.service.TextService
import net.gvmtool.service.TwitterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.POST

@Controller
class AnnounceController implements Authorisation {

    @Autowired
    BroadcastRepository repository

    @Autowired
    TextService textService

    @Autowired
    TwitterService twitterService

    @RequestMapping(value = "/announce/struct", method = POST)
    @ResponseBody
    ResponseEntity<ApiResponse> structured(@RequestBody StructuredAnnounceRequest request,
                                           @RequestHeader(value = "access_token") String header) {
        withAuthorisation(header) {
            def message = textService.composeStructuredMessage(request.candidate, request.version, request.hashtag)
            twitterService.update(message)
            def broadcast = repository.save(new Broadcast(text: message, date: new Date()))
            new ResponseEntity<ApiResponse>(new ApiResponse(status: OK.value(), id: broadcast.id, message: broadcast.text), OK)
        }
    }

    @RequestMapping(value = "/announce/freeform", method = POST)
    @ResponseBody
    ResponseEntity<ApiResponse> freeForm(@RequestBody FreeFormAnnounceRequest request,
                                         @RequestHeader(value = "access_token") String header) {
        withAuthorisation(header) {
            twitterService.update(request.text)
            def broadcast = repository.save(new Broadcast(text: request.text, date: new Date()))
            new ResponseEntity<ApiResponse>(new ApiResponse(status: OK.value(), id: broadcast.id, message: broadcast.text), OK)
        }
    }

    class ApiResponse {
        int status
        String id
        String message
    }
}
