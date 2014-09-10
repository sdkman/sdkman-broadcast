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
import net.gvmtool.domain.BroadcastId
import net.gvmtool.exception.BroadcastException
import net.gvmtool.repo.BroadcastRepository
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class BroadcastControllerSpec extends Specification {

    BroadcastController controller
    BroadcastRepository repository = Mock()

    void setup() {
        controller = new BroadcastController(repository: repository)
    }

    void "broadcast id for latest should successfully identify the latest broadcast message"() {
        given:
        def id = "12345"
        def broadcast = new Broadcast(id: id)
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast]

        when:
        ResponseEntity<BroadcastId> response = controller.latestId()

        then:
        1 * repository.findAll({it.pageSize == 1}) >> broadcastPage

        and:
        response.statusCode == HttpStatus.OK
        response.body.value == id
    }

    void "broadcast id for latest should return a broadcast exception if no latest broadcast message is available"() {
        given:
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> []

        when:
        controller.latestId()

        then:
        1 * repository.findAll({it.pageSize == 1}) >> broadcastPage

        and:
        thrown BroadcastException
    }

    void "broadcast by id should return a single broadcast for a valid id"() {
        given:
        def id = "1234"
        def text = "some message"
        def broadcast = new Broadcast(id: id, text: text)

        when:
        ResponseEntity<Broadcast> response = controller.byId(id)

        then:
        1 * repository.findOne(id) >> broadcast

        and:
        response.statusCode == HttpStatus.OK
        response.body.text == text
    }

    void "broadcast by id should throw a broadcast exception for an invalid id"() {
        given:
        def invalidId = "0"
        repository.findOne(invalidId) >> null

        when:
        controller.byId(invalidId)

        then:
        thrown BroadcastException
    }

    void "broadcast latest should successfully return the default number of broadcast messages from the repo"() {
        given:
        def id = "1234"
        def date = new Date()
        def message = "Welcome to GVM!"
        def broadcast = new Broadcast(id: id, text: message, date: date)
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast]

        when:
        ResponseEntity<List<Broadcast>> result = controller.latest(3)
        def line = result.body.first()

        then:
        1 * repository.findAll({ it.pageSize == 3 }) >> broadcastPage

        and:
        result.body.size() == 1

        and:
        result.statusCode == OK
        line.id == id
        line.date == date
        line.text == message
    }

    void "broadcast latest should return a given number of latest broadcasts from the repo"() {
        given:
        def limit = 2
        def broadcast1 = new Broadcast(id: 1, text: "broadcast 1", date: new Date())
        def broadcast2 = new Broadcast(id: 2, text: "broadcast 2", date: new Date())
        def broadcast3 = new Broadcast(id: 3, text: "broadcast 3", date: new Date())
        def broadcastPage = Stub(Page)

        and:
        broadcastPage.getContent() >> [broadcast1, broadcast2]

        when:
        ResponseEntity<List<Broadcast>> result = controller.latest(limit)
        def lines = result.body

        then:
        1 * repository.findAll({ it.pageSize == limit }) >> broadcastPage

        and:
        lines[0] == broadcast1
        lines[1] == broadcast2

        and:
        !lines.contains(broadcast3)
    }

    void "should respond with not found in case of broadcast exception"() {
        given:
        def message = "Some message"
        def be = new BroadcastException(message)

        when:
        ResponseEntity response = controller.handle(be)

        then:
        response.statusCode == NOT_FOUND
        response.body == message
    }

}


