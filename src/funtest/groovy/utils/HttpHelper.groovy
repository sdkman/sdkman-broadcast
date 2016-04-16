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
package utils

import groovy.json.JsonSlurper
import wslite.rest.RESTClient

class HttpHelper {

    static slurper = new JsonSlurper()

    static client = new RESTClient("http://localhost:9001")

    static post(map, closure) {
        client.post(map, closure)
    }

    static get(map) {
        client.get(map)
    }

}
