# On the function : 

```java
private void connectToDatabase(){
        try{
            String url = "jdbc:mysql://localhost:3306/db_employee";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }
```
customize with the database installes on your computer

# Employee Management System

A simple desktop application built using **JavaFX** and **MySQL** for managing employees in an organization.

## Features
- Add, update, delete employee records.
- Search employees by name or position.
- View employee details in an interactive table.
- Connects to a MySQL database for persistent data storage.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/404ahmd/Employee-Management-JavaFX.git
 

### Early Version Release
Version: [v1.0.0](https://github.com/404ahmd/Employee-Management-JavaFX/releases/tag/JavaFX) - 29 Jan 2025




