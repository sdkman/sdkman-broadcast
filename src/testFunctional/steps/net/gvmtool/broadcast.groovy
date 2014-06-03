package net.gvmtool

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

lineOrder = ["first": 0, "second": 1, "third": 2, "forth": 3, "fifth": 4]

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^the latest broadcast message is requested$') { ->
    response = restClient.get(path: "/broadcast").text
}

And(~'^the latest "([^"]*)" broadcast messages are requested$') { String limit ->
    response = restClient.get(path: "/broadcast/$limit").text
}

And(~'^the broadcast message "([^"]*)" is received$') { String message ->
    assert response == message
}

And(~'^the message "([^"]*)" on the date "([^"]*)"$') { String message, Date date ->
    insertBroadcastInDb(db, message, date)
}

And(~'^the broadcast message "([^"]*)" is received "([^"]*)"$') { String message, String order ->
    def lines = response.readLines()
    assert lines[lineOrder[order]] == message
}

And(~'^the broadcast message "([^"]*)" has not been received$') { String message ->
    def lines = response.readLines()
    assert !lines.contains(message)
}