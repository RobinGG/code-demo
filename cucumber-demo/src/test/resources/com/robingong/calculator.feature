Feature: Basic functions

  Background:
    Given I reset calculator

  Scenario: I test add
    When I calculate 1 and 1
    Then I should get 2
    But I shouldn't get 0

  Scenario: I test minus
    When I calculate 1 minus 1
    Then I should get 0

  Scenario Outline: I test add multi times
    When I calculate <first> and <second>
    Then I should get <result>

    Examples:
      | first | second | result |
      | 1     | 1      | 2      |
      | 0     | 0      | 0      |
      | 0     | -1     | -1     |

  Scenario Outline: I test add and minus at once
    When I calculate <first> <operator> <second>
    Then I should get <result>

    Examples:
      | first | operator | second | result |
      | 0     | and      | 1      | 1      |
      | 1     | and      | 1      | 2      |
      | 0     | minus    | 0      | 0      |
      | 1     | minus    | 1      | 0      |
      | 0     | minus    | 1      | -1     |

