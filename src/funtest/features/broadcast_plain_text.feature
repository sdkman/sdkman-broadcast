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

Feature: Broadcast Plain Text

  Scenario: Identify the latest Broadcast Message
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    And the message "Groovy 2.3.0-rc-1." on the date "May 11, 2014" with id "12344"
    When the identifier of the latest message is requested
    Then the content type is "text/plain"
    And the identifier is "12345"
    And an "OK" status is returned

  Scenario: Broadcast an existing Message by Identifier
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When a message is requested by identifier "12345"
    Then the content type is "text/plain"
    And only the message "Groovy 2.3.0!" is received
    And an "OK" status is returned

  Scenario: Broadcast a Message by invalid Identifier
    When a message is requested by identifier "999999999"
    Then the content type is "text/plain"
    And a "Not Found" message is received
    And a "NOT_FOUND" status is returned

  Scenario: Broadcast the latest 3 Messages by Default
    Given the message "Groovy 2.3.0 final has been released!" on the date "May 12, 2014"
    And the message "Groovy 2.3.0-rc-1 has been released." on the date "May 11, 2014"
    And the message "Groovy 2.3.0-beta-4 has been released." on the date "May 10, 2014"
    And the message "Groovy 2.3.0-beta-3 has been released." on the date "May 9, 2014"
    When the latest messages are requested
    Then the content type is "text/plain"
    And a total of 3 messages has been received
    And the message "* 12/05/14: Groovy 2.3.0 final has been released!" is received "first"
    And the message "* 11/05/14: Groovy 2.3.0-rc-1 has been released." is received "second"
    And the message "* 10/05/14: Groovy 2.3.0-beta-4 has been released." is received "third"
    And the message "* 09/05/14: Groovy 2.3.0-beta-3 has been released." has not been received
    And an "OK" status is returned

  Scenario: Broadcast the latest 2 Messages explicitly
    Given the message "Groovy 2.3.0 final has been released!" on the date "May 12, 2014"
    And the message "Groovy 2.3.0-rc-1 has been released." on the date "May 11, 2014"
    And the message "Groovy 2.3.0-beta-4 has been released." on the date "May 10, 2014"
    When the latest "2" messages are requested
    Then the content type is "text/plain"
    And a total of 2 messages has been received
    And the message "* 12/05/14: Groovy 2.3.0 final has been released!" is received "first"
    And the message "* 11/05/14: Groovy 2.3.0-rc-1 has been released." is received "second"
    And the message "* 10/05/14: Groovy 2.3.0-beta-4 has been released." has not been received
    And an "OK" status is returned