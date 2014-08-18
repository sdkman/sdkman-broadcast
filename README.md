# GVM Broadcast API

This is a dedicated microservice API used for Announcements on the GVM platform. It is used by GVM to announce breaking 
news about Candidate Releases and other important events. It's Broadcast Messages can be consumed through it's REST
endpoints, as is the case with the GVM Bash client. It also has the ability to publish it's Messages to social media 
sites such as Twitter.

## Content Negotiation

The default behaviour of the API is to return plain text responses which can be consumed by the Bash client. However,
it can also return JSON responses through content negotiation. By simply adding an `Accept` header of `application/json`
to your request, you should see the results in a JSON document.

## Interacting with the API

We have a [wiki page](https://github.com/gvmtool/gvm-broadcast-api/wiki/Public-Interface) detailing how to interact with
the API from Curl. Feel free to adapt these examples for your own use.

## Running it up locally

You will need to have MongoDB up and running locally on the default port.

    $ mongod

Once running, step into the project folder and run the tests.
 
    $ ./gradlew clean build

We can now run the app up locally with a simple

    $ java -jar build/libs/gvm-broadcast-api-1.0.0-SNAPSHOT.jar

## Environment Variables

The application can be configured by using environment variables.

#### MongoDB

`MONGO_HOST`: Host

`MONGO_PORT`: Port

`MONGO_DB_NAME`: Database Name

`MONGO_USERNAME`: Username

`MONGO_PASSWORD`: Password

#### OAuth

`CLIENT_ID`: Client Application Id

`CLIENT_SECRET`: Client Application Secret

`AUTH_USERNAME`: Admin Authentication Username

`AUTH_PASSWORD`: Admin Authentication Password

#### Twitter

`TWITTER_CONSUMER_KEY`: Twitter Consumer Key

`TWITTER_CONSUMER_SECRET`: Twitter Consumer Secret

`TWITTER_ACCESS_TOKEN`: Twitter Access Token

`TWITTER_ACCESS_TOKEN_SECRET`: Twitter Access Token Secret
