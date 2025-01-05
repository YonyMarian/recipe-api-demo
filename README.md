# **ReciFree: A Recipe API Demonstration**
Yony Marian 

## Project Description
A RESTful API built with Spring Boot demonstrating various
CRUD operations, wherein "Users" can post
"Recipes" with expected specifications like ingredients,
cook time, difficulty, instructions, and even tags.

I built this project to demonstrate my grasp of Java, Spring, and API structure. In theory, this API models the foundation of a backend for a forum where amateur and professional chefs alike can share recipes to be enjoyed by other users. The functions under **Database Operations** illustrate the simplest ways in which tasks that would allow such a forum to function are executed.

- Each *Recipe* may be publicly viewed by any *User*.
- *Recipes* contain various bits of data that allow users to search for them, whether it be by name, the *User* who made it, its ingredients, or any various distinct *Tags* that its creator attached to it.
- Each *Recipe* created has its own unique ID and is assigned to only one *User* with their own unique ID. *Recipes* and *Users* may be otherwise identical.


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
#### *Controllers* give meaning to URLs with HTTP requests, calling on functions in the *Service* layer, which in turn perform actions on the *Repositories* containing User, Recipe, or Tag *Entities*.
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
