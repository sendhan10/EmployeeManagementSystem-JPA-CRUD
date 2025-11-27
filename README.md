# EmployeeManagementSystem-JPA-CRUD

Simple Employee management console application using JPA (Hibernate) and H2 database.

Features:
- Create, Read (by id, by name, by department, by salary range), Update, Delete
- Count and List all
- Simple CLI menu

How to run:
1. Build: mvn clean package
2. Run: mvn exec:java

Notes:
- Uses H2 file-based DB at ./data/employee_db (created automatically)
- Change persistence.xml to switch DBs
