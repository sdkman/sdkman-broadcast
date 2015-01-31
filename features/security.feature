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

  Scenario: The Announce endpoints can NOT be Accessed when not Authorised
    Given the user is not Authorised to Announce
    And a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "FORBIDDEN" status is returned

  Scenario: The Announce endpoints CAN be Accessed when Authorised
    Given the user is Authorised to Announce
    And a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "OK" status is returned

  Scenario: The Broadcast endpoints can always be Accessed without Authorisation
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When the latest message is requested with "Accept" header "application/json"
    And an "OK" status is returned
