package net.gvmtool

import wslite.rest.RESTClientException

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

lineOrder = ["first": 1, "second": 2, "third": 3, "forth": 4, "fifth": 5]
statusCodes = [200: "OK", 404: "NOT_FOUND"]

//arrange

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^the message "([^"]*)" on the date "([^"]*)"$') { String message, Date date ->
    insertBroadcastInDb(db, message, date)
}

And(~'^the message "([^"]*)" on the date "([^"]*)" with id "([^"]*)"$') { String message, Date date, int id ->
    insertBroadcastInDb(db, message, date, id)
}

//act

And(~'^the identifier of the latest message is requested$') { ->
    response = restClient.get(path: "/broadcast/id").contentAsString
}

And(~'^a message is requested by identifier "([^"]*)"$') { String id ->
    try {
        httpResponse = restClient.get(path: "/broadcast/$id")
        response = httpResponse.text
        statusCode = httpResponse.statusCode

    } catch (RESTClientException re) {
        httpResponse = re.response
        response = httpResponse.statusMessage
        statusCode = httpResponse.statusCode
    }
}

And(~'^the latest message is requested$') { ->
    response = restClient.get(path: "/broadcast").text
}

And(~'^the latest "([^"]*)" messages are requested$') { String limit ->
    response = restClient.get(path: "/broadcast?limit=${limit}").text
}

//assert

And(~'^the message "([^"]*)" is received$') { String message ->
    assert response.contains(message)
}

And(~'^the message "([^"]*)" is received "([^"]*)"$') { String message, String order ->
    def lines = response.readLines()
    assert lines[lineOrder[order]] == message
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