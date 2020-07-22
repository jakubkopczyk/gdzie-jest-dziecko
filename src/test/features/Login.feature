@Login
  Feature: Login
    Scenario: Login with an already created account
      When I launch the application
      And I tap on the "SKIP" button on the "Welcome" page
      And I login using data from the properties
      Then "Home" page is displayed