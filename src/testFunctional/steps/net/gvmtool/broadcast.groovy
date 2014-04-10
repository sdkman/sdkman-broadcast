package net.gvmtool

import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

buildBroadcast = { broadcastMessage ->
"""
==== BROADCAST =================================================================

${broadcastMessage}

Running GVM server 1.0.0-SNAPSHOT on vertx 1.3.1.final

================================================================================
"""
}

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^a broadcast message is requested$') { ->
    response = restClient.get(path:"/broadcast").text
}

And(~'^the broadcast message "([^"]*)" is received$') { String message ->
    assert response == buildBroadcast(message)
}