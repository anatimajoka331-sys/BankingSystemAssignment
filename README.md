# Corporate Banking Ecosystem Application

A robust, Java-based Enterprise Banking System featuring automated relational database persistence. The application integrates an object-oriented Java backend with a local MySQL server via Apache Maven dependency injection. It is built to simulate automated account provisioning, secure double-entry transaction posting, system auditing, and secure record lookups.

---

## 🛠️ Technology Stack & Environment
* **Programming Language:** Java (JDK 25 optimized)
* **Build Automation Tool:** Apache Maven 3.x
* **Database Engine:** MySQL Server 8.0 / 9.x
* **Visual Database Editor:** MySQL Workbench
* **Development IDE:** Apache NetBeans IDE 29

---

## 🗄️ Database Architecture & Initialization

The relational schema is configured to satisfy comprehensive 3rd Normal Form (3NF) relational constraints across 5 transactional tables managed by structural integrity configurations (`ON DELETE CASCADE`).

### Database Layout Visual Representation

### How to Import the SQL Script:
1. Open **MySQL Workbench** and connect to your local database instance (`Local Instance 3306`).
2. Navigate to the top menu bar and select **File -> Open SQL Script...**
3. Locate and select the **`banking_system.sql`** file from this project directory.
4. Click the **Lightning Bolt** icon on the query toolbar to execute the entire script.
5. Verify that the `banking_system` schema appears in your left-hand *Schemas* pane with green checkmarks in the lower *Action Output* console log.

---

## 🚀 Application Compilation and Execution

Follow these steps to synchronize the dependencies, build the workspace binaries, and execute the runtime system.

### Prerequisites Verification
Ensure your database credentials match the database environment configuration within the `BankingSystemAssignment.java` file source parameters:
```java
private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_root_password";
