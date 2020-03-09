Feature: Google Search

@SmokeTest
Scenario Outline: Search for state capitals
Given the student selects to search using Google
When the student searches for capital of "<state>"
Then search results are displayed using "<capital>"
  
Examples:
    |    state      | capital    |
    |    Iowa       | Des Moines |
    |   Kansas      | Topeka     |
