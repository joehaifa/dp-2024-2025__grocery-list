# TP4 — Final Report  
Authors: Candice & Joe


## What we did not have time to do  
* **Nothing is missing** — every user-story in the backlog is implemented and covered by tests.

## What was difficult  
* **Class-diagram readability in Mermaid**  
  * With 20 + interconnected classes, arrows crossed everywhere.  
  * We therefore kept **only the detailed SVG version**; zooming lets the reader inspect any area without losing the big picture.

## Design patterns used and why  

* **Command** — `Command` + `Handle*Command`  
  * Encapsulates each CLI action; a new command can be added without touching the others.

* **Strategy** — `GroceryListStorage`, `CsvStorage`, `JsonStorage`  
  * Switches persistence format at runtime via the `-f` flag (CSV / JSON).

* **Adapter** — `MyGroceryShopAdapter`  
  * Bridges our domain model to the external `GroceryShopServer` interface.

* **Facade** — `CommandExecutor`  
  * Single entry point: parses args, chooses storage strategy, instantiates the proper command, executes it.

* **DAO / Repository** — `GroceryListManager`  
  * Isolates business logic from persistence details; simplifies unit testing.


## Theoretical answers to the questions  

* **How to add a new command?**  
  * Create a `HandleNewCommand` class implementing `Command`.  
  * Add a matching `case` in the `switch` inside `CommandExecutor`.  
  * Write its unit test.  
  * No other class needs changes.  

* **How to add a new data source?**  
  * Implement `GroceryListStorage` (e.g. `XmlStorage`).  
  * Instantiate it in `CommandExecutor` when `-f xml` is provided.  
  * Thanks to the Strategy pattern, no other layer is affected.  

* **What to change to specify a target shop when adding groceries?**  
  * Add a `--shop` option in `CommandParser` and store it in `CommandContext`.  
  * Extend `GroceryItem` with a `shop` field and adapt `GroceryListManager` plus storage implementations to persist it.  
  * Existing commands will keep working by reading the additional context value, without further modification.
