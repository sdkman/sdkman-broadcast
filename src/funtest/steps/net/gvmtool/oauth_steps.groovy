package net.gvmtool

import static cucumber.api.groovy.EN.And

And(~'^the user is Authorised to Announce$') { ->
    token = "access_token"
}

And(~'^the user is not Authorised to Announce$') { ->
    token = "invalid_token"
}