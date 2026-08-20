# 🚦 Smart Traffic Management System

A **Java-based Smart Traffic Management System** that simulates real-world traffic signal management at a junction using **custom data structures, multithreading, MySQL, and JDBC**.

The system manages vehicle queues, prioritizes emergency vehicles, simulates traffic signals, records traffic violations, validates Indian vehicle number plates, and generates detailed reports in **TXT, PDF, and CSV** formats.

> 🎓 Designed as a **Data Structures + DBMS mini-project** demonstrating practical use of BST, Java Threads, JDBC, DAO architecture, and relational database management.

---

## 📌 Project Overview

The **Smart Traffic Management System** models the operation of a traffic junction through a menu-driven Java console application.

Vehicles are organized into separate **Emergency** and **Regular** queues using a custom **Binary Search Tree (BST)**. A threaded traffic signal continuously cycles through:

**🟢 Green → 🟡 Yellow → 🔴 Red**

The system records vehicle activity and traffic violations in a **MySQL database**, allowing administrators to view statistics, search vehicles, generate reports, and manage multiple junctions.

---

## ✨ Key Features

### 🔐 Admin Authentication

* Secure admin login system
* Automatically seeds a default admin account
* Default credentials:

  * **Username:** `admin`
  * **Password:** `admin`

### 🏙️ Junction Management

* Create and select traffic junctions
* Maintain data separately for each junction
* Supports management of multiple traffic locations

### 🌳 Smart Vehicle Queue

* Custom **Binary Search Tree implementation**
* Separate queues for:

  * 🚑 Emergency vehicles
  * 🚗 Regular vehicles
* Emergency vehicles can receive priority processing
* Vehicle search functionality
* Vehicle deletion functionality

### 🚥 Traffic Signal Simulation

* Multi-threaded traffic signal engine
* Automatic signal cycling:

  * 🟢 Green
  * 🟡 Yellow
  * 🔴 Red
* Configurable signal durations
* Processes vehicles according to the active signal state

### 🪪 Indian Number Plate Validation

Supports validation of multiple Indian vehicle registration formats:

* Regular registration series
* Bharat Series (`BH`)
* Temporary registration series

Validation is performed using **Java Regular Expressions (Regex)**.

### ⚠️ Traffic Violation Management

The system records **12 different types of traffic violations**, including:

* Red-light jumping
* Overspeeding
* Drunk driving
* No helmet
* No seatbelt
* Wrong-side driving
* Illegal parking
* Mobile phone usage while driving
* Triple riding
* Overloading
* Driving without a valid license
* Other configurable violations

### 📊 Statistics & Reports

Generate reports containing:

* Violation statistics
* Vehicle activity logs
* Top offenders
* Violation history
* Junction-specific statistics

Supported formats:

* 📄 TXT
* 📕 PDF
* 📊 CSV

### 🗄️ MySQL Database

Persistent storage using:

* Java JDBC
* MySQL
* DAO architecture
* Automatic table creation

The required database tables are created automatically when the application starts.

---

# 🛠️ Tech Stack

| Technology            | Purpose                        |
| --------------------- | ------------------------------ |
| ☕ Java                | Core application development   |
| 🌳 Binary Search Tree | Vehicle queue management       |
| 🧵 Java Threads       | Traffic signal simulation      |
| 🗄️ MySQL             | Persistent data storage        |
| 🔌 JDBC               | Java–MySQL connectivity        |
| 🏗️ DAO Pattern       | Database access layer          |
| 📕 iText              | PDF report generation          |
| 📄 CSV                | Data export                    |
| 🔍 Regex              | Indian number plate validation |

---

# 🏗️ System Architecture

```text
                    ┌──────────────────────────┐
                    │      Admin / User        │
                    │      Console Menu        │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │   SmartTrafficManagement │
                    │       Main Program        │
                    └────────────┬─────────────┘
                                 │
                ┌────────────────┼────────────────┐
                │                │                │
                ▼                ▼                ▼
       ┌───────────────┐ ┌───────────────┐ ┌───────────────┐
       │   Services    │ │ Data          │ │ Report        │
       │   / Logic     │ │ Structures    │ │ Generator     │
       └───────┬───────┘ └───────┬───────┘ └───────────────┘
               │                 │
               │                 ▼
               │        ┌─────────────────┐
               │        │ Custom BST      │
               │        │ Vehicle Queues  │
               │        └─────────────────┘
               │
               ▼
       ┌───────────────────┐
       │       DAO Layer   │
       │                   │
       │ AdminDAO          │
       │ JunctionDAO       │
       │ VehicleLogDAO     │
       │ ViolationDAO      │
       └─────────┬─────────┘
                 │
                 │ JDBC
                 ▼
       ┌───────────────────┐
       │       MySQL       │
       │    trafficdb      │
       └───────────────────┘
```

