package net.gvmtool

import static cucumber.api.groovy.EN.And

And(~'^the user has a valid token header$') { ->
    token = "default_token"
}

And(~'^the user does not have a valid token header$') { ->
    token = "invalid_token"
}

And(~'^the user has a "(.*)" consumer header$') { String name ->
    consumer = name
}