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

import utils.HttpHelper
import wslite.rest.RESTClientException
import static cucumber.api.groovy.EN.And
import static utils.HttpHelper.*

And(~'^the identifier of the latest message is requested$') { ->
    http { get(path: "/broadcast/latest/id") }
}

And(~'^the identifier of the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    http { get(path: "/broadcast/latest/id", headers: [(header): value]) }
}

And(~'^the latest messages are requested$') { ->
    http { get(path: "/broadcast/latest") }
}

And(~'^the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    http { get(path: "/broadcast/latest", headers: [(header): value]) }
}

And(~'^the latest "([^"]*)" messages are requested$') { String limit ->
    http { get(path: "/broadcast/latest?limit=${limit}") }
}

And(~'^the latest "([^"]*)" messages are requested with "([^"]*)" header "([^"]*)"$') { String limit, String header, String value ->
    http { get(path: "/broadcast/latest?limit=${limit}", headers: [(header): value]) }
}

And(~'^a message is requested by identifier "([^"]*)" with "([^"]*)" header "([^"]*)"$') { String id, String header, String value ->
    http { get(path: "/broadcast/$id", headers: [(header): value]) }
}

And(~'^a message is requested by identifier "([^"]*)"$') { String id ->
    http { get(path: "/broadcast/$id") }
}

And(~/^an HTTP GET on the "([^"]*)" endpoint$/) { String endpoint ->
    http { get(path: endpoint) }
}

And(~/^an HTTP HEAD on the "([^"]*)" endpoint$/) { String endpoint ->
    http { head(path: endpoint) }
}

And(~/^the application should report a name of "(.*)"$/) { String name ->
    assert name == json(response).app.name
}

private http(restAction) {
    try {
        httpResponse = restAction()
        response = httpResponse.contentAsString
        statusCode = httpResponse.statusCode
        headers = httpResponse.headers

    } catch (RESTClientException re) {
        httpResponse = re.response
        response = httpResponse.statusMessage
        statusCode = httpResponse.statusCode
    }
}

private json(response) {
    HttpHelper.slurper.parseText(response)
}