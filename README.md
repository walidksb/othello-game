# Projet Othello — Documentation Complète (Version Française)

Une implémentation complète du jeu **Othello / Reversi** en **Java 25 + JavaFX**, reposant sur une architecture modulaire propre (inspirée MVC), avec système d’IA, sauvegarde/chargement, undo/redo, et interface graphique générique issue d’une librairie réutilisable.

Ce document décrit :

* L’architecture du projet
* Le rôle de chaque module
* Le fonctionnement interne du jeu
* Les instructions d’exécution avec Gradle ou fichier JAR

---

# 🏗️ Architecture Générale du Projet

L’architecture suit une séparation claire en 5 couches principales : **Model**, **AI**, **Services**, **Controller**, **View**, plus une application JavaFX qui orchestre l’ensemble.

```
fr.univ_amu.m1info
 ├── board_game_library.graphics   ← Librairie générique pour jeux de plateau (fourni)
 └── othello                        ← Notre implémentation du jeu Othello
      ├── model
      ├── ai
      ├── service
      ├── controller
      └── view
```

---

# 1️⃣ Couche **Modèle (model)** — Logique Pure du Jeu

Contient **toutes les règles du jeu** indépendantes de l’interface graphique.

### ✦ `OthelloBoard`

Représente la grille 8×8 contenant les valeurs **EMPTY**, **BLACK**, **WHITE**.

* Initialisation des 4 pions centraux
* Méthodes : `get`, `set`, `inside`, `reset`, `getCopy`

### ✦ `OthelloGame`

Le cœur du jeu :

* Vérification des coups valides
* Inversion des pions
* Passage de tour automatique
* Détection de fin de partie
* Comptage des points
* Méthode `clone()` utilisée par l’IA

### ✦ `Position`, `Player`, `CellState`, `Difficulty`

Structures de base et énumérations.

### ✦ `GameState`

Snapshot immuable d’une partie pour **undo/redo**.

➡️ *Tout est testable unitairement, sans JavaFX.*

----

# 2️⃣ Couche **IA (ai)** — Intelligence Artificielle

Interface commune : **`OthelloAI`**.

### ✦ `EasyAI`

Choix aléatoire parmi les coups possibles.

### ✦ `MediumAI`

Heuristique simple :

* Priorité aux coins
* Puis bords
* Puis cases internes

### ✦ `HardAI`

IA avancée :

* Algorithme **Minimax**
* **Élagage alpha-bêta**
* Table de poids positionnels

### ✦ Services IA

* `AIFactory` → crée l’IA en fonction de la difficulté
* `OthelloAIService` → interface utilisée par le contrôleur

➡️ *L’IA ne touche jamais l’UI. Elle calcule uniquement sur des clones du modèle.*

---

# 3️⃣ Couche **Services (service)** — Fonctionnalités Transversales

### ✦ `GameHistoryService`

Gère :

* Undo / Redo via pile d’états `GameState`
* Stocke uniquement **plateau + joueur courant** pour efficacité

### ✦ `SaveLoadService`

Fonctionnalités :

* Sauvegarde dans des fichiers `.othello`
* Stockage : difficulté, joueur, état complet du plateau
* Chargement dans une structure `LoadedGameData`

➡️ *Responsabilités isolées, facilitant les tests et la maintenance.*

---

# 4️⃣ Couche **Contrôleur (controller)**

### ✦ `OthelloController`

C’est le **chef d’orchestre** :

* Reçoit les clics utilisateur
* Applique les règles du modèle
* Déclenche les tours d’IA
* Gère undo/redo
* Gère sauvegarde/chargement
* Met à jour la vue via `OthelloViewAdapter`

Il ne dessine rien directement — il délègue.

➡️ *Pas testé unitairement car dépend étroitement de JavaFX et de threads.*

---

# 5️⃣ Couche **Vue (view)** — Adaptateur Graphique

### ✦ `OthelloViewAdapter`

Adaptateur pour convertir l’état du modèle en instructions UI :

* Dessin des pions
* Coloration des cases
* Affichage du score et du joueur courant
* Message de fin de partie

La vue réelle est fournie par la librairie générique `board_game_library.graphics`.

➡️ *Aucune logique métier ici, uniquement du rendu.*

---

# 6️⃣ Application JavaFX

### ✦ `JavaFXBoardGameApplication`

Gère :

* Menu d’accueil
* Choix de difficulté
* Mode **Humain vs Humain**
* Construction de l’interface
* Injection du contrôleur

### ✦ `JavaFXBoardGameApplicationLauncher`

Permet de stocker **controller + configuration** avant le démarrage JavaFX.

---

# 🧪 Structure des Tests Unitaires

Tests uniquement sur :

* `model` ✔️
* `service` ✔️
* `ai` (sélection de coups) ✔️

Le contrôleur n’est **pas testé** (dépend d’UI + threads).

Arborescence :

```
src/test/java/
 └── fr/univ_amu/m1info/othello/
      ├── model/
      ├── service/
      └── ai/
```

---

# ▶️ Exécution du Projet

## 1. 🎯 Depuis IntelliJ IDEA

Lancer la classe :

```
fr.univ_amu.m1info.othello.OthelloApplication
```

IntelliJ gère automatiquement JavaFX.

---

## 2. ▶️ Exécution via Gradle

### Lancer directement :

```
./gradlew run
```

Windows :

```
gradlew.bat run
```

---

## 3. 📦 Générer un fichier JAR exécutable

### Construire le JAR :

```
./gradlew jar
```

Il apparaît dans :

```
build/libs/othello-game.jar
```

### Lancer le JAR :

```
java --enable-native-access=ALL-UNNAMED -jar build/libs/othello-game.jar 
```

---

# 🎮 Fonctionnalités du Jeu

* Règles complètes d’Othello
* IA à 3 niveaux (Easy / Medium / Hard)
* Mode **Humain vs Humain**
* Mise en avant des coups possibles
* Undo / Redo
* Sauvegarde & chargement
* Détection automatique de fin de partie
* Interface réactive JavaFX

---

# 🧩 Conclusion

Le projet met en avant :

* Une architecture claire et modulaire
* Une séparation stricte Modèle / Vue / Contrôleur
* Une IA évolutive et remplaçable
* Une interface générique réutilisable pour d’autres jeux
* Une base parfaite pour extensions (réseau, animations, skins…)
