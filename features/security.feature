Feature: Security

  Scenario: The Announce endpoints can NOT be Accessed when not Authorised
    Given the user is not Authenticated and has no Bearer Token
    And a new free form message "This is a free form message" to be announced
    When the free form message is announced
    Then an "Unauthorized" status is returned

  Scenario: The Announce endpoints CAN be Accessed when Authorised
    Given the user is Authenticated and issued a Bearer Token
    And a new free form message "This is a free form message" to be announced
    When the free form message is announced presenting the Bearer Token
    Then an "OK" status is returned

  Scenario: The Broadcast endpoints can always be Accessed without Authorisation
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When the latest message is requested with "Accept" header "application/json"
    And an "OK" status is returned
