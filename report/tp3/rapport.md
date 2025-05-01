# TP3 Report
**Authors**: Candice & Joe  
**Date**: April 2025  

---

## 1. Implementation Details

The application is built on a simple yet robust architecture, using design patterns studied during the course:

- **Command**: Each command (`add`, `remove`, `list`, `info`) is encapsulated in a dedicated class implementing the `Command` interface. This decouples the business logic from the execution logic.

- **DAO (Data Access Object)**: The `GroceryListDAO` class centralizes operations on the grocery list. It separates business logic from persistence logic through an abstraction of the storage layer.

- **STATIC**: The `CommandParser` class uses a static, stateless method (`parseArgs`), which fits the STATIC type covered in class for pure utility functions.

---

## 2. Technical Challenges

We did not encounter any major difficulties during the modifications of this project.
