# compet

![App SS](<Screenshot 2026-03-23 at 8.16.04 PM.png>)

## Project Idea and User Stories

### What will the application do?

**compet** is a gamified productivity companion that rewards focus and study sessions. Users start a **Pomodoro-style focus timer**, and each completed session triggers a **randomized interaction** with their chosen pet (e.g., petting, feeding, cleaning, or playing).
Each pet comes with a unique themed **room or aesthetic** to make the experience more fun and personalized. As more sessions are completed, the pet's **fondness meter** grows, visually reflecting the bond with the user. Users may also switch between pets anytime, with each pet's fondness level and room saved individually.

### Who will use it?

The primary users of **compet** are **students and professionials** seeking motivation to maintain consistent focus.
Also, it appeals to users who enjoy gamification and virtual pet experiences tied to productivity. 

### Why is this project of interest to you?

I am greatly interested in this project because I enjoy **combining productivity with creativity**. Focus and task apps can sometimes feel dry, but by adding a **virtual pet system with randomized interactions**, I can create an app that is fun, motivating, and unique. It is not just about working and focusing but about forming a bond with a companion while staying productive. 

### User Stories

- [x] As a user, I want to be able to add a completed focus session to my session log so that I can keep a record of my productivity.
- [x] As a user, I want to be able to see a random interaction (like petting, feeding, or playing) after a session so that I feel rewarded and motivated. 
- [x] As a user, I want to be able to choose a pet companion and give it a name so that I can personalize my experience. 
- [x] As a user, I want my pet's fondess meter to increase when I complete sessions so that I can see my bond with the pet grow.
- [x] As a user, I want to be able to switch between different pets so that I can interact with multiple companions and enjoy their unique rooms.
- [x] As a user, I want to be able to view a list of all my past focus sessions so that I can see my history of effort and consistency (including details such as which pet and progress). 
- [x] As a user, I want to be able to save my entire application state (all pets, their fondness levels, and session history) to file so that I can preserve my progress.
- [x] As a user, I want to be able to load my previously saved application state from file so that I can continue where I left off.

## Instructions for End User

### Managing Your Compet Productivity Companion

**Adding a Pet:**
- Click "+ New Pet", enter a name, pick a type and room, and click OK
- Your pet appears in the left panel with their room background

**Switching Pets:**
- Click "Switch Pet", select a name from the dropdown, and click OK

**Viewing All Pets:**
- Click "View All Pets" to see a table of all your pets and their fondness levels

**Running a Focus Session:**
- Enter a duration in minutes and click "Start Session"
- When the timer finishes, a random interaction popup appears and the session is saved to history

**Stopping a Session:**
- Click "Stop" to cancel — stopped sessions are not recorded

**Visual Components:**
- Your pet's GIF is displayed inside their themed room in the left panel
- After each session, an interaction GIF popup appears (petting, feeding, playing, or cleaning)
- The timer turns red in the final 60 seconds

**Saving:**
- Click "Save" in the top right, or save when prompted on exit

**Loading:**
- Click "Load" in the top right, or load when prompted on startup

### Event Log Sample

![Event Log Sample](<Screenshot 2026-03-23 at 7.28.21 PM.png>)

### UML Class Diagram

![UML Class Diagram](<UML_Design_Diagram.png>)

The design involves the model, persistence, and ui packages. Writable is implemented by Pet, PetManager, SessionLog, and FocusSession. EventLog uses the Singleton pattern and holds 0..* Event objects. 

### Refactoring Possibilities

- Split CompetGUI into smaller panels: It does way too much in one class. We could extract TimerPanel, PetDisplayPanel, and SessionLogPanel in order to improve readability and make each component easier to modify. 
- Make AppState implement Writable: It currently holds the full app state but does not implement Writable. Adding this could let JsonWriter call appState.toJson() directly, reducing dependency. 
