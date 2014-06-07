Feature: Broadcast

  Scenario: Identify the latest Broadcast Message
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    And the message "Groovy 2.3.0-rc-1." on the date "May 11, 2014" with id "12344"
    When the identifier of the latest message is requested
    Then the identifier is "12345"

  Scenario: Broadcast an existing Message by Identifier
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When a message is requested by identifier "12345"
    Then the message "Groovy 2.3.0!" is received
    And an "OK" status is returned

  Scenario: Broadcast a Message by invalid Identifier
    When a message is requested by identifier "999999999"
    Then a "Not Found" message is received
    And a "NOT_FOUND" status is returned

  Scenario: Broadcast a Message
    Given the message "Welcome to GVM!"
    When the latest message is requested
    Then the message "Welcome to GVM!" is received

  Scenario: Broadcast the latest 3 Messages
    Given the message "Groovy 2.3.0 final has been released!" on the date "May 12, 2014"
    And the message "Groovy 2.3.0-rc-1 has been released." on the date "May 11, 2014"
    And the message "Groovy 2.3.0-beta-4 has been released." on the date "May 10, 2014"
    And the message "Groovy 2.3.0-beta-3 has been released." on the date "May 9, 2014"
    And the message "Groovy 2.3.0-beta-2 has been released." on the date "May 8, 2014"
    And the message "Groovy 2.3.0-beta-1 has been released." on the date "May 7, 2014"
    When the latest "3" messages are requested
    Then the message "Groovy 2.3.0 final has been released!" is received "first"
    And the message "Groovy 2.3.0-rc-1 has been released." is received "second"
    And the message "Groovy 2.3.0-beta-4 has been released." is received "third"
    And the message "Groovy 2.3.0-beta-3 has been released." has not been received