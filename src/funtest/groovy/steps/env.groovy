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
package steps

import groovy.json.JsonSlurper
import wslite.rest.RESTClient

import static cucumber.api.groovy.Hooks.After
import static db.MongoHelper.*

def BASE_URL = "http://localhost:9001"
restClient = new RESTClient(BASE_URL)
slurper = new JsonSlurper()

lineOrder = ["first": 0, "second": 1, "third": 2, "forth": 3, "fifth": 4]
statusCodes = [200: "OK", 403: "FORBIDDEN", 404: "NOT_FOUND", 405: "METHOD_NOT_ALLOWED"]

if(!binding.hasVariable("db")) {
    db = prepareDB()
}

After() {
    dropCollectionFromDb(db, "application")
    dropCollectionFromDb(db, "broadcast")
    dropCollectionFromDb(db, "candidates")
}
