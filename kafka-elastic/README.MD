### README

# Spring Kafka Elastic Demo

This project demonstrates the integration of Spring Boot with Kafka and Elasticsearch. It includes a simple REST API to send notifications, which are then processed by Kafka and stored in Elasticsearch.

## Prerequisites

- Docker
- Docker Compose
- Java 17
- Maven

## Getting Started

### Build and Run with Docker

1. **Build the Docker images:**
   ```sh
   docker-compose build
   ```

2. **Start the services:**
   ```sh
   docker-compose up
   ```

### Run Locally

1. **Build the project:**
   ```sh
   mvn clean package -DskipTests
   ```

2. **Run the application:**
   ```sh
   java -jar target/spring-kafka-elastic-demo-0.0.1-SNAPSHOT.jar
   ```

## API Documentation

### Check Status

- **URL:** `/api/v1/status`
- **Method:** `GET`
- **Response:**
  ```json
  {
    "status": "working"
  }
  ```

### Send Notification

- **URL:** `/api/v1/send`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "userId": 1,
    "message": "Hello, World!",
    "recipientId": 2
  }
  ```
- **Response:**
    - **Success:**
      ```json
      {
        "message": "Message sent successfully"
      }
      ```
    - **Error:**
      ```json
      {
        "error": "Error message"
      }
      ```

## Configuration

### Kafka Configuration

- **Bootstrap Servers:** `${spring.kafka.bootstrap-servers}`
- **Consumer Group ID:** `springConsumerGroup1`
- **Topic:** `foodsOrder`

### Elasticsearch Configuration

- **URI:** `elasticsearch:9200`
- **Username:** `elastic`
- **Password:** `Elasticsearch@123`

## Docker Compose Services

- **Zookeeper:** `zookeeper:2181`
- **Kafka:** `kafka:9092`
- **Elasticsearch:** `elasticsearch:9200`
- **Spring Boot App:** `springbootapp:8080`

---

### API Documentation

#### Check Status

**Endpoint:** `/api/v1/status`

**Method:** `GET`

**Description:** Check if the service is running.

**Response:**
```json
{
  "status": "working"
}
```

#### Send Notification

**Endpoint:** `/api/v1/send`

**Method:** `POST`

**Description:** Send a notification to Kafka.

**Request Body:**
```json
{
  "userId": 1,
  "message": "Hello, World!",
  "recipientId": 2
}
```

**Response:**
- **Success:**
  ```json
  {
    "message": "Message sent successfully"
  }
  ```
- **Error:**
  ```json
  {
    "error": "Error message"
  }
  ```