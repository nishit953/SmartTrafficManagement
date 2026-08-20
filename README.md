🚦 Smart Traffic Management System
A Java console application that simulates real-world traffic signal management at a junction — vehicle queuing, signal cycling, violation tracking, and reporting — backed by a MySQL database.

📖 Description
Smart Traffic Management System is a menu-driven Java application built for a Data Structures / DBMS mini-project. It models how a traffic junction handles vehicles and violations:

Vehicles are queued using a custom Binary Search Tree (BST), with separate trees for emergency and regular vehicles so emergency traffic can be prioritized.
A threaded traffic signal simulator cycles through Green → Yellow → Red states, processing each queued vehicle in turn with configurable durations.
All vehicle activity (added / processed / deleted) and traffic violations are persisted to a MySQL database via a DAO (Data Access Object) layer using JDBC.
Admins log in before using the system, and each junction is tracked separately so multiple locations can be managed.
The system validates Indian vehicle number plates (Regular, Bharat, and Temporary series formats) using regex.
Reports can be generated as plain text, PDF (via iText), or exported as CSV, summarizing violations, top offenders, and vehicle log statistics.
✨ Features
🔐 Admin authentication (auto-seeds a default admin/admin account)
🏙️ Junction creation/selection so data can be tracked per location
🌳 Custom BST-based vehicle queue with separate emergency/regular lanes
🚥 Multi-threaded traffic signal simulation with configurable Red/Yellow/Green durations
🔍 Vehicle search and deletion
🪪 Indian number plate validation (Regular, Bharat & Temporary series)
⚠️ Logging of 12 types of traffic violations (red-light jumps, overspeeding, drink & drive, no helmet/seatbelt, etc.)
📊 Violation statistics and top-offender leaderboard
📄 Report generation: plain text (.txt), PDF (.pdf), and CSV export
🗄️ Persistent storage via MySQL (auto-creates required tables on first run)
🛠️ Tech Stack
Language: Java
Database: MySQL (via JDBC)
PDF Generation: iText
Data Structures: Custom Binary Search Tree implementation
Concurrency: Java Threads for signal simulation
📁 Project Structure
SmartTrafficManagement/
├── src/
│   ├── SmartTrafficManagement.java   # Main entry point & menu
│   ├── dao/                          # Database access layer (Admin, Junction, VehicleLog, Violation)
│   ├── datastructures/               # Custom BST implementation for vehicle queues
│   ├── model/                        # Domain models (Vehicle, ViolationRecord, TrafficViolation, etc.)
│   ├── service/                      # Traffic signal service & state logic
│   └── utils/                        # Console I/O helper & report generator
└── README.md
⚙️ Prerequisites
JDK 8 or higher
MySQL Server running locally (default DB name: trafficdb)
MySQL JDBC Connector (mysql-connector-java) on the classpath
iText library (for PDF report generation)
🚀 Getting Started
Clone the repository

git clone https://github.com/devamdoshi4412/SmartTrafficManagement.git
cd SmartTrafficManagement
Set up MySQL

Create a database named trafficdb (tables are auto-created on first run).
Update the database URL, username, and password in src/dao/BaseDAO.java to match your local setup.
Add dependencies

Download mysql-connector-java and the iText JAR, and add them to your project's classpath / libraries.
Compile and run

javac -d out -cp "lib/*" $(find src -name "*.java")
java -cp "out:lib/*" SmartTrafficManagement
Log in

Default admin credentials: admin / admin
📋 Usage
Once logged in, you'll be greeted with a menu to:

Add a vehicle (with number plate validation)
Start threaded traffic signal processing
Search or delete a vehicle
Log a traffic violation
View violation & log statistics
Generate text/PDF reports or export CSV
Configure signal durations
View violation history for a specific vehicle
🔮 Future Improvements
Add a GUI (JavaFX/Swing) instead of console-only interaction
Externalize DB credentials into a config file instead of hardcoding
Add password hashing for admin accounts
Add unit tests for BST and DAO layers
📄 License
This project is open source. Feel free to use and modify it for learning purposes.

👤 Author
Developed by Nishit Modi
