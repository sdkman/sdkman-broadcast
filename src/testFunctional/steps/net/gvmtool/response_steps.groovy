package net.gvmtool

import static cucumber.api.groovy.EN.And

lineOrder = ["first": 0, "second": 1, "third": 2, "forth": 3, "fifth": 4]
statusCodes = [200: "OK", 404: "NOT_FOUND"]

And(~'^only the message "([^"]*)" is received$') { String message ->
    def lines = response.readLines()
    assert lines.size() == 3
    assert lines[1].contains(message)
}

And(~'^the message "([^"]*)" is received "([^"]*)"$') { String message, String order ->
    def lines = response.readLines()
    def lineNumber = lineOrder[order] + 1
    assert lines[lineNumber] == message
}

And(~'^the message "([^"]*)" has not been received$') { String message ->
    def lines = response.readLines()
    assert !lines.contains(message)
}

And(~'^the identifier is "([^"]*)"$') { String uid ->
    assert response == uid
}

And(~'^a "([^"]*)" status is returned$') { String status ->
    assert status == statusCodes[statusCode]
}

And(~'^an "([^"]*)" status is returned$') { String status ->
    assert status == statusCodes[statusCode]
}

And(~'^a "([^"]*)" message is received$') { String message ->
    assert response == message
}

And(~'^the payload is "(.*)"$') { String payload ->
    assert response == payload
}

And(~'^has a single result$') { ->
    def json = slurper.parseText(response)
    assert json.size() == 1
}

And(~'^the payload contains "(.*)"$') { String payload ->
    def json = slurper.parseText(response)
    if(json instanceof ArrayList) {
        assert payload == json.first().text
    } else {
        assert payload == json.text
    }
}

And(~'^the "([^"]*)" payload message is "([^"]*)"$') { String number, String payload ->
    def json = slurper.parseText(response)
    assert json.get(lineOrder[number]).text == payload
}

And(~'^the payload message "([^"]*)" has not been received$') { String payload ->
    def json = slurper.parseText(response)
    assert ! json.find { it.text == payload }
}