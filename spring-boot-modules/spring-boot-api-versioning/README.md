# spring-boot-api-versioning
Demonstrating API versioning strategies in Spring Boot
# API Versioning in Spring Boot

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3+-brightgreen)](https://spring.io/projects/spring-boot)  
[![Java](https://img.shields.io/badge/Java-17+-blue)](https://openjdk.org/)  
[![Build](https://img.shields.io/badge/Build-Maven-orange)](https://maven.apache.org/)  

A demo project showcasing **different strategies for API versioning in Spring Boot**, including:

- URI Versioning  
- Request Parameter Versioning  
- Header Versioning  
- Content Negotiation (MIME Type)  
- Native Spring Boot 4 Annotation Support  

---

## Project Setup

Clone the repository:

```bash
git clone https://github.com/your-org/api-versioning-spring-boot
cd api-versioning-spring-boot
```

Build and run:

```bash
./mvnw spring-boot:run
```

---

## Usage Examples

### 1. URI Versioning
```bash
curl http://localhost:8080/api/v1/users
curl http://localhost:8080/api/v2/users
```

### 2. Request Parameter Versioning
```bash
curl http://localhost:8080/api/users?version=1
curl http://localhost:8080/api/users?version=2
```

### 3. Header Versioning
```bash
curl -H "API-VERSION: 1" http://localhost:8080/api/users
curl -H "API-VERSION: 2" http://localhost:8080/api/users
```

### 4. Content Negotiation
```bash
curl -H "Accept: application/vnd.company.v1+json" http://localhost:8080/api/users
curl -H "Accept: application/vnd.company.v2+json" http://localhost:8080/api/users
```

### 5. Native Annotation Support (Spring Boot 4)
```bash
curl http://localhost:8080/api/users --header "API-VERSION: 1"
curl http://localhost:8080/api/users --header "API-VERSION: 2"
```

---

## Tutorial Reference

This project accompanies the post:  
**[API Versioning in Spring](#)**  
- (link will be added once published)  
---

## Running Tests

```bash
./mvnw test
```

---

## Best Practices

- Version only when necessary  
- Document changes clearly  
- Deprecate gracefully  
- Automate testing across versions  
- Align with business needs  

---

## 📜 License

This project is licensed under the MIT License.

---
