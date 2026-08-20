# 🚦 Smart Traffic Management System

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![JDBC](https://img.shields.io/badge/JDBC-Connectivity-green)
![License](https://img.shields.io/badge/License-Open%20Source-lightgrey)

A **Java console-based traffic management system** that simulates traffic signals, vehicle queues, emergency vehicle priority, traffic violations, and reporting using **Java, MySQL, JDBC, BST, and multithreading**.

## ✨ Features

* 🔐 Admin login with default `admin/admin` account
* 🏙️ Create and manage multiple junctions
* 🚗 Add, search, and delete vehicles
* 🌳 Custom Binary Search Tree for vehicle queues
* 🚑 Separate emergency and regular vehicle queues
* 🚥 Multithreaded Green → Yellow → Red signal simulation
* 🪪 Indian vehicle number plate validation
* ⚠️ 12 types of traffic violations
* 📊 Violation statistics and top offenders
* 📄 Generate TXT, PDF, and CSV reports
* 🗄️ MySQL database with JDBC
* 🔧 Configurable traffic signal durations

## 🛠️ Tech Stack

* **Language:** Java
* **Database:** MySQL
* **Connectivity:** JDBC
* **Data Structure:** Binary Search Tree
* **Concurrency:** Java Threads
* **PDF:** iText
* **Validation:** Regular Expressions

## 🏗️ Architecture

```text
User
 │
 ▼
Console Menu
 │
 ▼
Service Layer
 │
 ├── BST Vehicle Queue
 ├── Traffic Signal Thread
 └── Report Generator
 │
 ▼
DAO Layer
 │
 ▼
MySQL Database
```

## 📁 Project Structure

```text
SmartTrafficManagement/
│
├── src/
│   ├── SmartTrafficManagement.java
│   ├── dao/
│   ├── datastructures/
│   ├── model/
│   ├── service/
│   └── utils/
│
├── lib/
├── screenshots/
└── README.md
```

## ⚙️ Requirements

* JDK 8 or higher
* MySQL Server
* MySQL Connector/J
* iText library

## 🚀 Setup

### 1. Clone the repository

```bash
git clone https://github.com/devamdoshi4412/SmartTrafficManagement.git
cd SmartTrafficManagement
```

### 2. Create the database

```sql
CREATE DATABASE trafficdb;
```

Tables are automatically created when the application runs.

### 3. Configure MySQL

Open:

```text
src/dao/BaseDAO.java
```

Update your MySQL username and password.

### 4. Add libraries

Place the required JAR files inside:

```text
lib/
```

### 5. Compile & Run

**Linux/macOS:**

```bash
javac -d out -cp "lib/*" $(find src -name "*.java")
java -cp "out:lib/*" SmartTrafficManagement
```

**Windows:**

```cmd
java -cp "out;lib/*" SmartTrafficManagement
```

## 🔑 Default Login

```text
Username: admin
Password: admin
```

## 📸 Screenshots

Add your screenshots here:

```text
screenshots/
├── login.png
├── main-menu.png
├── vehicle-management.png
└── reports.png
```

## 🔮 Future Improvements

* GUI using JavaFX/Swing
* Password hashing
* Unit testing
* External configuration for database credentials
* Real-time traffic monitoring
* Automatic number plate recognition

## 👨‍💻 Author

**Nishit Modi**
