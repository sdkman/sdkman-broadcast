package net.gvmtool

import static cucumber.api.groovy.EN.And

And(~'^a new message to be announced for "([^"]*)" version "([^"]*)"$') { String msgCandidate, String msgVersion ->
    candidate = msgCandidate
    version = msgVersion
}

And(~'^a new free form message "([^"]*)" to be announced$') { String message ->
    freeForm = message
}