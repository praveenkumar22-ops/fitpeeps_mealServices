# Meal Service

The Meal Service is a core microservice of the **FitPeeps** fitness tracking platform. It is responsible for managing users' meal logs, nutritional information, and food consumption records. The service enables users to track their daily dietary intake and provides data that can be used by the Recommendation Service to generate personalized fitness and nutrition recommendations.

## Features

* Log meals consumed by users
* Add multiple food items to a meal
* Track nutritional information
* Retrieve meal history
* Update existing meal records
* Delete meal records
* Store meal and food data in PostgreSQL
* Expose REST APIs for inter-service communication

## Technology Stack

* Java 25
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* REST APIs

## Service Responsibilities

The Meal Service handles:

* Meal creation and management
* Food item tracking
* Nutritional data storage
* Daily meal history management
* Data persistence and validation
* Communication with other FitPeeps services

## Project Structure

```text
src
├── main
│   ├── java
│   │   ├── controllers
│   │   ├── services
│   │   ├── repos
│   │   ├── models
│   │   ├── dtos
│   │   └── utils
│   └── resources
│       └── application.properties
└── test
```

## Database Schema

### Meal

| Field         | Type          | Description                     |
| ------------- | ------------- | ------------------------------- |
| mealId            | Long          | Meal Identifier                 |
| userId        | Long          | User Identifier                 |
| mealType      | String        | Breakfast, Lunch, Dinner, Snack |
| mealTime      | LocalDateTime | Time of Meal                    |
| totalCalories | Double        | Total Calories                  |
| createdAt     | LocalDateTime | Record Creation Time            |

### Food Item (Probable Design)

| Field         | Type   | Description                 |
| ------------- | ------ | --------------------------- |
| name          | String | Food Name                   |
| quantity      | Double | Consumed Quantity           |
| unit          | String | g, ml, serving, piece, etc. |
| calories      | Double | Calories                    |
| protein       | Double | Protein (g)                 |
| carbohydrates | Double | Carbohydrates (g)           |
| fats          | Double | Fats (g)                    |

## API Endpoints

### Create Meal

```http
POST /api/meals
```

Request Body

```json
{
  "userId": 1,
  "mealType": "Lunch",
  "mealTime": "2026-06-22T13:00:00",
  "foodItems": [
    {
      "name": "Rice",
      "quantity": 200,
      "unit": "g"
    },
    {
      "name": "Chicken Breast",
      "quantity": 150,
      "unit": "g"
    }
  ]
}
```

### Get Meal By Id

```http
GET /api/meals/{mealId}
```

### Get User Meals

```http
GET /api/meals/user/{userId}
```

### Update Meal

```http
PUT /api/meals/{mealId}
```

### Delete Meal

```http
DELETE /api/meals/{mealId}
```

## Example Response

```json
{
  "id": 1,
  "userId": 1,
  "mealType": "Lunch",
  "totalCalories": 650,
  "foodItems": [
    {
      "name": "Rice",
      "quantity": 200,
      "unit": "g"
    },
    {
      "name": "Chicken Breast",
      "quantity": 150,
      "unit": "g"
    }
  ]
}
```

## Configuration

Example `application.properties`

```properties
spring.application.name=meal-service

server.port=8084

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Running the Service

### Clone Repository

```bash
git clone <repository-url>
cd meal-service
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

The service will start on:

```text
http://localhost:8084
```

## Future Enhancements

* Nutritional analysis
* separation of Db for Food Items and Meal
* Creation of a separate Table for faster Queries
* Integration with external food databases
* RabbitMQ event publishing for recommendation generation
* Daily calorie tracking
* Macro and micronutrient tracking
* AI-powered meal suggestions

## Part of FitPeeps

This service is part of the FitPeeps microservices ecosystem and works alongside User, Activity, and Recommendation services to provide comprehensive fitness and nutrition tracking for users.
