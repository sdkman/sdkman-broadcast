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

import static cucumber.api.groovy.EN.And

And(~'^has a single result$') { ->
    assert json(response).size() == 1
}

And(~'^the first json message is "(.*)"$') { String message ->
    assert message == json(response).first().text
}

And(~'^the "([^"]*)" json message is "([^"]*)"$') { String number, String message ->
    assert json(response).get(lineOrder[number]).text == message
}

And(~'^the json message "([^"]*)" has not been received$') { String message ->
    assert !json(response).find { it.text == message }
}

And(~'^the json identifier is "(.*)"$') { String id ->
    assert json(response).id == id
}

And(~'^the json message is "(.*)"$') { String message ->
    assert json(response).text == message
}

And(~'^the content type is "([^"]*)"$') { String contentType ->
    assert headers['Content-Type'].contains(contentType)
}

And(~'^a valid Broadcast Identifier is returned$') { ->
    broadcastId = json(response).id
}

And(~'^a total of (\\d+) json messages have been received$') { int number ->
    assert json(response).size() == number
}

private json(response) {
    slurper.parseText(response)
}
