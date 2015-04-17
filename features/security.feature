#
#  Copyright 2014 Marco Vermeulen
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

Feature: Security

  Scenario: The Announce Freeform endpoints can NOT be Accessed with incorrect Access Token
    Given the user does not have a valid token header
    And a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "FORBIDDEN" status is returned

  Scenario: The Announce Freeform endpoints CAN be Accessed with correct Access Token
    Given the user has a valid token header
    And a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "OK" status is returned

  Scenario: The Announce Structured endpoints can NOT be Accessed with incorrect Access Token
    Given the user does not have a valid token header
    And the user has a "invalid" consumer header
    And a new message to be announced for "groovy" version "2.4.1" hashtag "#groovylang"
    When the structured message is announced
    Then a "FORBIDDEN" status is returned

  Scenario: The Announce Structured endpoints can NOT be Accessed with incorrect Consumer header
    Given the user has a "invalid" consumer header
    And the user has a valid token header
    And a new message to be announced for "groovy" version "2.4.1" hashtag "#groovylang"
    When the structured message is announced
    Then a "FORBIDDEN" status is returned

  Scenario: The Announce Structured endpoints CAN be Accessed with valid token and consumer headers
    Given the user has a valid token header
    And the user has a "groovy" consumer header
    And a new message to be announced for "groovy" version "2.4.1" hashtag "#groovylang"
    When the structured message is announced
    Then an "OK" status is returned

  Scenario: The Announce Structured endpoints CAN be Accessed with valid token and admin consumer headers
    Given the user has a valid token header
    And the user has a "default_admin" consumer header
    And a new message to be announced for "groovy" version "2.4.1" hashtag "#groovylang"
    When the structured message is announced
    Then an "OK" status is returned

  Scenario: The Broadcast endpoints can always be Accessed without Authorisation
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When the latest message is requested with "Accept" header "application/json"
    And an "OK" status is returned
