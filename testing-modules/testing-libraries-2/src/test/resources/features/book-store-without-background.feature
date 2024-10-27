Feature: Book Store Without Background
  Scenario: Find books by author
    Given I have the following books in the store
      | The Devil in the White City          | Erik Larson |
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
      | In the Garden of Beasts              | Erik Larson |
    When I search for books by author Erik Larson
    Then I find 2 books

  Scenario: Find books by author, but isn't there
    Given I have the following books in the store
      | The Devil in the White City          | Erik Larson |
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
      | In the Garden of Beasts              | Erik Larson |
    When I search for books by author Marcel Proust
    Then I find 0 books

  Scenario: Find book by title
    Given I have the following books in the store
      | The Devil in the White City          | Erik Larson |
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
      | In the Garden of Beasts              | Erik Larson |
    When I search for a book titled The Lion, the Witch and the Wardrobe
    Then I find a book

  Scenario: Find book by title, but isn't there
    Given I have the following books in the store
      | The Devil in the White City          | Erik Larson |
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
      | In the Garden of Beasts              | Erik Larson |
    When I search for a book titled Swann's Way
    Then I find no book