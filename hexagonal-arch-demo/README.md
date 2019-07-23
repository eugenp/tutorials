# Quotes Engine Application

## Introduction

Quotes engine is a backend application to faciliate quotes creation and retrieval for a user.

From simplicity perspective, the quotes engine application will meet the following user-cases:
- As an application user, I must be able to view a random quote.
- As an application user, I must be able to save a new quote.


## Architecture

Quotes engine adheres to hexagonal architecture. Application can be viewed as a combination of inside and outside system 
scope briefly describe below.
<br />
- Inside Scope: It consists of the core domain and business logic.
<br />
- Outside Scope: It consists of technology specific interfaces that needs to interacts with the domain.

Interaction between inside and outside scope happens through port and adapters.

- Port: It refers to the interface that is allowed to interact with the domain. 
- Adapter:  It is a specific implementation that plugs into the port to interact with the domain.

The inside and outside can interact on multiple ends, analogous to interaction of inside and outside of a hexagonal boundary at its edges. Note that there is no restriction on the number of ports/adapters, and hexagonal terminology is just a symbolic reference here.

## Domain
Domain consists of a `Quote` model with attributes `message` and `author`.


## Usage
Quotes Engine application supports REST APIs for usage.

- GET `{base_url}/quote` 
- POST `{base_url}/quote` 


