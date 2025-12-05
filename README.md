# ESP32AndroidNotifier — Real-Time ESP32 Alerts on Android

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](#license)
[![Languages: Java | Android](https://img.shields.io/badge/languages-Java%20%7C%20Android-green.svg)](#tech-stack)
[![GitHub repo size](https://img.shields.io/github/repo-size/migit/ESP32AndroidNotifier)](#)

<p align="center">
 
<!-- <img width="768" height="504" alt="ESP32AndroidNotifier_ Real-time Notifications from Your ESP32 to Android - visual selection" src="https://github.com/user-attachments/assets/422e42d0-5e88-4b29-9707-5c4b4d5b3422" /> --> 
<!--  <img width="1536" height="1024" alt="esp32notifier" src="https://github.com/user-attachments/assets/c39fae85-e74d-412e-af43-fbd6d835982f" /> --> 

  ![ESP32 Android Notifier](https://github.com/migit/ESP32AndroidNotifier/assets/6859479/55790b6a-7855-4efd-abfd-43edaf4ece28) 
 
</p>

---

**ESP32AndroidNotifier** is an Android application designed to provide instant, context-aware notifications from your ESP32 microcontroller. It seamlessly monitors sensor readings, actuator states, and system events, pushing alerts in real-time to your phone, even in the background via dedicated threads.

This platform bridges IoT hardware and mobile software, enabling a highly responsive and interactive smart environment.

---

## Features

* **Real-Time Notifications**: Receive immediate alerts from ESP32 sensors or actuators.
* **Background Monitoring**: Notifications continue even when the app is not active.
* **Tagging & Prioritization**: Alerts are tagged with sensor type and priority for easy recognition.
* **Modular Integration**: Supports multiple ESP32 devices and various data types.
* **Custom Alerts**: Configure custom messages, thresholds, and triggers per device.
* **Thread-Safe Operation**: Uses background threads to avoid UI freezes and ensure continuous monitoring.

---

## Architecture

ESP32AndroidNotifier uses a modular client-server approach:

* **ESP32 Firmware**: Publishes data over Wi-Fi/Bluetooth using JSON or MQTT.
* **Android App**: Subscribes to ESP32 events, parses incoming messages, and triggers notifications.
* **Notification Manager**: Ensures persistent background operation and handles multiple sources concurrently.
* **UI Layer**: Lightweight, intuitive interface showing device status, last updates, and custom action buttons.

---

## Installation

### Prerequisites

* Android Studio installed
* Android device running API 24+ (Android 7.0+)
* ESP32 microcontroller with sensor modules

### Steps

1. Clone the repository:

```bash
git clone <repo-url>
cd ESP32AndroidNotifier
```

2. Open the project in Android Studio
3. Build and install the APK on your device
4. Configure your ESP32 devices’ IP or Bluetooth addresses in the app settings

---

## Getting Started

1. Launch the app and add your ESP32 devices
2. Assign sensors, actuators, and tags
3. Configure alert thresholds and notification preferences
4. Start monitoring — notifications will arrive in real-time, even when the app is running in the background

---

## Troubleshooting

* **App not receiving notifications**

  * Ensure ESP32 is on the same network (Wi-Fi) or properly paired via Bluetooth
  * Check firewall/router settings
* **UI freezing or lagging**

  * Verify that background threads are active and app permissions are granted
* **Alerts are missing or delayed**

  * Confirm sensor thresholds and ESP32 firmware is sending messages in correct format

---

## Roadmap & Ideas

* Multi-device dashboard: view all ESP32 devices and sensor statuses on one screen
* Notification history log with export capability
* Integration with home automation systems (Home Assistant, Node-RED)
* Custom vibration patterns or sound alerts per device type
* AI-driven anomaly detection: alert when readings deviate from historical norms
* Android widget: quick glance of sensor states without opening the app

---

## Contributing

* Submit feature requests, bug reports, and improvements via GitHub Issues
* Follow Android coding conventions and best practices
* Pull requests must include clear description, code comments, and tested functionality

---

## License

This project is licensed under the **MIT License**.

---

**Stay connected to your ESP32 devices with instant, intelligent alerts!**
