Feature: Broadcast

  @wip
  Scenario: Broadcast a message
    Given the message "This is a LIVE Broadcast!"
    When a broadcast message is requested
    Then the broadcast message "This is a LIVE Broadcast!" is received