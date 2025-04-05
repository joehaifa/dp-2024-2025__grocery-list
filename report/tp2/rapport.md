# Rapport TP2
**Auteurs** : Candice & Joe  
**Date** : Avril 2025  

---

## 1. Implementation Details

L'application repose sur une architecture simple mais robuste, avec les design patterns vus en cours :

- **Command** : Chaque action (`add`, `remove`, `list`) est représentée par une classe dédiée (`HandleAddCommand`, etc.) qui implémente l’interface `Command`. Cela permet de séparer la logique des commandes et de faciliter l’ajout de nouvelles.

- **DAO (Data Access Object)** : La classe `GroceryListDAO` centralise les opérations métier sur les données, ce qui isole complètement la logique applicative de la logique de stockage.

- **Strategy (via interface)** : Grâce à l’interface `GroceryListStorage`, on peut choisir dynamiquement entre un stockage en JSON ou en CSV (`JsonStorage` ou `CsvStorage`), sans modifier la logique métier.

- **STATIC** : `CommandParser.parseArgs()` est une méthode pure qui gère l’analyse des arguments de manière isolée.

---

## 2. Technical Challenges 

Nous n'avons rencontré aucune difficulté majeure lors des modifications de ce projet.  
