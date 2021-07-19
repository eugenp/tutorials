Feature: Book Store With Hooks
  Background: The Book Store
    Given The following books are available in the store
      | The Devil in the White City          | Erik Larson |
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
      | In the Garden of Beasts              | Erik Larson |

  @Screenshots
  Scenario: 1 - Find books by author
    When I ask for a book by the author Erik Larson
    Then The salesperson says that there are 2 books

  Scenario: 2 - Find books by author, but isn't there
    When I ask for a book by the author Marcel Proust
    Then The salesperson says that there are 0 books


