package steps

import static cucumber.api.groovy.EN.And

And(~'^a "([^"]*)" status is returned$') { String status ->
    assert status == statusCodes[statusCode]
}

And(~'^an "([^"]*)" status is returned$') { String status ->
    assert status == statusCodes[statusCode]
}

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

And(~'^a "([^"]*)" message is received$') { String message ->
    assert response == message
}

And(~'^a total of (\\d+) messages has been received$') { int number ->
    def lines = response.readLines()
    //substract 2 for header and footer
    assert (lines.size()) - 2 == number
}