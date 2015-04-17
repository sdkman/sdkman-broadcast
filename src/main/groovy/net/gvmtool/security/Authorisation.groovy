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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

import static org.springframework.http.HttpStatus.FORBIDDEN

trait Authorisation {

    @Autowired
    SecureHeaders secureHeaders

    ResponseEntity withAuthorisation(String header, validation, fun) {
        if(secureHeaders.token == header && validation()) fun()
        else throw new ForbiddenException("Not authorised to access this service.")
    }

    @ExceptionHandler(ForbiddenException)
    ResponseEntity<ForbiddenMessage> handle(ForbiddenException ae) {
        new ResponseEntity<ForbiddenMessage>(buildAuthorisationDeniedMessage(ae.message), FORBIDDEN)
    }

    private static buildAuthorisationDeniedMessage(String message) {
        new ForbiddenMessage(code: FORBIDDEN.value(), message: message)
    }
}