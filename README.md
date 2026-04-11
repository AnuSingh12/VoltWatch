#VoltWatch – Smart Battery Monitoring & Alert System

A modern Android application that provides real-time battery insights, intelligent alerts, and background tracking — built with clean architecture and system-level APIs.

#Overview

VoltWatch is designed to help users monitor their device’s battery health with precision.
It combines real-time system data, background processing, and user-defined alerts to deliver a reliable and efficient battery monitoring experience.

#Key Features

1.Real-Time Battery Dashboard

* Live battery percentage with dynamic circular indicator
* Battery temperature (°C), voltage (mV), and technology
* Charging status detection (Charging / Not Charging)
* Color-based UI feedback

2.Background Tracking & History

* Automatically logs battery data every **15 minutes**
* Works even when the app is closed
* Stores data using "Room Database"
* Clean history screen with scrollable records
  
3.Smart Alert System

* User selects a target battery percentage
* Triggers a system notification at exact percentage
* Prevents duplicate alerts using state handling
* 
4.Plug-in Feedback

* Detects charger connection in real-time
* Triggers a subtle vibration (300ms)
* Ensures feedback only on actual plug-in events

#Architecture & Design

This project strictly follows **MVVM architecture** with a clean and scalable structure.

UI (Jetpack Compose)
      ↓
ViewModel (StateFlow)
      ↓
Repository
      ↓
Room Database + System Services

#Highlights:

* Reactive UI using **StateFlow**
* Separation of concerns (UI / Data / Logic)
* Lifecycle-aware components
* Clean, maintainable folder structure

#Tech Stack

Kotlin                            
Jetpack Compose (Material 3)      
MVVM                              
Room                              
Coroutines + Flow                 
WorkManager                       
BatteryManager, BroadcastReceiver 
NotificationManager               

#Background Logging

* WorkManager runs every 15 minutes
* Saves battery data to Room DB

#Alert Trigger

* Compares current battery with saved target
* Fires notification at exact match

# Charging Detection

* Tracks state transition (Not Charging → Charging)
* Avoids false triggers on screen navigation

#Final Thoughts

VoltWatch is built with a strong focus on:

* Clean architecture
* System-level integration
* Real-world usability

It reflects practical Android development skills and attention to detail.
