Feature: Broadcast

  @wip
  Scenario: Broadcast a message
    Given the message "Welcome to GVM!"
    When a broadcast message is requested
    Then the broadcast message "Welcome to GVM!" is received