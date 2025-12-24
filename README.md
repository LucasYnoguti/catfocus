![Java](https://img.shields.io/badge/Java-25-blue)

# CatFocus 🐱⏱️

**CatFocus** is a desktop application inspired by *Forest*, with a cat theme. It helps users stay focused using a **Pomodoro Timer**, automatically alternating between focus sessions and short breaks.  

---

## Implemented Features

- **Pomodoro Timer** with automatic phase switching between focus and break
- **Play/Pause** and **Reset** buttons
- **Customizable durations** for focus, short breaks, and long breaks via a Settings Dialog
- Displays remaining time and current phase (`FOCUS` / `SHORT BREAK` / `LONG BREAK`)

---

## Technologies Used

- Java 25
- Java Swing for the graphical interface  
- `javax.swing.Timer` for real-time updates  
- Immutability and pure logic in the **Functional Core** (`PomodoroState` and `PomodoroPhase`)  

---

## Project Structure
```
core/
    └─ pomodoro/
       ├─ PomodoroPhase.java
       ├─ PomodoroSettings.java
       └─ PomodoroState.java

    ui/
    ├─ AppButton.java
    ├─ Main.java
    ├─ MainFrame.java
    └─ SettingsDialog.java
```
---

## Next Steps

- Integrate a **virtual cat collection**  
- Add **XP, levels, and accessories**  
- Save user progress using **JSON or a lightweight database**  
- Improve notifications and UI animations  

