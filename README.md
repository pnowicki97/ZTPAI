# Split with me

## About
The application allows user to track and manage their expanses and share them with other users.

## Table of contents
* [Features](#features)
* [Technology](#technology)
* [Setup](#setup)
* [ERD Diagram](#erd-diagrm)
* [Frameworks](#frameworks)
* [Authorization](#authorization)
* [REST API](#rest-api)
* [RabbitMq](#rabbitmq)
* [Demo](#demo)
* [Development](#development)

## Features:
- Register new user
- Login with an existing user
- Send email to the user
- Add new group of expanses
- Add other users to the group
- Add new expanse to the group
- Set payer of the expanse

## Technology:
- HTML5,
- CSS,
- Java 17,
- Spring 3.4.0,
- Hibernate 6.6.3.Final,
- JPA 3.4.0,
- Thymeleaf 3.4.0,
- Spring Security 3.4.0
- PostgreSQL 42.7.4,
- RabbitMQ 3.4.0,

## Setup:

To run the application it is necessary to install the docker tool which enables managing the containers.
Then it's necessary to download the snapshot and then run all the containers with docker.\
To build the application run the command in the terminal opened in the location of docker-compose.yml file:\
`docker-compose up --build`\
The database should be available at \
`http://localhost:5050`\
And the application itself should be available at\
`http://localhost:8080`

## ERD Diagrm:
![img_2.png](img_2.png)
The database consists of 4 tables:
- users
- groups
- expenses
- user_group\
The relations between entities are:
- OneToMany between users and expenses - one user may have many expenses, but one expense may be paid by only one user
- ManyToOne between expenses and users
- OneToMany between and groups and expenses
- ManyToOne between expenses and groups
- ManyToMany between users and groups - one user may be in many groups and in one group may be many users\
To handle the ManyToMany relation, JPA created the user_group table which stores primary keys for both users and groups table.

## Frameworks
- Application is written in Java 17 with Spring framework working on Tomcat server. 
- Creation and connection to the database is fulfilled by Hibernate with JPA (Java Persistence API). 
- Frontend views are mapped with Thymeleaf. 
- User authentication is implemented with Spring Security based on JWT (Json Web Token). 
- Email sending is served by RabbitMQ and SMTP.

The reason for using this technology stack is to enable possibility to develop the application in the future. The core functionality of the application
should be to allow users to share their expenses. As the application grows, the next functions could be adding events to shared calendar, or making list 
of household duties.\
Having all that in mind Java with Spring, and it's range of frameworks are the best choice to guarantee that all the calculations are correct and new functionalities will not affect existing ones.

## Authorization
User authorization is implemented using SecurityFilterChain which uses JwtAuthenticationFilter. The filter browses through request header and cookies looking for authorization token. 
The token is created in JwtService with HS256 signature algorithm. Generated token is then stored in cookies.

## REST API
After successful running the application it is possible to check the available API endpoints. \
The whole list of API endpoints should be available at\
`http://localhost:8080/swagger-ui/index.html`

Here is the example of methods available for application users configuration.
![img.png](img.png)

And here is the example of description of every method used by API.
![img_1.png](img_1.png)

## RabbitMQ

After successful registration, the application sends welcome email to the new user. This functionality is implemented using RabbitMQ service.
When the user is created, the message with greetings goes to the Rabbit queue, and then it is sent with the smtp protocol.
![img_4.png](img_4.png)

## Demo:
### API (Postman)

The application can be accessed by using REST API. To perform operation using API it is necessary to send proper request on the specific endpoint.\
List of all the endpoints can be accessed here:
`http://localhost:8080/swagger-ui/index.html` 

To register new user the correct endpoint is `http://localhost:8080/auth/register`

REST method should be POST, and the request body should contain name, email and password:

```
{
"name": "Jan",
"password": "Jan123",
"email": "jan@gmail.pl"
}
```

The correct response from the server should contain 200 OK code and JWT token:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKYW4iLCJpYXQiOjE3Mzc3NTM5MzAsImV4cCI6MTczNzc2MjkzMH0.9t_puII2NRC0q4cV1eAbW42kN-K8bIB5bzfbTgx3wPs"
}
```

![img_5.png](img_5.png)

To authorize all the next requests, it is necessary to provide the token in the header of the requests.

![img_6.png](img_6.png)

To get the list of all users, the endpoint should be like this with the method GET:

`http://localhost:8080/api/v1/user`

For get requests, the body may remain empty.\
The response from the server should be a JSON with a list of all the users:
```
[
    {
        "name": "a",
        "email": "a@s.d",
        "id": "0815493b-fa36-409a-9901-dd1ad3f96d1e"
    },
    {
        "name": "Asia",
        "email": "asia@wp.pl",
        "id": "d5d44a2e-79e8-495a-b7ae-098dd49fa38d"
    },
    {
        "name": "Piotr",
        "email": "piotr@gmail.com",
        "id": "5986c901-32b6-486e-b3d4-25f288d19630"
    },
    {
        "name": "Jan",
        "email": "jan@gmail.pl",
        "id": "80332de4-c037-4de8-a67c-39ece17b52ee"
    }
]
```
To delete the user, the endpoint should be: 
`http://localhost:8080/api/v1/user/{user_id}` \
The method used in the request should be DELETE, the body should remain empty.
![img_7.png](img_7.png)
The response from the server should only contain 200 OK and empty body.

### GUI (Browser)

After typing `http://localhost:8080/auth/registerUser` in the browser, the main page will appear. This page allows user to register in the application or move to the login page if they already has an account.
![img_9.png](img_9.png)
After clicking on Sign up button, the user is created, email is sent to provided address, and the page is redirected into login form.
![img_10.png](img_10.png)
After successful login the page is redirected into the user view.\
Here all the user groups are visible. For now the user does not have any group.
![img_11.png](img_11.png)
Now user can log out or add a new group.
![img_12.png](img_12.png)
In this page user can set the group name and photo and add users to group.
![img_13.png](img_13.png)
Recently created group is now visible in the user page. 
If the user clicks in the group, the page will be redirected to the group view.
![img_14.png](img_14.png)
Here, all the group expanses are visible.
For now there are no expanses, but after clicking the Add Expense button, the page will be redirected.
![img_15.png](img_15.png)
Now the user may set the expense name, amount and choose which user from the group paid for the expense.
![img_16.png](img_16.png)
After adding the expense, it is shown in the group view.
![img_17.png](img_17.png)

## Development:

The application can be developed. The first feature to be added should be possibility to choose users between which the specific expanse should be split.
Another idea is to create another view for the user with balances calculated per user.
Next feature would be possibility to clean the balance. 
Another functionality is to enable editing and deleting expanses and groups.
Then the application can be extended by other functionalities like calendar with events related to group users, or todo list with household duties divided between users.
