# 🌡️📊 JavaSwing TempTracker DB: Temperature Logging & Analysis 🇵🇱
_A Java Swing-based desktop application for recording, viewing, and analyzing temperature measurements, storing data in an SQLite database, with a Polish user interface._

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-%3E%3D8-007396.svg?logo=java&logoColor=white)](https://www.java.com/)
[![Swing](https://img.shields.io/badge/UI-Java%20Swing-orange.svg)]()
[![SQLite](https://img.shields.io/badge/Database-SQLite-003B57.svg?logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![JDBC](https://img.shields.io/badge/Connectivity-SQLite%20JDBC-blue.svg)]()

## 📋 Table of Contents
1.  [Overview](#-overview)
2.  [Key Features](#-key-features)
3.  [Screenshots (Conceptual)](#-screenshots-conceptual)
4.  [System & Database Requirements](#-system--database-requirements)
5.  [Database Schema (`BazaSQL/BazaSQLite.db`)](#-database-schema-bazasqlbazsqlite.db)
6.  [Installation and Setup](#️-installation-and-setup)
7.  [Application Usage](#️-application-usage)
8.  [File Structure (Expected)](#-file-structure-expected)
9.  [Technical Notes](#-technical-notes)
10. [Contributing](#-contributing)
11. [License](#-license)
12. [Contact](#-contact)

## 📄 Overview

**JavaSwing TempTracker DB** (from main class `TemperaturaUI`), developed by Adrian Lesniak, is a desktop application built using Java Swing for recording and analyzing temperature measurements. Users can input a person's first name, last name, and a temperature reading in Celsius (°C). This data, along with an automatic conversion to Fahrenheit (°F) and a timestamp, is then stored in an **SQLite database** (`BazaSQL/BazaSQLite.db`). The application features a main window that displays a sortable table of individuals with their average recorded temperatures. Double-clicking a row in this table opens a detailed view showing all historical measurements for that specific person. The UI includes input validation and user-friendly error messages, with interface text primarily in Polish.

## ✨ Key Features

*   👤 **Data Entry & Storage**:
    *   Input fields for First Name ("Imię"), Last Name ("Nazwisko"), and Temperature in Celsius ("Temperatura °C").
    *   Automatic conversion of Celsius to Fahrenheit: `°F = (°C × 9/5) + 32`.
    *   Data (name, surname, temperatures °C & °F, date/timestamp) is persistently stored in an SQLite database.
*   📊 **Table Display & Sorting**:
    *   A central `JTable` displays a summary for each person: First Name, Last Name, Average Temperature (°C), and Average Temperature (°F).
    *   The table is sortable by clicking on column headers, allowing easy organization of data (e.g., sort by name, average temperature).
*   📋 **Detailed Measurement View**:
    *   Double-clicking a row in the main table opens a new dialog (`JOptionPane` or `JDialog`).
    *   This dialog shows all individual temperature measurements recorded for the selected person, including the date/timestamp, °C value, and °F value.
*   ✔️ **Input Validation**:
    *   Ensures that all input fields (first name, last name, temperature) are filled before saving.
    *   Validates that the temperature input is a valid numeric value.
*   💬 **Error Handling**:
    *   Displays user-friendly error messages (e.g., via `JOptionPane`) for issues like invalid input or database connection/operation problems.
*   🇵🇱 **Polish User Interface**: All labels, button texts, and messages are presented in Polish.
*   💾 **SQLite Database Integration**: Utilizes an SQLite database for robust data storage and retrieval, accessed via the SQLite JDBC driver.

## 🖼️ Screenshots (Conceptual)

**Coming soon!**

_This section would ideally show screenshots of: the main application window with the input panel and the summary table, an example of the detailed measurements dialog after double-clicking a row, and an error message dialog._

## ⚙️ System & Database Requirements

### Software:
*   **Java Development Kit (JDK)**: Java 8 or higher (for compilation and running).
*   **Java Runtime Environment (JRE)**: Required to run the compiled application.
*   **SQLite JDBC Driver**: The `sqlite-jdbc.jar` file must be included in the project's classpath. The overview mentions version `3.42.0` via Maven; if not using Maven, the JAR needs to be added manually.

### Database:
*   **SQLite Database File**: A database file named `BazaSQLite.db` located in a subfolder `BazaSQL/` (i.e., `BazaSQL/BazaSQLite.db`) relative to the application's execution path.
*   **Predefined Schema**: This database file must be pre-created with two specific tables: `osoby` (persons) and `pomiary` (measurements). See the "Database Schema" section below for details.

### Operating System:
*   Any OS that supports Java and Swing (e.g., Windows, macOS, Linux).

## 💾 Database Schema (`BazaSQL/BazaSQLite.db`)

The SQLite database (`BazaSQL/BazaSQLite.db`) must contain the following tables:

1.  **`osoby` (Persons) Table**:
    ```sql
    CREATE TABLE osoby (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nazwisko TEXT, -- Last Name
        imie TEXT      -- First Name
    );
    ```
2.  **`pomiary` (Measurements) Table**:
    ```sql
    CREATE TABLE pomiary (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        id_osoby INTEGER,          -- Foreign key referencing osoby.id
        temperatura_c REAL,        -- Temperature in Celsius
        temperatura_f REAL,        -- Temperature in Fahrenheit
        data TEXT,                 -- Date/Timestamp of the measurement
        FOREIGN KEY (id_osoby) REFERENCES osoby(id)
    );
    ```

## 🛠️ Installation and Setup

1.  **Clone or Download the Source Code**:
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```
    *(Replace `<repository-url>` and `<repository-directory>` if applicable, or simply download/save `TemperaturaUI.java` and other necessary class files if they are separate).*

2.  **Set Up SQLite Database**:
    *   Create a subfolder named `BazaSQL` in your project's root directory (or where the application will be run from).
    *   Using an SQLite database tool (e.g., DB Browser for SQLite, sqlite3 command-line tool), create a new database file named `BazaSQLite.db` inside the `BazaSQL` folder.
    *   Execute the SQL commands provided in the "Database Schema" section above to create the `osoby` and `pomiary` tables.

3.  **Include SQLite JDBC Driver**:
    *   **If using Maven/Gradle**: Add the dependency to your `pom.xml` (as shown in the overview) or `build.gradle` file.
        ```xml
        <!-- For Maven pom.xml -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.42.0.0</version> <!-- Or the latest stable version -->
        </dependency>
        ```
    *   **If compiling manually/using an IDE without Maven/Gradle**: Download the `sqlite-jdbc-VERSION.jar` (e.g., `sqlite-jdbc-3.42.0.0.jar`) from its official repository (e.g., Xerial on GitHub or Maven Central). Place this JAR file in a `lib/` folder within your project or directly in the project root, and ensure it's added to your project's classpath during compilation and runtime.

4.  **Compile the Java Application**:
    Open a terminal or command prompt, navigate to your project's root directory (the one containing `TemperaturaUI.java` or the `zad3` package if it's structured that way), and compile:
    *   If `TemperaturaUI.java` is in the default package:
        ```bash
        javac -cp ".;path/to/sqlite-jdbc.jar" TemperaturaUI.java
        ```
        (Replace `path/to/sqlite-jdbc.jar` with the actual path to your JDBC driver JAR. Use `:` instead of `;` for classpath separation on Linux/macOS).
    *   If classes are in a package like `zad3`:
        ```bash
        javac -cp ".;path/to/sqlite-jdbc.jar" zad3/TemperaturaUI.java zad3/OtherClass.java ...
        ```

5.  **Run the Java Application**:
    From the same directory:
    *   If in default package:
        ```bash
        java -cp ".;path/to/sqlite-jdbc.jar" TemperaturaUI
        ```
    *   If in package `zad3`:
        ```bash
        java -cp ".;path/to/sqlite-jdbc.jar" zad3.TemperaturaUI
        ```

## 💡 Application Usage

1.  Compile and run the application `TemperaturaUI` (or `zad3.TemperaturaUI`) as detailed above.
2.  **Main Window Interface**:
    *   **Left Panel (Data Entry)**:
        *   "Imię:" (First Name) text field.
        *   "Nazwisko:" (Last Name) text field.
        *   "Temperatura °C:" text field.
        *   "Zapisz dane" (Save data) button.
    *   **Center Table**: Displays a list of individuals with their average recorded temperatures in Celsius and Fahrenheit. Initially, this table might be empty or load existing data from the database. It's sortable by clicking column headers.
    *   **Bottom Panel**: Contains an instructional message like "Dwukrotne kliknięcie na wiersz tabeli wyświetli szczegółowe pomiary dla wybranej osoby." (Double-clicking a table row will display detailed measurements for the selected person).
3.  **Actions**:
    *   **Adding a Temperature Record**:
        1.  Enter the person's first name, last name, and the temperature in Celsius into the respective fields in the left panel.
        2.  Click the "Zapisz dane" button.
        3.  The application will validate the input (non-empty fields, numeric temperature). If valid, the data (including the auto-converted Fahrenheit temperature and a timestamp) is saved to the SQLite database.
        4.  The summary table in the center will refresh to reflect the new data or updated averages.
    *   **Viewing Detailed Measurements**:
        1.  Double-click on a row in the center table that corresponds to a person.
        2.  A new dialog box will appear, listing all individual temperature measurements (date, °C value, °F value) recorded for that person.
    *   **Sorting Data**: Click on any column header in the center table to sort the data by that column (ascending/descending).
4.  **Error Handling**:
    *   If input validation fails (e.g., empty name, non-numeric temperature), an error message pop-up will appear.
    *   If database operations fail (e.g., cannot connect, error writing data), an appropriate error message will be displayed.

## 🗂️ File Structure (Expected)

*   `TemperaturaUI.java`: The main Java source file containing the Swing GUI, event handling, database interaction logic, and potentially other helper classes if not separated.
*   (Potentially other `.java` files if classes like `Person`, `Measurement`, or a dedicated `DatabaseHandler` are in separate files within the same package or a `zad3` package).
*   `BazaSQL/` (subfolder):
    *   `BazaSQLite.db`: The SQLite database file where all data is stored. This file is created by the user with the specified schema.
*   `README.md`: This documentation file.
*   `lib/` (optional conventional folder):
    *   `sqlite-jdbc-VERSION.jar` (if JDBC driver is added manually).

## 📝 Technical Notes

*   **SQLite Database**: The application uses a local SQLite database file, making it self-contained once the database is initialized with the correct schema.
*   **JDBC Driver**: Connectivity to the SQLite database is achieved using the `sqlite-jdbc` driver. It's crucial that this driver JAR is correctly included in the project's classpath during both compilation and runtime.
*   **Polish UI & Character Encoding**: The UI text (labels, button captions, messages) is in Polish. Ensure that the Java source files are saved with UTF-8 encoding and that the development environment/compiler handles this correctly to prevent issues with displaying Polish diacritics (ą, ć, ę, ł, ń, ó, ś, ź, ż).
*   **Table Sorting**: The `JTable` component in Swing has built-in support for sorting rows based on column values when a `TableRowSorter` is attached to its model.
*   **Data Validation**: Input validation is essential. The description mentions checks for empty fields and numeric temperature, which should be robustly implemented.
*   **Concurrency**: For a desktop application, Swing operations should always be performed on the Event Dispatch Thread (EDT). Long-running database operations, if any, should ideally be done in a separate worker thread (e.g., using `SwingWorker`) to keep the UI responsive, though for simple operations this might not be strictly necessary.

## 🤝 Contributing

Contributions to the **JavaSwing TempTracker DB** application are welcome! If you have ideas for:

*   Enhancing the user interface or adding more visual themes.
*   Implementing more advanced statistical analysis or charting of temperature data.
*   Adding features like data export (e.g., to CSV).
*   Improving error handling or database interaction efficiency.
*   Refactoring for better separation of concerns (e.g., dedicated data access objects - DAO).

1.  Fork the repository.
2.  Create a new branch for your feature (`git checkout -b feature/TempChart`).
3.  Make your changes to the Java source files.
4.  Commit your changes (`git commit -m 'Feature: Add temperature charting capability'`).
5.  Push to the branch (`git push origin feature/TempChart`).
6.  Open a Pull Request.

Please ensure your code is well-commented and adheres to good Java and Swing development practices.

## 📃 License

This project is licensed under the **MIT License**.
(If you have a `LICENSE` file in your repository, refer to it: `See the LICENSE file for details.`)

## 📧 Contact

Application concept by **Adrian Lesniak**.
For questions or feedback, please open an issue on the GitHub repository or contact the repository owner.

---
📈 _Tracking and analyzing temperatures with Java Swing and SQLite!_
