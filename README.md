# TetFit
## Installation
Clone this repository and import into **Android Studio**

## Introduction

TetFit offers a modular approach into the ever-growing marketplace for DIY fitness apps. 
With features such as extensive workout timeline customisation, body condition monitoring and exhaustive guides to a constantly updated catalogue of exercises, 
TetFit aims to deliver an easy-to-use, informative and robust workout experience to all fitness enthusiasts out there.

## App walkthrough
###### The app consists of four major activities of user interaction:
- The launch activity serves up the exercise catalogue from which the user may select desired number of repetitions of each exercise for the workout.
- Post rep-selection, the user is moved into an activity to modify the duration of each rep in the workout.
- Once durations are finalized, a workout countdown timer is launched for each exercise and relevant info to the workout is stored locally.
- A floating action button on the home activity opens into a body condition report for the user based on past workouts.

## Features
###### 1. Home activity
  - Exercise catalogue fetched from server hosted on AWS and displayed in the form of "custom views" through the ListAdapter class.
  - User rating fetched from server for each exercise and displayed in the respective exercise item.
  - Description button displays detailed info on each exercise in the form of a Toast message.
  - Play button launches into relevant exercise video on YouTube through an explicit intent.
###### 2. Duration modifier activity
  - Selected exercises and relevant information transferred from home activity via an implicit intent and displayed in list form using the ArrayAdapter class.
  - Star button lauches AlertDialogBox to enable user to rate exrcise; changes reflected on the server.
  - Relative workload calculation with respect to muscle groups made for overall workout and information transferred to timer activity through an implicit intent.
###### 3. Timer activity
  - Dataset modified to remove third from last workout and incorporate current workout information; workload information for user stored locally in a SharedPreferences file.
  - CountdownTimer sequentially launched with respective durations for each rep in the workout.

###### 4. Status activity
  - Workload data imported from SharedPreferences file and "muscle groups views" set to appropriate colours.
## Hardware requirements
Minimum SDK version 15

## Relevant material
- [Screenshots](https://github.com/butter-chicken27/TetFit/tree/master/Featureset_outline)
- [Backend](https://github.com/peppermenta/TetFit-backend)

## Maintainers
This project is mantained by:
* [Agraj Srivastava](https://github.com/butter-chicken27)
