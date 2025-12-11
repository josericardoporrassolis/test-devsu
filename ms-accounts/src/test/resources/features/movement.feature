Feature: Movement API integration tests

  Background:
    * url karate.properties['baseUrl']
    * configure headers = { 'Content-Type': 'application/json', 'Authorization': authHeader }

  Scenario: should create movement
    Given path '/api/cuentas'
    And request { accountNumber: 'ACC123', accountType: 'Checking', initialBalance: 500.0, status: true, personId: 1 }
    When method post
    Then status 201
    * def created = response

    Given path '/api/movimientos'
    And request { movementType: 'Deposit', value: 500.0, accountNumber: 'ACC123' }
    When method post
    Then status 201
    * def created = response
    * print created
    And match created.movementType == 'Deposit'
    And match created.value == 500.0
    And match created.accountNumber == 'ACC123'

  Scenario: should update movement
    Given path '/api/movimientos'
    And request { movementType: 'Withdrawal', value: -200.0, accountNumber: 'ACC123' }
    When method post
    Then status 201
    * def created = response
    * print created

    * set created.value = -300.0
    * set created.movementType = 'Withdrawal'

    Given path '/api/movimientos', created.movementId
    And request created
    When method put
    Then status 200
    * print response
    And match response.value == -300.0
    And match response.movementType == 'Withdrawal'

  Scenario: should reject withdrawal when insufficient balance
    Given path '/api/cuentas'
    And request { accountNumber: 'ACC456', accountType: 'Checking', initialBalance: 500.0, status: true, personId: 1 }
    When method post
    Then status 201
    * def created = response

    Given path '/api/movimientos'
    And request { movementType: 'Withdrawal', value: -100000.0, accountNumber: 'ACC456' }
    When method POST
    Then status 400
    And match response.message == 'Saldo no disponible'

  Scenario: should register valid withdrawal and update balance
    Given path '/api/cuentas'
    And request { accountNumber: 'ACC456', accountType: 'Checking', initialBalance: 0.0, status: true, personId: 1 }
    When method post
    Then status 201

    Given path '/api/movimientos'
    And request { movementType: 'Deposit', value: 500.0, accountNumber: 'ACC456' }
    When method post
    Then status 201
    * def deposito = response

    Given path '/api/movimientos'
    And request { movementType: 'Withdrawal', value: -200.0, accountNumber: 'ACC456' }
    When method post
    Then status 201
    * def retiro = response
    * print retiro
    And match retiro.balance == 300.0