---

# 🔄 Database Flow

The application follows a layered database architecture using the **DAO (Data Access Object) pattern**.

```text
User Action
     │
     ▼
Console Menu
     │
     ▼
Service Layer
     │
     ▼
DAO Layer
     │
     ▼
JDBC Connection
     │
     ▼
MySQL Database
     │
     ├── Admin Data
     ├── Junction Data
     ├── Vehicle Logs
     └── Violation Records
```

### Database Operations

```text
Add Vehicle
    ↓
Vehicle Validation
    ↓
BST Queue
    ↓
Vehicle Log DAO
    ↓
MySQL

Traffic Violation
    ↓
Violation DAO
    ↓
MySQL

Generate Report
    ↓
DAO Queries
    ↓
Statistics
    ↓
TXT / PDF / CSV
```

---

# 📁 Project Structure

```text
SmartTrafficManagement/
│
├── src/
│   │
│   ├── SmartTrafficManagement.java
│   │
│   ├── dao/
│   │   ├── BaseDAO.java
│   │   ├── AdminDAO.java
│   │   ├── JunctionDAO.java
│   │   ├── VehicleLogDAO.java
│   │   └── ViolationDAO.java
│   │
│   ├── datastructures/
│   │   └── BinarySearchTree.java
│   │
│   ├── model/
│   │   ├── Vehicle.java
│   │   ├── ViolationRecord.java
│   │   ├── TrafficViolation.java
│   │   └── ...
│   │
│   ├── service/
│   │   ├── TrafficSignalService.java
│   │   ├── TrafficSignalState.java
│   │   └── ...
│   │
│   └── utils/
│       ├── ConsoleUtil.java
│       ├── ReportGenerator.java
│       └── ...
│
├── lib/
│   ├── mysql-connector-java.jar
│   └── itext.jar
│
├── screenshots/
│   ├── login.png
│   ├── main-menu.png
│   ├── vehicle-management.png
│   ├── traffic-signal.png
│   └── reports.png
│
├── README.md
└── .gitignore
```

---

# ⚙️ Prerequisites

Before running the project, make sure you have:

* **JDK 8 or higher**
* **MySQL Server**
* **MySQL JDBC Connector**
* **iText library**
* Git

Check your Java installation:

```bash
java -version
```

Check your compiler:

```bash
javac -version
```

---

# 🚀 Installation & Setup

## 1. Clone the Repository

```bash
git clone https://github.com/devamdoshi4412/SmartTrafficManagement.git
```

Move into the project directory:

```bash
cd SmartTrafficManagement
```

---

## 2. Create the MySQL Database

Open MySQL and create the database:

```sql
CREATE DATABASE trafficdb;
```

The application will automatically create the required tables during the first run.

---

## 3. Configure Database Credentials

Open:

```text
src/dao/BaseDAO.java
```

Update the database configuration according to your MySQL installation.

Example:

```java
String URL = "jdbc:mysql://localhost:3306/trafficdb";
String USERNAME = "root";
String PASSWORD = "your_password";
```

> ⚠️ Do not commit your real database password to GitHub.

For production, database credentials should be stored in environment variables or a configuration file.

---

# 📦 Add Required Libraries

Place the required `.jar` files inside:

```text
lib/
```

Required libraries:

```text
mysql-connector-java.jar
itext.jar
```

---

# ▶️ Compile & Run

### Linux / macOS

Compile:

```bash
javac -d out -cp "lib/*" $(find src -name "*.java")
```

Run:

```bash
java -cp "out:lib/*" SmartTrafficManagement
```

### Windows

Compile:

```cmd
javac -d out -cp "lib/*" src\**\*.java
```

If your shell does not support recursive wildcards, compile all Java source files through your IDE or use a file list.

Run:

