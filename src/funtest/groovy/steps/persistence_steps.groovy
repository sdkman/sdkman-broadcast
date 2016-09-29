package steps

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.*

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^the message "([^"]*)" on the date "([^"]*)"$') { String message, Date date ->
    insertBroadcastInDb(db, message, date)
}

And(~'^the message "([^"]*)" on the date "([^"]*)" with id "([^"]*)"$') { String message, Date date, String id ->
    insertBroadcastInDb(db, message, date, id)
}

And(~'^the message "([^"]*)" is available$') { String message ->
    assert readBroadcastById(db, broadcastId).text == message
}
