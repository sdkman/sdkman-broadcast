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

Feature: Announce

  Background:
    Given the user has a valid token header
    And the user has a "groovy" consumer header

  Scenario: Announce a new Structured Message
    Given a new message to be announced for "groovy" version "2.3.0" hashtag "#groovylang"
    When the structured message is announced
    Then an "OK" status is returned
    And the content type is "application/json"
    And a valid Broadcast Identifier is returned
    And the message "Groovy 2.3.0 released on SDKMAN! #groovylang" is available

  Scenario: Announce a new Free Form Message
    Given a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "OK" status is returned
    And the content type is "application/json"
    And a valid Broadcast Identifier is returned
    Then the message "This is a free form message" is available
