# MVVM Android App – Client Management

##  Project Description

This project is a simple **Android application** developed using the **MVVM architecture (Model – View – ViewModel)**.
The application allows basic **CRUD operations** (Create, Read, Update, Delete) for managing clients stored in a **SQLite database**.

The goal of this project is to demonstrate how **MVVM architecture** can be applied in Android development while keeping a clear separation between the **UI**, **business logic**, and **data layer**.

---

#  Architecture

The application follows the **MVVM pattern**:

```
UI (MainActivity)
        ↓
ViewModel (ClientViewModel)
        ↓
Database Helper (ClientDbHelper)
        ↓
Model (Client)
```

###  Model

Represents the data structure of a client.

**Client fields:**

* `id`
* `nom`
* `ville`

File:

```
model/Client.java
```

---

###  Data Layer

Handles all **SQLite database operations** such as:

* Creating the database
* Inserting clients
* Updating clients
* Deleting clients
* Retrieving all clients

File:

```
data/ClientDbHelper.java
```

---

###  ViewModel

Acts as the **bridge between the UI and the database**.

Responsibilities:

* Communicates with `ClientDbHelper`
* Uses **LiveData** to notify the UI when data changes
* Handles CRUD operations

File:

```
viewmodel/ClientViewModel.java
```

---

###  UI Layer

The user interface is handled by **MainActivity**.

Features:

* Add a client
* Update a client
* Delete a client (long press)
* Display clients in a **ListView**

File:

```
ui/MainActivity.java
```

---

#  Features

✔ Add new client
✔ Display clients list
✔ Update client information
✔ Delete client with long press
✔ SQLite database storage
✔ LiveData automatic UI updates

---

#  Project Structure

```
mvvm_app/
│
├── data/
│   └── ClientDbHelper.java
│
├── model/
│   └── Client.java
│
├── viewmodel/
│   └── ClientViewModel.java
│
├── ui/
│   └── MainActivity.java
│
├── res/
│   └── layout/activity_main.xml
│
└── README.md
```

---

#  Technologies Used

* **Java**
* **Android Studio**
* **SQLite Database**
* **MVVM Architecture**
* **LiveData**
* **ListView UI Component**

---

#  How to Run the Project

1. Clone the repository

```
git clone https://github.com/your-username/mvvm_app.git
```

2. Open the project in **Android Studio**

3. Let Gradle sync the project

4. Run the application on:

* an Android Emulator
  or
* a physical Android device

---

#  Educational Purpose

This project was developed as part of a **practical assignment (TP)** to understand:

* Android application architecture
* MVVM pattern
* SQLite database integration
* LiveData and ViewModel usage

---

#  Author

Developed by **abdessamad-erramy**

---

# 📄 License

This project is for **educational purposes**.
