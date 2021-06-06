# Transactions

_Technologies used_

- Java 11
- Spring Boot 2.5
- H2 Database
- Lombok
- Junit 5
- Mockito
- Swagger
- Docker

## Run Application

To start the project `transactions` is required have [Docker](https://docs.docker.com/engine/install/) installed.

Then, simply run: [sh start-docker-application.sh](./start-docker-application.sh)

Followed the steps, the application is avaliable in `localhost:8080/`

## Database

After start up application the database is avaliable on `localhost:8080/h2-console/` with the params:
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:transactions`
- User Name: `root`
- Password: `root`


## API Documentation

After start up application check `localhost:8080/swagger-ui.html`

YAML example:
```yaml
openapi: 3.0.1
info:
  title: Transactions API
  version: v0
servers:
- url: http://localhost:8080
paths:
  /transactions:
    post:
      tags:
      - transaction-resource
      operationId: post
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/TransactionDTO'
        required: true
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: integer
                format: int64
  /accounts:
    post:
      tags:
      - account-resource
      operationId: post_1
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Account'
        required: true
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: integer
                format: int64
  /accounts/{accountId}:
    get:
      tags:
      - account-resource
      operationId: findById
      parameters:
      - name: accountId
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Account'
components:
  schemas:
    TransactionDTO:
      required:
      - accountId
      - amount
      - operationTypeId
      type: object
      properties:
        accountId:
          type: integer
          format: int64
        operationTypeId:
          type: integer
          format: int64
        amount:
          type: number
    Account:
      required:
      - documentNumber
      type: object
      properties:
        id:
          type: integer
          format: int64
        documentNumber:
          type: string
``` 


