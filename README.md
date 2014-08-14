# GVM Broadcast API

This is a dedicated microservice API used for Announcements on the GVM platform. It is used by GVM to announce breaking 
news about Candidate Releases and other important events. It's Broadcast Messages can be consumed through it's REST
endpoints, as is the case with the GVM Bash client. It also has the ability to publish it's Messages to social media 
sites such as Twitter.

## Content Negotiation

The default behaviour of the API is to return plain text responses which can be consumed by the Bash client. However,
it can also return JSON responses through content negotiation. By simply adding an `Accept` header of `application/json`
to your request, you should see the results in a JSON document.

## Available Endpoints

Some endpoints are exposed publicly, while others require an authentication token.

### Open Endpoints

The following endpoints do not require any authentication:

#### Broadcast Related

Gets the latest Broadcast Message available on the platform. Can also return more Messages by passing in a `limit`
query parameter. When querying for multiple results, the order will always be in datetime descending.

##### Plain Text Default

    $ curl -I http://hostname/broadcast/latest
    
    ==== BROADCAST =================================================================
    * 07/08/14: Welcome to the brand new GVM Broadcast API!
    ================================================================================%                               

##### JSON with Accept Header
    
    $ curl -H "Accept:application/json" http://hostname/broadcast/latest
    
    [
        {
            "date": 1407451262797,
            "id": "53e4007ef58edee9442471d6",
            "text": "Welcome to the brand new GVM Broadcast API!"
        }
    ]
    
##### JSON using limit request parameter
    
    $ curl -H "Accept:application/json" http://hostname/broadcast/latest?limit=2
    [
        {
            "date": 1407451263897,
            "id": "93e90079f5ab12eda42941e3",
            "text": "Groovy 2.4.0 has been released."
        },
        {
            "date": 1407451262797,
            "id": "53e4007ef58edee9442471d6",
            "text": "Welcome to the brand new GVM Broadcast API!"
        }
    ]

##### Identify the latest broadcast message

    $ curl -H "Accept:application/json" http://hostname/broadcast/latest/id

    {"id":"53e4007ef58edee9442471d6"}

##### Retrieve broadcast messages by identifier

    $ curl -H "Accept:application/json" http://hostname/broadcast/53e4007ef58edee9442471d6

    {"id":"53e4007ef58edee9442471d6","text":"Welcome to the brand new GVM Broadcast API!","date":1407451262797}


### Secured Endpoints

#### Get authorisation token

    $ curl -X POST -u client_id:client_secret \
        https://hostname/oauth/token \
        -H "Accept: application/json" \
        -d "password=auth_password&username=auth_username&grant_type=password&scope=read%20write&client_secret=client_secret&client_id=client_id"
    
    {"access_token":"XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX","token_type":"bearer","expires_in":31698,"scope":"read write"}

#### Announcement Related

##### Announcing free-form message

    $ curl -X POST -H "Content-Type: application/json" \
        -H "Authorization:Bearer XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX" \
        -d '{"text":"Announcement made from curl."}' \
        https://hostname/announce/freeform

    {"id":"53ed20e3de2e3153bad84100"}

##### Announcing new release candidates

    $ curl -X POST -H "Content-Type: application/json" \
        -H "Authorization:Bearer XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX" \
        -d '{"candidate":"Groovy.", "version":"2.4.0"}' \
        https://cast-dev.cfapps.io/announce/struct

    {"id":"53ed2162de2e3153bad84101"}
    
