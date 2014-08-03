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
