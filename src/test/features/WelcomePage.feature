@WelcomePage
Feature: WelcomePage
  Scenario: Welcome page validations
    When I launch the application
    Then I can see "Lokalizuj Bliskich i bądź spokojny o ich bezpieczeństwo." description
    When I swipe "left" "1" times
    Then I can see "Otrzymuj automatyczne powiadomienia, kiedy Bliski dotrze na miejsce." description
    When I swipe "left" "1" times
    Then I can see "Przeglądaj historię lokalizacji i zobacz, gdzie był Bliski." description
    When I swipe "left" "1" times
    Then I can see "Reaguj na wezwanie pomocy od Bliskiego." description
    When I swipe "right" "3" times
    Then I can see "Lokalizuj Bliskich i bądź spokojny o ich bezpieczeństwo." description
    When I tap on the "NEXT" button on the "Welcome" page
    Then I can see "Otrzymuj automatyczne powiadomienia, kiedy Bliski dotrze na miejsce." description
    When I tap on the "NEXT" button on the "Welcome" page
    Then I can see "Przeglądaj historię lokalizacji i zobacz, gdzie był Bliski." description
    When I tap on the "NEXT" button on the "Welcome" page
    Then I can see "Reaguj na wezwanie pomocy od Bliskiego." description
    When I tap on the "NEXT" button on the "Welcome" page