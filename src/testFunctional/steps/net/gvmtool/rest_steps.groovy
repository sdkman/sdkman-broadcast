import wslite.rest.RESTClientException

import static cucumber.api.groovy.EN.And

And(~'^the identifier of the latest message is requested$') { ->
    httpGet({ restClient.get(path: "/broadcast/latest/id") })
}

And(~'^the identifier of the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    httpGet({ restClient.get(path: "/broadcast/latest/id", headers: [(header): value]) })
}

And(~'^the latest message is requested$') { ->
    httpGet({ restClient.get(path: "/broadcast/latest") })
}

And(~'^the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    httpGet({ restClient.get(path: "/broadcast/latest", headers: [(header): value]) })
}

And(~'^the latest "([^"]*)" messages are requested$') { String limit ->
    httpGet({ restClient.get(path: "/broadcast/latest?limit=${limit}") })
}

And(~'^the latest "([^"]*)" messages are requested with "([^"]*)" header "([^"]*)"$') { String limit, String header, String value ->
    httpGet({ restClient.get(path: "/broadcast/latest?limit=${limit}", headers: [(header): value]) })
}

And(~'^a message is requested by identifier "([^"]*)" with "([^"]*)" header "([^"]*)"$') { String id, String header, String value ->
    httpGet({ restClient.get(path: "/broadcast/$id", headers: [(header): value]) })
}

And(~'^a message is requested by identifier "([^"]*)"$') { String id ->
    httpGet({ restClient.get(path: "/broadcast/$id") })
}

private httpGet(restAction) {
    try {
        httpResponse = restAction()
        response = httpResponse.contentAsString
        statusCode = httpResponse.statusCode
        headers = httpResponse.headers

    } catch (RESTClientException re) {
        httpResponse = re.response
        response = httpResponse.statusMessage
        statusCode = httpResponse.statusCode
    }
}

And(~'^the structured message is announced$') { ->
    def httpResponse = restClient.post(path: "/announce") {
        type "application/json"
        json candidate: candidate, version: version
    }
    broadcastId = slurper.parseText(httpResponse.contentAsString).id
    println "Broadcast ID: $broadcastId"
    println "bye..."
}
