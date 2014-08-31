package utils

import groovy.json.JsonSlurper
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

class HttpHelper {

    static slurper = new JsonSlurper()

    static client = new RESTClient("http://localhost:8080")

    static obtainBearerToken() {
        client.authorization = new HTTPBasicAuthorization("client_id", "client_secret")
        def oauthResp = client.post(path: "/oauth/token") {
            type "application/x-www-form-urlencoded"
            text "password=auth_password&username=auth_username&grant_type=password&scope=read%20write&client_secret=client_secret&client_id=client_id"
        }
        slurper.parseText(oauthResp.contentAsString).access_token
    }

    static post(map, closure) {
        client.post(map, closure)
    }

    static get(map) {
        client.get(map)
    }

}
