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