```cmd
java -cp "out;lib/*" SmartTrafficManagement
```

---

# 🔑 Default Login

After starting the application:

```text
Username: admin
Password: admin
```

The default admin account is automatically seeded if it does not already exist.

> 🔒 For real-world deployment, password hashing should be implemented.

---

# 📋 Application Workflow

```text
Start Application
       │
       ▼
Admin Login
       │
       ▼
Select / Create Junction
       │
       ▼
Main Menu
       │
       ├── Add Vehicle
       │
       ├── Search Vehicle
       │
       ├── Delete Vehicle
       │
       ├── Start Traffic Signal
       │
       ├── Log Violation
       │
       ├── View Statistics
       │
       ├── Violation History
       │
       ├── Generate Reports
       │
       └── Configure Signal
```

---

# 🚗 Vehicle Management

When adding a vehicle, the application validates its registration number before inserting it into the appropriate queue.

```text
Vehicle Number
      │
      ▼
Regex Validation
      │
      ├── Invalid ──► Reject Vehicle
      │
      └── Valid
            │
            ▼
      Vehicle Type
            │
       ┌────┴────┐
       ▼         ▼
   Emergency   Regular
       │         │
       ▼         ▼
 Emergency     Regular
    BST           BST
```

---

# 🌳 Binary Search Tree

The project implements a custom **Binary Search Tree** instead of relying entirely on Java's built-in collection classes.

The BST is used for:

* Vehicle insertion
* Vehicle searching
* Vehicle deletion
* Queue management

Separate trees are maintained for:

```text
Emergency Vehicles
        +
Regular Vehicles
```

This demonstrates practical application of **Data Structures and Algorithms**.

---

# 🚥 Traffic Signal Simulation

The traffic signal is implemented using **Java multithreading**.

Signal sequence:

```text
🟢 GREEN
   │
   │ Configurable duration
   ▼
🟡 YELLOW
   │
   │ Configurable duration
   ▼
🔴 RED
   │
   │ Configurable duration
   ▼
🟢 GREEN
   │
   └──────────────► Repeat
```

The signal service runs independently using a Java thread, allowing the system to simulate continuous traffic signal operation.

---

# ⚠️ Traffic Violations

The application supports multiple violation categories.

Examples include:

```text
Red Light Jump
Overspeeding
Drink & Drive
No Helmet
No Seatbelt
Wrong Side Driving
Illegal Parking
Mobile Phone Usage
Triple Riding
Overloading
Invalid License
Other Violations
```

Each violation can be associated with:

* Vehicle number
* Junction
* Violation type
* Timestamp
* Additional information

---

# 📊 Reporting System

The system can generate reports in three formats.

### 📄 Text Report

```text
traffic_report.txt
```

### 📕 PDF Report

```text
traffic_report.pdf
```

Generated using the **iText** library.

### 📊 CSV Export

```text
traffic_report.csv
```

Useful for opening data in:

* Microsoft Excel
* Google Sheets
* Other data-analysis tools

---

# 📸 Screenshots

## 🔐 Admin Login

> Add your screenshot here.

```text
screenshots/login.png
```

![Admin Login](screenshots/login.png)

---

## 🏙️ Main Menu

> Add your main menu screenshot here.

```text
screenshots/main-menu.png
```

![Main Menu](screenshots/main-menu.png)

---

## 🚗 Vehicle Management

> Add your vehicle management screenshot here.

```text
screenshots/vehicle-management.png
```

![Vehicle Management](screenshots/vehicle-management.png)

---

## 🚥 Traffic Signal Simulation

> Add your traffic signal screenshot here.

```text
screenshots/traffic-signal.png
```

![Traffic Signal](screenshots/traffic-signal.png)

---

## 📊 Reports & Statistics

> Add your report/statistics screenshot here.

```text
screenshots/reports.png
```

![Reports](screenshots/reports.png)

---

# 🧠 Concepts Demonstrated

This project combines multiple important computer science concepts.

### Data Structures

* Binary Search Tree
* Tree traversal
* Searching
* Insertion
* Deletion
* Priority-based vehicle processing

### Object-Oriented Programming

* Classes & Objects
* Encapsulation
* Abstraction
* Inheritance where applicable
* Polymorphism where applicable

### Database Management

