Feature: Conversation api

  Scenario: a full e2e test example
    Given mock backend responds with
      """
      {"text":"hello testing!"}
      """
    When client calls GET /conversation?q=test123
    Then response code is 200
    And response content type contains application/json
    And response body is
      """
      {"message":{"text":"hello testing!"},"topic":"Conversation for keyword test123"}
      """
    And recorded request was GET /message?query=test123
