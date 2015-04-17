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

package net.gvmtool.security

import net.gvmtool.exception.ForbiddenException
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

class AuthorisationSpec extends Specification {

    class AuthorisationUnderTest implements Authorisation {}

    AuthorisationUnderTest underTest

    void setup() {
        def secureHeaders = new SecureHeaders(token: "valid_token")
        underTest = new AuthorisationUnderTest(secureHeaders: secureHeaders)
    }

    void "should invoke function and return success response on valid token and consumer"() {
        given:
        def invoked = false
        def success = new ResponseEntity("Success", OK)
        def fun = {
            invoked = true
            success
        }

        when:
        def response = underTest.withAuthorisation("valid_token", {true}, fun)

        then:
        invoked
        response == success
    }

    void "should not invoke function and return forbidden response if token not valid"() {
        given:
        def invoked = false
        def fun = {
            invoked = true
        }

        when:
        underTest.withAuthorisation("invalid_token", {true}, fun)

        then:
        ! invoked
        thrown(ForbiddenException)
    }

    void "should not invoke function and return forbidden response if consumer validation fails"() {
        given:
        def invoked = false
        def fun = {
            invoked = true
        }

        when:
        underTest.withAuthorisation("valid_token", {false}, fun)

        then:
        ! invoked
        thrown(ForbiddenException)
    }
}
