package net.gvmtool

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

lineOrder = ["first": 1, "second": 2, "third": 3, "forth": 4, "fifth": 5]

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^the message "([^"]*)" on the date "([^"]*)"$') { String message, Date date ->
    insertBroadcastInDb(db, message, date)
}

And(~'^the message "([^"]*)" on the date "([^"]*)" with id "([^"]*)"$') { String message, Date date, String id ->
    insertBroadcastInDb(db, message, date, id)
}

And(~'^the identifier of the latest broadcast message is requested$') { ->
    response = restClient.get(path: "/broadcast/id").text
}

And(~'^the latest broadcast message is requested$') { ->
    response = restClient.get(path: "/broadcast").text
}

And(~'^the latest "([^"]*)" broadcast messages are requested$') { String limit ->
    response = restClient.get(path: "/broadcast?limit=${limit}").text
}

And(~'^the broadcast message "([^"]*)" is received$') { String message ->
    assert response.contains(message)
}

And(~'^the broadcast message "([^"]*)" is received "([^"]*)"$') { String message, String order ->
    def lines = response.readLines()
    assert lines[lineOrder[order]] == message
}

And(~'^the broadcast message "([^"]*)" has not been received$') { String message ->
    def lines = response.readLines()
    assert !lines.contains(message)
}

And(~'^the identifier is "([^"]*)"$') { String uid ->
    assert response == uid
}