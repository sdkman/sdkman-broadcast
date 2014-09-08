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
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

class HttpHelper {

    static slurper = new JsonSlurper()

    static client = new RESTClient("http://localhost:8080")

    static obtainBearerToken() {
        client.authorization = new HTTPBasicAuthorization("client_id", "client_secret")
        def oauthResp = client.post(path: "/oauth/token") {
            type "application/x-www-form-urlencoded"
            text "password=auth_password&username=auth_username&grant_type=password&scope=read%20write&client_secret=client_secret&client_id=client_id"
        }
        slurper.parseText(oauthResp.contentAsString).access_token
    }

    static post(map, closure) {
        client.post(map, closure)
    }

    static get(map) {
        client.get(map)
    }

}
