package net.gvmtool

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^a broadcast message is requested$') { ->
    response = restClient.get(path:"/broadcast").text
}

And(~'^the broadcast message "([^"]*)" is received$') { String message ->
    assert response == message
}