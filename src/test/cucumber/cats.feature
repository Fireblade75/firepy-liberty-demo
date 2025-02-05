Feature: Cats

Scenario: Fetch all the cats
  When the url "CATS" is called
  Then the following list of Cats ir returned:
    | Name  | Bob   | Timmy | Liss  | Boris | 
    | Color | BLACK | BLACK | WHITE | BROWN |
    | Age   | 8     | 8     | 8     | 8     |

Scenario: Fetch a cat by name
  When the url "CATS" is called with the name "Timmy"
  Then the following list of Cats ir returned:
    | Name  | Timmy |
    | Color | BLACK |
    | Age   | 0     |

Scenario: Fetch a cat by a non existing name
  When the url "CATS" is called with the name "Charles"
  Then an empty list of Cats is returned 
