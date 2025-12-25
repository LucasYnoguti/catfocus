![Java](https://img.shields.io/badge/Java-25-blue)

# CatFocus 🐱⏱️

**CatFocus** is a desktop application inspired by *Forest*, with a cat theme. It helps users stay focused using a **Pomodoro Timer**, automatically alternating between focus sessions and short breaks.  

---

## Implemented Features

- **Pomodoro Cicles:** Alternate automatically between FOCUS, SHORT PAUSE and LONG PAUSE
- **Adjustable Configurations:** Adjust the duration of each faseAjuste o tempo de cada fase in the interface.
- **Data Persistence:** Configurations and progress are saved in a SQLite database.
- **Sound Feedback:** gives a sound feedback when the timer ends..
- **Responsive Interface:** Design adapted to size changes.

---
## Architecture

The project follows an approach that separates pure business logic from interface side effects:

**Core (Functional Core)**: Contains the state and transition rules. It is immutable and framework-agnostic, making it easier to test and ensuring predictability.

**UI (Imperative Shell)**: Handles graphical rendering, user input, database persistence, and audio output.

---

## Technologies Used

- **Language:** Java 25
- **Graphic Interface:** Swing with [FlatLaf](https://github.com/JFormDesigner/FlatLaf).
- **Database:** SQLite to store user settings and progress.
- **Architecture:** Standard **MVC** (Model-View-Controller) to separate concerns.
- **Dependency manager:** Maven.

---

## Project Structure
```
io.github.lucasynoguti
    ├─ Main.java (Entry Point)
    ├─ core/ (Functional Core)
    │  └─ pomodoro/
    │    ├─ PomodoroPhase.java
    │    ├─ PomodoroSettings.java
    │    └─ PomodoroState.java
    └─ ui/ (Imperative Shell)
       ├─ AppButton.java
       ├─ AppTheme.java
       ├─ MainFrame.java
       ├─ PomodoroController.java
       ├─ PomodoroPanel.java
       ├─ SettingsDialog.java
       └─ SoundPlayer.java
```
---

## Next Steps

- Integrate a **virtual cat collection**  
- Add **XP, levels, and accessories**
- Improve notifications and UI animations  

