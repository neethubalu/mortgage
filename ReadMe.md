# MORTGAGE MICROSERVICE

This application is the backend service for a mortgage platform which supports CRUD operations using RESTful webservices. The application is developed using Java, Spring Boot and in-memory H2 database. Following are the major features of the application.
* User can `create` a customer.
* User can `create` a mortgage for one or more customers.
* User can `find` all customers or find one customer with account number.
* User can `find` all mortgages or find one mortgage with mortgage ID.
* User can `add` any number of customers to mortgages.
* User can `update` customer details.
* User can `update` mortgage details.
* User can `delete` customer if there are no mortgage associated with the account id.
* User can `delete` mortgage if there are no customers associated with mortgage id.
* User can `delete` mortgage for a customer.

### Customer

| Method | Path                                  | Description                                                                        |
|--------|---------------------------------------|------------------------------------------------------------------------------------|
| POST   | /rabo/customer/create                 | Creates a customer, including only the `name` , `address` and Autogenerated `UUID` |
| GET    | /rabo/customer/get                    | Retrieves full details of all customers                                            |
| GET    | /rabo/customer/get/{accountNumber}    | Retrieves the full details of a single customer using `accountNumber`              |
| DELETE | /rabo/customer/delete/                | Deletes a mortgage for customer using `accountNumber` and `mortgageId`             |
| DELETE | /rabo/customer/delete/{accountNumber} | Deletes a customer using `accountNumber` when there is no mortgages are associated |
| PUT    | /rabo/customer/update/                | updates a customer detail. Only address is allowed to update.                      |

### Mortgage

| Method | Path                               | Description                                                                                     |
|--------|------------------------------------|-------------------------------------------------------------------------------------------------|
| POST   | /rabo/mortgage/create              | Creates a mortgage, including only the mortgage details                                         |
| GET    | /rabo/mortgage/get                 | Retrieves full details of all the mortgages                                                     |
| GET    | /rabo/mortgage/get/{mortgageId}    | Retrieves the full details of a single mortgage using `mortgageId`                              |
| DELETE | /rabo/mortgage/delete/{mortgageId} | Deletes a mortgage. Only if there are no customers associated with the `mortgageId`             |
| PUT    | /rabo/mortgage/update/             | Updates mortgage details . Mortgage Id cannot be updated                                        |
| PUT    | /rabo/mortgage/addCustomer/        | Updates a mortgage adding existing customers to mortgage using `accountNumber` and `mortgageId` |



## Steps to install

1. Pull the repository into your local drive.
2. Project is built on `JDK 21` and `Spring boot 3.2.2`
3. `application.properties` is kept along with the project 
4. This project uses `In-memory database - H2 `
5. Run the application using mvn `spring-boot:run` or from your IDE.

## Postman Collection
A postman collection `Mortgage_Application_Postman_Collection.json` is included in the projects folder which could be used to test
and validate once the API is up and running.

## Note
1. Initial Data is loaded on startup
2. There will be few customers and mortgage associated with those customers on startup
3. Please use appropriate AccountID or Mortgage ID in the param or request body as it is UUID
