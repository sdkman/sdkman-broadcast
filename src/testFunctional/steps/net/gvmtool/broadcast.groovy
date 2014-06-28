package net.gvmtool

import wslite.rest.RESTClientException

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

lineOrder = ["first": 0, "second": 1, "third": 2, "forth": 3, "fifth": 4]
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
    response = restClient.get(path: "/broadcast/latest/id").contentAsString
}

And(~'^the identifier of the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    response = restClient.get(path: "/broadcast/latest/id", headers: [(header): value]).contentAsString
}

And(~'^a message is requested by identifier "([^"]*)" with "([^"]*)" header "([^"]*)"$') { String id, String header, String value ->
    httpGet({ restClient.get(path: "/broadcast/$id", headers: [(header): value]) })
}

And(~'^a message is requested by identifier "([^"]*)"$') { String id ->
    httpGet({ restClient.get(path: "/broadcast/$id") })
}

private httpGet(restAction) {
    try {
        httpResponse = restAction()
        response = httpResponse.contentAsString
        statusCode = httpResponse.statusCode

    } catch (RESTClientException re) {
        httpResponse = re.response
        response = httpResponse.statusMessage
        statusCode = httpResponse.statusCode
    }
}

And(~'^the latest message is requested$') { ->
    response = restClient.get(path: "/broadcast/latest").contentAsString
}

And(~'^the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    response = restClient.get(path: "/broadcast/latest", headers: [(header): value]).contentAsString
}

And(~'^the latest "([^"]*)" messages are requested$') { String limit ->
    response = restClient.get(path: "/broadcast/latest?limit=${limit}").contentAsString
}

And(~'^the latest "([^"]*)" messages are requested with "([^"]*)" header "([^"]*)"$') { String limit, String header, String value ->
    response = restClient.get(path: "/broadcast/latest?limit=${limit}", headers: [(header): value]).contentAsString
}

//assert

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
    if(json instanceof ArrayList)
        assert payload == json.first().text
    else
        assert payload == json.text
}

And(~'^the "([^"]*)" payload message is "([^"]*)"$') { String number, String payload ->
    def json = slurper.parseText(response)
    assert json.get(lineOrder[number]).text == payload
}

And(~'^the payload message "([^"]*)" has not been received$') { String payload ->
    def json = slurper.parseText(response)
    assert ! json.find { it.text == payload }
}