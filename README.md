# Jakku
project for agile software project management
tRAINing – a Jakku project
An app that helps to schedule studytime and training with respect to weather

Who’s who
Ludvig Andersson – ludvan
Oliver Brottare – OliverSkola, Oliver, utan namn
Erik Gustavsson – Erigust, Elldrik
Liam Mattson – YamadaTTV
Christina Meisoll – chrmeis

About tRAINing:
tRAINing was developed in Android Studio on a simulated Google Pixel 3a with android 33 (Tiramisu).
The app is tested on the Samsung S8 as well, but not optimized for it.


How to run the app:
To simulate the app please install Android Studio on your system according to its specifications.
Open Android Studio
chose "Open Project from VCS"
enter https://github.com/chrmeis/Jakku.git
if gradle:build is not starting automatically, press the Elefant with the little blue arrow int the upper right corner
the build might take some time
Go to the Device Manager in the upper right corner choose a Pixel 3a, choose API 33 (Tiramisu) as the version of android
when the gradle Build has finished, build the app using the hammer-icon and run the app by pressing the play-button


Structure
* The app has different Java classes with the following names and purposes:
* AlarmReceiver: 	wakes up the phone and sends notifications based on the intent of the caller.
* Exercise: 	is a simple class working as a page for different workouts.
* LocationSetter: is used by the user to set up the location to be used by the rest of the appMainActivity
* MainActivity: shows the home-screen.
It gets the weather from an API and displays the current weather,
shows the schedule created by Schema that is closable for every day of the week by button and
displays travel recommendations based on weather.
* Notifications: 	represents the notifications sent to the user every 4 hours, reminding them to drink a glass of water
* NotificationSetup: is used to set up notifications based on data saved in shared preference called "sharedPref".
* Pass1: 		is a workout pass with different exercises and the ability to swap between them.
* Schema: 	is used to create the schema accommodating for both study time and training.
* StudyActivity: 	takes in the users study preferences, saves, loads and updates them.
* TrainingActivity: takes in the users training preferences, saves, loads and updates them.
* Waterintake: 	simple class to enable user to track how many glasses of water that has been consumed during the day.
* Weather: 	gets a weather forecast from SMHI and uses it in several functions.

How to use the app:
* When the app has started you see the home-screen, showing today's data.
  On the top third the current weather is displayed. there under follows today's schedule and recommendations on
  how to commute and how warm to dress. The schedule can even be changed to any other day of the week using the buttons
  in the bottom third of the screen
* The three stacked dots in the upper right corner take you to the menu.
* Home takes you back to the home-screen.
* Waterintake lets you set how many glasses of water you want to drink per day by moving the slider in the bottom of the page.
* Exercises lets you choose what workout you want to do. Currently there is only one to choose.
  Choosing this you get shown some exercises with suggestions on reps and rest intervals. You can browse them using
  the buttons at the bottom ot the page.
* The next menu option is Training preferences. Here you find 2 sliders to set how often per week and how long you want to train.
  You save your preferences using the save button.
* Study preferences works similarly. Here you choose how many times per day you want to study and how long each study session
  should be. You save your preferences using the save button.
* Set location is a page you can use to let tRAINing save your current coordinates. This way you can travel but still have
  the recommendations for your homeplace.
* In Water notification you have the possibility to enable or disable notifications to keep hydrated.


Gitinspector:
As we mentioned in mail, we had troubles to run GitInspector. So we have a separate file for 
the insights from GitHub as agreed by mail. You also find Github Insights here:
https://github.com/chrmeis/Jakku/pulse


Länkar:
Trello: https://trello.com/b/aR1u3W3Z/training-scrum-board
Google drive: https://drive.google.com/drive/folders/1obfKtimswApkzlfuuk4IWFzDOqKE12-4?usp=sharing




