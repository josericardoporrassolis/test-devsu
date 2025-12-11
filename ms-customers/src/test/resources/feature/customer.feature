Feature: Customer API integration tests

Background:
  * url karate.properties['baseUrl']
  * configure headers = { 'Content-Type': 'application/json', 'Authorization': authHeader }

Scenario: should create customer
  Given path '/api/clientes'
  And request { "name": "JR Porras", "gender": "Male", "age": 33, "identification": "123456789", "address": "CR", "phone": "50612345678", "password": "123", "status": true, "email": "12@gmail.com"}
  When method post
  Then status 201
  And match response.name == 'JR Porras'
  And match response.identification == '123456789'

Scenario: should get all customers
  Given path '/api/clientes'
  When method get
  Then status 200
  * def created = response
  * print created

Scenario: should get customer by id
  Given path '/api/clientes'
  And request { "name": "JR Porras", "gender": "Male", "age": 33, "identification": "987654321", "address": "CR", "phone": "50612345678", "password": "123", "status": true, "email": "123@gmail.com"}
  When method post
  Then status 201
  * def created = response
  * print created

  Given path '/api/clientes', created.id
  When method get
  Then status 200
  * print response

  Scenario: should update customer
    Given path '/api/clientes'
    And request { "name": "JR Porras", "gender": "Male", "age": 33, "identification": "0000123", "address": "CR", "phone": "50612345678", "password": "123", "status": true, "email": "1234@gmail.com" }
    When method post
    Then status 201
    * def created = response
    * print created

    * set created.name = 'Carlos Updated'

    Given path '/api/clientes', created.id
    And request created
    When method put
    Then status 200
    And match response.name == 'Carlos Updated'

Scenario: should delete customer
  Given path '/api/clientes'
  And request { name: 'Ana', gender: 'F', age: 28, identification: '111111111', address: 'Cartago', phone: '99999999', password: 'pwd', status: true, "email": "12345@gmail.com" }
  When method post
  Then status 201
  * def created = response
  * print created

  Given path '/api/clientes', created.id
  When method delete
  Then status 204

  Given path '/api/clientes', created.id
  When method delete
  Then status 404

  Scenario: should get customer by name
    Given path '/api/clientes'
    And request { "name": "JR", "gender": "Male", "age": 33, "identification": "954100004", "address": "CR", "phone": "50612345678", "password": "123", "status": true, "email": "123456@gmail.com" }
    When method post
    Then status 201
    * def created = response
    * print created

    Given path '/api/clientes/search'
    And param name = 'JR'
    When method get
    Then status 200
    And match response.id != null
    And match response.name == 'JR'
    And match response.email contains '@'
    * print 'Customer found:', response