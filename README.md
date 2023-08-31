# cards
Card API Release Notes

## System Requirements

1. Java 17 SDK
2. Maven : Version 3.8 and above
3. MySQL


##Instructions : 

Create a MySQL Database with the following details

  database : cardib
  user: [set-your-installed-username]
  pass: [set-your-installed-password]

Pull the code :

  git clone https://github.com/infilitry/cards

Edit cards/src/main/resources/application.properties file. Set your mysql username and password

Run the following build command :

  mvn clean install.

To run the application, run the following command :

  mvn spring-boot:run

Attached you will find a postman collection that will list all the APIs you can run :

##Registering a User : 

curl --location 'http://localhost:8080/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email" : "tester5@testing.com",
    "password" : "12345678",
    "role" : ["user", "admin"]
}'


##Login in a User :

curl --location 'http://localhost:8080/v1/auth/signin' \
--header 'Content-Type: application/json' \
--header 'Cookie: bezkoder=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDExMDM1LCJleHAiOjE2OTM0MTEwNjV9.83cCSpLTp1Ue9uGG3uhSE07dhhwuGvG8MgBDIvogLrc' \
--data-raw '{
    "email" : "tester5@testing.com",
    "password" : "12345678"
}'

You will get the following payload as a response : 

{
    "id": 2,
    "email": "tester5@testing.com",
    "roles": [
        "ROLE_USER",
        "ROLE_ADMIN"
    ],
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDE3NDY1LCJleHAiOjE2OTM1MDM4NjV9.Ysu-FDfnNVVSxVAb0DTCNL4cNrMBPH-i00eEjiZbgww"
}


##Cards API

For making subsequent calls you need to copy the value of the accessToken and add it as a Authorization Bearer header in the calls : 

Creating Card : 

curl --location 'http://localhost:8080/v1/card' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDE3NDY1LCJleHAiOjE2OTM1MDM4NjV9.Ysu-FDfnNVVSxVAb0DTCNL4cNrMBPH-i00eEjiZbgww' \
--header 'Cookie: bezkoder=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDExMDM1LCJleHAiOjE2OTM0MTEwNjV9.83cCSpLTp1Ue9uGG3uhSE07dhhwuGvG8MgBDIvogLrc' \
--data '{
    "name" : "testing23",
    "color" : "BLUE",
    "description" : "This is the begining of the task",
    "status" : "TODO"

}'

Get Specific Card :

curl --location 'http://localhost:8080/v1/card/252' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDE3NDY1LCJleHAiOjE2OTM1MDM4NjV9.Ysu-FDfnNVVSxVAb0DTCNL4cNrMBPH-i00eEjiZbgww' \
--header 'Cookie: bezkoder=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDExMDM1LCJleHAiOjE2OTM0MTEwNjV9.83cCSpLTp1Ue9uGG3uhSE07dhhwuGvG8MgBDIvogLrc'


Listing all cards [You need admin role]

curl --location 'http://localhost:8080/v1/card' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDE3NDY1LCJleHAiOjE2OTM1MDM4NjV9.Ysu-FDfnNVVSxVAb0DTCNL4cNrMBPH-i00eEjiZbgww' \
--header 'Cookie: bezkoder=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXI1QHRlc3RpbmcuY29tIiwiaWF0IjoxNjkzNDExMDM1LCJleHAiOjE2OTM0MTEwNjV9.83cCSpLTp1Ue9uGG3uhSE07dhhwuGvG8MgBDIvogLrc'



## Swagger UI

You can view the URLs from the following link : 

http://localhost:8080/swagger-ui/index.html





