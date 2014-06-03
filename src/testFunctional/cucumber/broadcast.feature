Feature: Broadcast

  Scenario: Broadcast a message
    Given the message "Welcome to GVM!"
    When the latest broadcast message is requested
    Then the broadcast message "Welcome to GVM!" is received

  Scenario: Broadcast the latest 3 message
    Given the message "Groovy 2.3.0 final has been released!" on the date "May 12, 2014"
    And the message "Groovy 2.3.0-rc-1 has been released." on the date "May 11, 2014"
    And the message "Groovy 2.3.0-beta-4 has been released." on the date "May 10, 2014"
    And the message "Groovy 2.3.0-beta-3 has been released." on the date "May 9, 2014"
    And the message "Groovy 2.3.0-beta-2 has been released." on the date "May 8, 2014"
    And the message "Groovy 2.3.0-beta-1 has been released." on the date "May 7, 2014"
    When the latest "3" broadcast messages are requested
    Then the broadcast message "Groovy 2.3.0 final has been released!" is received "first"
    And the broadcast message "Groovy 2.3.0-rc-1 has been released." is received "second"
    And the broadcast message "Groovy 2.3.0-beta-4 has been released." is received "third"
    And the broadcast message "Groovy 2.3.0-beta-3 has been released." has not been received