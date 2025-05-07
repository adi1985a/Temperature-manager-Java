# Temperature Management Application

## Overview
Temperature Management Application is a Java Swing-based desktop tool for recording and viewing temperature measurements. It allows users to input names and temperatures, stores data in an SQLite database, and displays average temperatures in a sortable table. Double-clicking a table row shows detailed measurements.

## Features
- **Data Entry**: Input first name, last name, and temperature (°C), automatically converted to °F.
- **Database Storage**: Stores data in an SQLite database (`BazaSQL/BazaSQLite.db`).
- **Table Display**: Shows average temperatures (°C and °F) per person in a sortable table.
- **Detailed View**: Double-click a table row to view all measurements for a person.
- **Input Validation**: Ensures all fields are filled and temperature is numeric.
- **Error Handling**: Displays user-friendly error messages for database or input issues.

## Requirements
- Java 8 or higher
- Libraries:
  - `sqlite-jdbc` (SQLite JDBC driver)
- SQLite database: `BazaSQL/BazaSQLite.db` with `osoby` and `pomiary` tables

Install the SQLite JDBC driver using Maven (add to `pom.xml`):
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.42.0</version>
</dependency>
```

## Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
2. Ensure the SQLite JDBC driver is included in the project (e.g., via Maven or manual JAR inclusion).
3. Create the SQLite database (`BazaSQL/BazaSQLite.db`) with the following schema:
   ```sql
   CREATE TABLE osoby (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       nazwisko TEXT,
       imie TEXT
   );
   CREATE TABLE pomiary (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       id_osoby INTEGER,
       temperatura_c REAL,
       temperatura_f REAL,
       data TEXT,
       FOREIGN KEY (id_osoby) REFERENCES osoby(id)
   );
   ```
4. Compile and run the application:
   ```bash
   javac TemperaturaUI.java
   java TemperaturaUI
   ```

## Usage
1. Launch the application to open the GUI.
2. **Interface**:
   - **Left Panel**: Enter first name, last name, and temperature (°C), then click "Zapisz dane" to save.
   - **Center Table**: Displays names and average temperatures (°C and °F), sortable by column.
   - **Bottom Panel**: Instructs to double-click a row for detailed measurements.
   - **Details Dialog**: Shows all temperature records for the selected person.
3. **Actions**:
   - Input validation prevents empty fields or non-numeric temperatures.
   - Double-click a table row to view a dialog with all measurements (date, °C, °F).
   - Errors (e.g., database issues) display pop-up messages.

## File Structure
- `TemperaturaUI.java`: Main Java file with GUI, database logic, and event handling.
- `BazaSQL/BazaSQLite.db`: SQLite database file (must be created with the specified schema).
- `README.md`: This file, providing project documentation.

## Notes
- Ensure the `BazaSQL/` directory exists and is writable for the SQLite database.
- The database must have the `osoby` and `pomiary` tables pre-created as shown above.
- The app uses `sqlite-jdbc` for database connectivity; include the driver in the classpath.
- Text labels use Polish characters (e.g., "Nazwisko", "Temperatura °C"); encoding issues may arise if not handled properly.
- The table is non-editable and sorted by first name by default.

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make changes and commit (`git commit -m "Add feature"`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For questions or feedback, open an issue on GitHub or contact the repository owner.