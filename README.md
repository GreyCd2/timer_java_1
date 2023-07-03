# PB Timer

---------
## TODO: 
- [x] Display error message in red text.
- [x] Check for exceptions.
- [x] Clear and fix comments.
- [x] Change displayed index to start with 1.
- [x] Add default folder when storage is empty.
- [x] Autoload.
- [ ] Tests for previous todos (especially 1,2 & 3).
---------
## Timer 1.1 Features:
- [ ] Upgrade database.
- [ ] Complete GUI.

## A Timer Designed for Magic Cube Players

For the project, I am planning to make a *Magic Cube Timer*. Here are some functionality of this application:
- **A built-in timer** : when the space key is first pressed and released, the timer starts and will stop when the space
key is pressed again. The user can then choose to keep this record or not, and if yes, where (which folder) to store the 
record.


- **A storage of past records** : since many magic cube players play more than one kind of magic cube, users are able to
create multiple folders to store their time records for different type of magic cubes and name these folders accordingly
. Inside folders, they can also choose to delete or move the selected record.


- **A built-in calculator** : inside the folder, there is an average calculator that allows users to calculate their
average time for all the records in the folder.


- **A display of personal best record** : on the top of each folder, a specific record will be displayed as the personal
best (PB) record, which is the attempt with the least amount of time.


Although this app is designed as a magic cube timer, I can imagine that not only magic cube players, but also people 
with needs to time on different things might be interested in this timer. However, as  a magic
cube player myself, I have been troubled by how expensive competition timer is and have used some timer apps which do
not serve a classification function. In this case, I am triggered to make my own timer to make convenient for magic
cube players.

## User Stories
- As a user, I want to be able to create a time record.
- As a user, I want to be able to add a time record into a desired folder.
- As a user, I want to be able to see a list of time records in the folder.
- As a user, I want to be able to delete a time record from the folder.
- As a user, I want to be able to add a new folder into my storage.
- As a user, I want to be able to see a list of folders I have in the storage.
- As a user, I want to be able to delete a folder from the storage.
- As a user, I want to be able to calculate my average time in a folder.
- As a user, I want to be able to see my personal best records in a folder.
- As a user, I want to be able to save all my changes (to files and time records) when quitting the app if I choose to.
- As a user, I want to be able to load previous time records from the file if I choose to.

# Instructions for Grader
- Visual component: when the application is opened, a starter image will be displayed for 2 seconds.
- You can add a folder with user input name to this storage by "Folder -> Add".
- You can remove a folder with user input index from this storage by "Folder -> Remove".
- You can save the state of the storage by "File -> Save".
- You can reload the state of the storage by "File -> Load".

# Phase 4: Task 2
- Mon Apr 10 20:40:39 PDT 2023
Folder folder1 added.
- Mon Apr 10 20:40:43 PDT 2023
Folder folder1 deleted.

# Phase 4: Task 3
- If I have more time, I may rewrite storage using singleton pattern because I want to make sure this is the only
place to store folders in this app. I also want to delete some redundant methods in my TimeRecord, Folder and Storage 
class, such as getIthFolder(). 
