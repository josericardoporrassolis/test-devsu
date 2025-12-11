Feature: Account API integration tests

  Background:
    * url karate.properties['baseUrl']
    * configure headers = { 'Content-Type': 'application/json', 'Authorization': authHeader }

  Scenario: should create account
    Given path '/api/cuentas'
    And request { accountNumber: 'ACC123', accountType: 'Savings', initialBalance: 1000.0, status: true, "personId": 1 }
    When method post
    Then status 201
    * def created = response
    * print created
    And match created.accountNumber == 'ACC123'
    And match created.personId == 1

  Scenario: should get all accounts by accountNumber
    Given path '/api/cuentas'
    And request { accountNumber: 'ACC456', accountType: 'Checking', initialBalance: 500.0, status: true, "personId": 1 }
    When method post
    Then status 201
    * def created = response

    Given path '/api/cuentas', created.accountNumber
    When method get
    Then status 200
    * print response
    And match response[0].accountNumber == 'ACC456'

  Scenario: should update account
    Given path '/api/cuentas'
    And request { accountNumber: 'ACC789', accountType: 'Savings', initialBalance: 200.0, status: true, "personId": 1 }
    When method post
    Then status 201
    * def created = response
    * print created

    * set created.status = false

    Given path '/api/cuentas', created.accountNumber
    And request created
    When method put
    Then status 200
    * print response
    And match response.status == false
