# **ReciFree: A Recipe API Demonstration**
Yony Marian 

## Project Description
A RESTful API built with Spring Boot demonstrating various
CRUD operations, wherein theoretical "Users" can post
"Recipes" with expected specifications like ingredients,
cook time, difficulty, instructions, and even tags.

----
## Project Specifications
- Language: **Java 21**
  - Framework: **Spring** (*with Spring Boot 3.4.1*)
- Made in: **IntelliJ IDEA**
- CLI: **Bash** (*via Git Bash*)
### Other Tools
- Build: **Maven**
- Database: **H2** (*persisted locally*)
  - ORM: **Hibernate**
- Requests: **Postman**
----
## Database Operations
### User
- Create
- Read
- Update
  - Name
  - Add recipe
  - Remove recipe
- Delete
### Recipe
- Create
- Read
- Update
  - Basic metadata
  - Update ingredient(s)
  - Update step(s)
  - Add/remove tags
- Delete
### Tags (Unique)
- Create
- Read
- Delete
### Data Relationships
| Entity | has a ... relationship with | Entity |
|--------|-----------------------------|--------|
| User | one-to-many | Recipe |
| Recipe | many-to-many | Tag |
| Recipe | many-to-one | User |
