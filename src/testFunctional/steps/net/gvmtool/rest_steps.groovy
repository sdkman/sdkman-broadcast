import wslite.rest.RESTClientException

import static cucumber.api.groovy.EN.And

And(~'^the identifier of the latest message is requested$') { ->
    response = restClient.get(path: "/broadcast/latest/id").contentAsString
}

And(~'^the identifier of the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    response = restClient.get(path: "/broadcast/latest/id", headers: [(header): value]).contentAsString
}

And(~'^the latest message is requested$') { ->
    response = restClient.get(path: "/broadcast/latest").contentAsString
}

And(~'^the latest message is requested with "([^"]*)" header "([^"]*)"$') { String header, String value ->
    response = restClient.get(path: "/broadcast/latest", headers: [(header): value]).contentAsString
}

And(~'^the latest "([^"]*)" messages are requested$') { String limit ->
    response = restClient.get(path: "/broadcast/latest?limit=${limit}").contentAsString
}

And(~'^the latest "([^"]*)" messages are requested with "([^"]*)" header "([^"]*)"$') { String limit, String header, String value ->
    response = restClient.get(path: "/broadcast/latest?limit=${limit}", headers: [(header): value]).contentAsString
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

    } catch (RESTClientException re) {
        httpResponse = re.response
        response = httpResponse.statusMessage
        statusCode = httpResponse.statusCode
    }
}
