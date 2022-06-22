Feature: Test Automation - Balsam Hill
  Scenario: Successful filtering of Undecorated Artificial Christmas Trees sorted by Price Low to High
    Given Testcase "tc1" from sheet "Testdata", Artificial Christmas Trees page is displayed
    When I choose "menu" and click "subMenu" and filter using "filter"
    And I check search results
    Then Print the 1st, 3rd, and 4th products on the list