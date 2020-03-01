Feature: Google Searchin

@SmokeTest
Scenario Outline: Search for state capitals
Given the user selects to search using Google
When the user searches for capital of "<state>"
Then search results are displayed using "<capital>"
  
Examples:
    |    state      | capital    |
    |    Iowa       | Des Moines |
    |   Kansas      | Topeka     |
