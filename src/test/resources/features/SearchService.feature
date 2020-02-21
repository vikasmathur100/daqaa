Feature: Google Searching

@SmokeTest
Scenario Outline: Simple Google search
Given the user selects to search using Google
When the user searches for "<phrase>"
Then search results are displayed using "<phrase>"
  
Examples:
    |    phrase     |
    |    DAQAA      |
