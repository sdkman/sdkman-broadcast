package net.gvmtool

import static cucumber.api.groovy.EN.And
import static utils.HttpHelper.obtainBearerToken

And(~'^the user is Authenticated and issued a Bearer Token$') { ->
    token = obtainBearerToken()
    println "Bearer Token: $token"
}

And(~'^the user is not Authenticated and has no Bearer Token$') { ->
    token = ""
}