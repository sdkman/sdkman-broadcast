# SDKMAN! Broadcast API

This is a dedicated microservice API used for Broadcast Messages on the SDKMAN! platform. It is used by SDKMAN! to announce breaking
news about Candidate Releases and other important events. It's Broadcast Messages can be consumed through it's REST
endpoints, as is the case with the SDKMAN! Bash client.

## Content Negotiation

The default behaviour of the API is to return plain text responses which can be consumed by the Bash client. However,
it can also return JSON responses through content negotiation. By simply adding an `Accept` header of `application/json`
to your request, you should see the results in a JSON document.

## Running it up locally

You will need to have MongoDB up and running locally on the default port.

    $ docker run -d -p 27017:27017 mongo:latest

Next, assemble and run the application.

    $ ./gradlew clean assemble
    $ java -jar build/libs/application.jar

Once running, you are ready to run the full suite of tests.
 
    $ ./gradlew check
