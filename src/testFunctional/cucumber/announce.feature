Feature: Announce

  Scenario: Announce a new Structured Message
    Given a new message to be announced for "groovy" version "2.3.0"
    When the structured message is announced
    Then the message "Groovy 2.3.0 has been released." is available