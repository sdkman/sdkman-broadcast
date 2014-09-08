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

Feature: Backward Compatibility

  Scenario: Accept broadcast calls by Prod version number as ID
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "1.3.13"
    When a message is requested by identifier "1.3.13"
    Then only the message "Groovy 2.3.0!" is received
    And the content type is "text/plain"
    And an "OK" status is returned

  Scenario: Accept broadcast calls by Dev Build number as ID
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "1.0.0-build-226"
    When a message is requested by identifier "1.0.0-build-226"
    Then only the message "Groovy 2.3.0!" is received
    And the content type is "text/plain"
    And an "OK" status is returned
