# Bloom's Level Java

A Java Swing application to manage Bloom's taxonomy levels with user login, built using SQLite and AWT/Swing.

## Features
- User authentication (Login system)
- Add, view, update, and delete Bloom's taxonomy levels
- SQLite database integration
- GUI interface using Java Swing

## Requirements
- Java JDK
- SQLite JDBC driver (`sqlite-jdbc-<version>.jar`)

## How to Run
1. Clone the repository
2. Place the `sqlite-jdbc` JAR in your project directory
3. Create a SQLite database named `javaapp.db` and use the schema below to set up tables
4. Compile the Java code:
   cmd..
   javac BLOOMSLEVEL.java

### Required DB Table

-- User table for login
CREATE TABLE users (
    uname TEXT NOT NULL,
    pwd TEXT NOT NULL
);

-- Bloom's level management
CREATE TABLE blooms_level (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  bloom_code TEXT,
  bloom_level TEXT,
  bloom_description TEXT
);
