package net.gvmtool

import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

import static cucumber.api.groovy.EN.And

And(~'^the user is Authenticated and Authorised to Announce$') { ->
    RESTClient client = new RESTClient("http://localhost:8080")
    client.authorization = new HTTPBasicAuthorization("clientapp", "123456")
    def oauthResp = client.post(path: "/oauth/token") {
        type "application/x-www-form-urlencoded"
        text "password=spring&username=roy&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
    }
    token = slurper.parseText(oauthResp.contentAsString).access_token
    println "Bearer Token: $token"
}

And(~'^the user is not Authenticated and Authorised$') { ->
    token = "invalid_token"
}