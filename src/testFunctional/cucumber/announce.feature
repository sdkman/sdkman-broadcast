Feature: Announce

  Background:
    Given the user is Authenticated and Authorised to Announce

  Scenario: Announce a new Structured Message
    Given a new message to be announced for "groovy" version "2.3.0"
    When the structured message is announced
    Then an "OK" status is returned
    And the content type is "application/json"
    And a valid Broadcast Identifier is returned
    And the message "Groovy 2.3.0 has been released." is available

  Scenario: Announce a new Free Form Message
    Given a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "OK" status is returned
    And the content type is "application/json"
    And a valid Broadcast Identifier is returned
    Then the message "This is a free form message" is available