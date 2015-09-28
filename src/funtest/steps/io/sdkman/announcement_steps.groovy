package io.sdkman

import static cucumber.api.groovy.EN.And

And(~'^a new message to be announced for "([^"]*)" version "([^"]*)" hashtag "([^"]*)"$')
        { String msgCandidate, String msgVersion, String msgHashtag ->
    candidate = msgCandidate
    version = msgVersion
    hashtag = msgHashtag
}

And(~'^a new free form message "([^"]*)" to be announced$') { String message ->
    freeForm = message
}