* MySQL
* SQL queries
* JDBC
* DAO pattern
* Relational data persistence

### Multithreading

* Java Threads
* Concurrent traffic signal simulation
* Thread-based state transitions

### Input Validation

* Regular Expressions
* Indian vehicle registration validation

### File Processing

* TXT generation
* PDF generation
* CSV export

---

# 🔮 Future Improvements

Several improvements can be added in future versions:

* [ ] Build a GUI using **JavaFX or Swing**
* [ ] Add real-time vehicle detection using cameras
* [ ] Integrate automatic number plate recognition (ANPR)
* [ ] Add password hashing
* [ ] Move database credentials to environment variables
* [ ] Add role-based authentication
* [ ] Add automated unit tests
* [ ] Add JUnit tests for BST operations
* [ ] Add DAO integration tests
* [ ] Add live traffic statistics dashboard
* [ ] Add REST API support
* [ ] Add cloud database support
* [ ] Add emergency vehicle GPS prioritization
* [ ] Add graphical traffic analytics

---

# 🧪 Testing

Recommended testing areas include:

### BST Testing

```text
✓ Insert vehicle
✓ Search vehicle
✓ Delete vehicle
✓ Empty tree
✓ Duplicate vehicle
✓ Emergency vehicle priority
```

### Database Testing

```text
✓ Admin creation
✓ Junction creation
✓ Vehicle logging
✓ Violation insertion
✓ Violation retrieval
✓ Report generation
```

### Validation Testing

```text
✓ Valid registration number
✓ Invalid registration number
✓ Bharat Series
✓ Temporary registration
✓ Incorrect formats
```

---

# 🔐 Security Considerations

This project is intended primarily for **educational purposes**.

For production use, the following should be implemented:

* Password hashing using BCrypt/Argon2
* Environment variables for DB credentials
* Prepared statements for all database queries
* Role-based authorization
* Input sanitization
* Secure database configuration
* Audit logging

---

# 📈 Project Highlights

| Feature               | Implementation         |
| --------------------- | ---------------------- |
| Vehicle Queue         | Custom BST             |
| Emergency Priority    | Separate Emergency BST |
| Signal Processing     | Java Thread            |
| Database              | MySQL                  |
| Database Connectivity | JDBC                   |
| Architecture          | DAO + Service          |
| Plate Validation      | Regex                  |
| Violations            | 12 Categories          |
| Reports               | TXT / PDF / CSV        |
| Authentication        | Admin Login            |
| Junction Management   | Multi-location Support |

---

# 🎯 Learning Objectives

This project was developed to demonstrate how theoretical computer science concepts can be applied to a practical real-world problem.

The main objectives are:

1. Apply **Binary Search Trees** to vehicle management.
2. Understand **Java multithreading** through traffic signal simulation.
3. Implement database connectivity using **JDBC**.
4. Understand the **DAO architecture pattern**.
5. Practice relational database design using **MySQL**.
6. Implement input validation using **Regular Expressions**.
7. Generate structured reports from database data.
8. Build a complete menu-driven Java application.

---

# 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

### Steps

```bash
# Fork the repository

# Clone your fork
git clone https://github.com/your-username/SmartTrafficManagement.git

# Create a feature branch
git checkout -b feature/new-feature

# Make your changes

# Commit your changes
git commit -m "Add new feature"

# Push your branch
git push origin feature/new-feature

# Open a Pull Request
```

---

# 📄 License

This project is open source and intended for **educational and learning purposes**.

You are free to use, modify, and improve the project according to the applicable license terms.

---

# 👨‍💻 Author

## Nishit Modi

**Java Developer | Data Structures | DBMS | Software Development**

Developed as a **Data Structures / DBMS mini-project** using Java, MySQL, JDBC, custom Binary Search Trees, and multithreading.

### 🔗 Project Repository

[Smart Traffic Management System](https://github.com/devamdoshi4412/SmartTrafficManagement)

---

# ⭐ Support

If you found this project useful or interesting:

⭐ **Star the repository**

🍴 **Fork the project**

🐛 **Report issues**

💡 **Suggest improvements**

🤝 **Contribute to the project**

---

## 🚦 Smart Traffic Management System

**Java • MySQL • JDBC • BST • Multithreading • iText**

> *From vehicle queues to traffic violations — a complete simulation of smart junction management.*
