# 🌡️📊 JavaSwing TempTracker DB: Temperature Logging & Analysis 🇵🇱
_A Java Swing-based desktop application for recording, viewing, analyzing, importing, and exporting temperature measurements, storing data in an SQLite database, with a modern English user interface and professional graphics integration._

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-%3E%3D8-007396.svg?logo=java&logoColor=white)](https://www.java.com/)
[![Swing](https://img.shields.io/badge/UI-Java%20Swing-orange.svg)]()
[![SQLite](https://img.shields.io/badge/Database-SQLite-003B57.svg?logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![JDBC](https://img.shields.io/badge/Connectivity-SQLite%20JDBC-blue.svg)]()
[![JFreeChart](https://img.shields.io/badge/Charts-JFreeChart-green.svg)]()
[![Version](https://img.shields.io/badge/Version-2.2-blue.svg)]()
[![Platform](https://img.shields.io/badge/Platform-Cross--Platform-lightgrey.svg)]()
[![Status](https://img.shields.io/badge/Status-Active-success.svg)]()

## 📋 Table of Contents
1. [Overview](#-overview)
2. [Key Features](#-key-features)
3. [Screenshots (Conceptual)](#-screenshots-conceptual)
4. [System & Database Requirements](#-system--database-requirements)
5. [Database Schema (`BazaSQL/BazaSQLite.db`)](#-database-schema-bazasqlbazsqlite.db)
6. [Installation and Setup](#️-installation-and-setup)
7. [Application Usage](#️-application-usage)
8. [File Structure](#-file-structure)
9. [Graphics Integration](#-graphics-integration)
10. [Technical Notes](#-technical-notes)
11. [Troubleshooting](#-troubleshooting)
12. [Contributing](#-contributing)
13. [License](#-license)
14. [Contact](#-contact)

## 📄 Overview

**JavaSwing TempTracker DB** (from main class `TemperaturaUI`), developed by **Adrian Lesniak**, is a modern desktop application built using Java Swing for comprehensive temperature measurement management. Users can input a person's first name, last name, and a temperature reading in Celsius (°C). This data, along with an automatic conversion to Fahrenheit (°F) and a timestamp, is then stored in an **SQLite database** (`BazaSQL/BazaSQLite.db`). 

<br> 
<p align="center">
  <img src="screenshots/1.gif" width="70%">
</p>
<br>

The application features a **professional modern interface** with:
- 🖥️ **Optimized window size** (1400x900) for better button visibility
- 🎨 **Professional graphics integration** (icons, backgrounds, logos)
- 📊 **Interactive data table** with sorting and action buttons
- 🔄 **Advanced features** including data import/export, statistics, and detailed measurements view
- ✨ **Enhanced UI** with hover effects, modern styling, and responsive layout

The main window displays a sortable table of individuals with their average recorded temperatures. Double-clicking a row opens a detailed view showing all historical measurements for that specific person. The UI includes comprehensive input validation, user-friendly error messages, and a modern English interface with professional graphics.

## ✨ Key Features

### 👤 **Data Entry & Storage**:
- 📝 Input fields for First Name, Last Name, and Temperature in Celsius (°C)
- 🔄 Automatic conversion of Celsius to Fahrenheit: `°F = (°C × 9/5) + 32`
- 💾 Data (name, surname, temperatures °C & °F, date/timestamp) is persistently stored in an SQLite database
- 🧠 **Smart field management** with auto-clear functionality and placeholder text

### 📊 **Advanced Table Display & Sorting**:
- 📋 A central `JTable` displays a summary for each person: First Name, Last Name, Average Temperature (°C), Average Temperature (°F), and Measurements Count
- ⚡ **Action buttons** in each row for quick edit and add temperature operations
- 🔍 The table is sortable by clicking on column headers, allowing easy organization of data
- 🎯 **Centered content** for better readability and professional appearance

### 📋 **Detailed Measurement View**:
- 🖱️ Double-clicking a row in the main table opens a new dialog (`JDialog`)
- 📈 This dialog shows all individual temperature measurements recorded for the selected person
- 📅 Displays date/timestamp, °C value, and °F value in a formatted table
- ⬇️ Sorted by most recent measurements first

### 📤 **Data Export & Import**:
- 📤 **Export functionality**: Save all data to CSV format with timestamped filenames
- 📥 **Import functionality**: Import temperature data from CSV files with validation
- 🔄 Supports bulk data operations with error handling

### 📈 **Statistics Dashboard**:
- 📊 Comprehensive statistics including totals, averages, min/max temperatures
- 🎨 Professional dialog display with formatted data presentation
- ⚡ Real-time calculation of statistical measures

### ✔️ **Input Validation & Error Handling**:
- ✅ Ensures that all input fields (first name, last name, temperature) are filled before saving
- 🔢 Validates that the temperature input is a valid numeric value
- ❄️ Prevents temperatures below absolute zero (-273.15°C)
- 💬 Displays user-friendly error messages via `JOptionPane` for various issues

### 🎨 **Modern UI & Graphics**:
- 🎨 **Professional color scheme** with blue-based theme and color coding
- 🖼️ **Icon integration** for all buttons (add, export, statistics, clear, import)
- 🖼️ **Background graphics** for header and panels
- 🏷️ **Logo integration** for application and author branding
- ✨ **Hover effects** and interactive animations
- 📱 **Responsive layout** optimized for 1400x900 resolution

### 💾 **SQLite Database Integration**: 
- 🗄️ Utilizes an SQLite database for robust data storage and retrieval
- 🔌 Accessed via the SQLite JDBC driver with proper connection management
- 🔧 Automatic database creation and schema management

## 🖼️ Screenshots (Conceptual)

_Screenshots of: the main application window with the input panel and the summary table, an example of the detailed measurements dialog after double-clicking a row, the statistics dashboard, and the import/export dialogs._

<p align="center">
  <img src="screenshots\1.jpg" width="300"/>
  <img src="screenshots\2.jpg" width="300"/>
  <img src="screenshots\3.jpg" width="300"/>
  <img src="screenshots\4.jpg" width="300"/>
  <img src="screenshots\5.jpg" width="300"/>
  <img src="screenshots\6.jpg" width="300"/>
  <img src="screenshots\7.jpg" width="300"/>
  <img src="screenshots\8.jpg" width="300"/>
  <img src="screenshots\9.jpg" width="300"/>
  <img src="screenshots\10.jpg" width="300"/>
  <img src="screenshots\11.jpg" width="300"/>
</p>


## ⚙️ System & Database Requirements

### 🖥️ Software:
- ☕ **Java Development Kit (JDK)**: Java 8 or higher (recommended: Java 17+)
- 🚀 **Java Runtime Environment (JRE)**: Required to run the compiled application
- 🔌 **SQLite JDBC Driver**: Version 3.36.0.3 or higher
- 📊 **JFreeChart Library**: Version 1.5.4 for charting capabilities
- 🛠️ **JCommon Library**: Version 1.0.24 for chart utilities

### 🗄️ Database:
- 📁 **SQLite Database File**: A database file named `BazaSQLite.db` located in a subfolder `BazaSQL/` (i.e., `BazaSQL/BazaSQLite.db`) relative to the application's execution path
- 📋 **Predefined Schema**: This database file must be pre-created with two specific tables: `osoby` (persons) and `pomiary` (measurements). See the "Database Schema" section below for details

### 💻 Operating System:
- 🌍 Any OS that supports Java and Swing (e.g., Windows, macOS, Linux)
- 🖥️ **Display**: Minimum resolution 1400x900 (optimized for this size)

### 🎨 Graphics:
- 🖼️ **Image files**: PNG/JPG format for icons, backgrounds, and logos
- 📁 **File structure**: Organized in `images/` folder with subdirectories for different types

## 💾 Database Schema (`BazaSQL/BazaSQLite.db`)

The SQLite database (`BazaSQL/BazaSQLite.db`) must contain the following tables:

### 1. **`osoby` (Persons) Table**:
    ```sql
    CREATE TABLE osoby (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nazwisko TEXT NOT NULL, -- Last Name
        imie TEXT NOT NULL      -- First Name
    );
    ```

### 2. **`pomiary` (Measurements) Table**:
    ```sql
    CREATE TABLE pomiary (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        id_osoby INTEGER NOT NULL,          -- Foreign key referencing osoby.id
        temperatura_c REAL NOT NULL,        -- Temperature in Celsius
        temperatura_f REAL NOT NULL,        -- Temperature in Fahrenheit
        data TEXT NOT NULL,                 -- Date/Timestamp of the measurement
        FOREIGN KEY (id_osoby) REFERENCES osoby(id)
    );
    ```

### 3. **Recommended Indexes**:
    ```sql
    CREATE INDEX idx_osoby_name ON osoby(nazwisko, imie);
    CREATE INDEX idx_pomiary_date ON pomiary(data);
    ```

## 🛠️ Installation and Setup

### 1. **Clone or Download the Source Code**:
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

### 2. **Set Up SQLite Database**:
    - 📁 Create a subfolder named `BazaSQL` in your project's root directory
    - 🗄️ Using an SQLite database tool (e.g., DB Browser for SQLite, sqlite3 command-line tool), create a new database file named `BazaSQLite.db` inside the `BazaSQL` folder
    - 📋 Execute the SQL commands provided in the "Database Schema" section above to create the `osoby` and `pomiary` tables

### 3. **Include Required Libraries**:
    - **If using Maven/Gradle**: Add the dependencies to your `pom.xml`:
        ```xml
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.36.0.3</version>
        </dependency>
        <dependency>
            <groupId>org.jfree</groupId>
            <artifactId>jfreechart</artifactId>
            <version>1.5.4</version>
        </dependency>
        <dependency>
            <groupId>org.jfree</groupId>
            <artifactId>jcommon</artifactId>
            <version>1.0.24</version>
        </dependency>
        ```
    - **If compiling manually**: Download the JAR files and place them in the `lib/` folder:
        - 📦 `sqlite-jdbc-3.36.0.3.jar`
        - 📊 `jfreechart-1.5.4.jar`
        - 🛠️ `jcommon-1.0.24.jar`

### 4. **Compile the Java Application**:
    ```bash
    # Using Java directly (Recommended)
    javac -cp "lib/*" src/TemperaturaUI.java
    
    # Using Maven
    mvn clean compile
    ```

### 5. **Run the Java Application**:
    ```bash
    # Option 1: Using Java directly
    java -cp "src;lib/*" TemperaturaUI
    
    # Option 2: Using JAR file
    java -jar Adrian_Lesniak_Zadanie4_Fixed.jar
    
    # Option 3: Using Maven
    mvn exec:java -Dexec.mainClass="TemperaturaUI"
    ```

## 💡 Application Usage

### 1. **Main Window Interface**:
    - 🏷️ **Title Bar**: Application title with professional logo
    - 📝 **Left Panel (Data Entry)**:
        - 📋 "Last Name" text field with placeholder text
        - 📋 "First Name" text field with placeholder text
        - 🌡️ "Temperature (°C)" text field with placeholder text
        - 🔘 Action buttons: "Add Measurement", "Export", "Statistics", "Clear Fields", "Import"
    - 📊 **Center Table**: Displays a list of individuals with their average recorded temperatures in Celsius and Fahrenheit, measurement counts, and action buttons
    - 📍 **Bottom Panel**: Contains status messages and instructions

### 2. **Core Actions**:
    - **Adding a Temperature Record**:
        1. 📝 Enter the person's first name, last name, and the temperature in Celsius
        2. ➕ Click the "Add Measurement" button
        3. ✅ The application validates the input and saves to the database
        4. 🔄 The summary table refreshes to reflect the new data
    - **Viewing Detailed Measurements**:
        1. 🖱️ Double-click on a row in the center table
        2. 📊 A dialog appears showing all temperature measurements for that person
    - **Exporting Data**:
        1. 📤 Click the "Export" button
        2. 📁 Choose save location and filename
        3. 💾 Data is exported to CSV format
    - **Importing Data**:
        1. 📥 Click the "Import" button
        2. 📁 Select CSV file to import
        3. ✅ Data is validated and imported to database
    - **Viewing Statistics**:
        1. 📈 Click the "Statistics" button
        2. 📊 View comprehensive data analysis in a formatted dialog

### 3. **Advanced Features**:
    - 🔍 **Table Sorting**: Click column headers to sort data
    - ⚡ **Quick Actions**: Use in-table buttons for edit and add temperature operations
    - 🧹 **Field Management**: Auto-clear functionality and smart focus management
    - ⚠️ **Error Handling**: Comprehensive validation with user-friendly messages

## 🗂️ File Structure

```
Exercise 4/
├── src/
│   └── TemperaturaUI.java          # Main application file
├── images/                         # Graphics folder
│   ├── icons/                      # Button and app icons
│   │   ├── app_icon.png           # Application icon
│   │   ├── add_button.png         # Add measurement button
│   │   ├── export_button.png      # Export button
│   │   ├── stats_button.png       # Statistics button
│   │   └── clear_button.png       # Clear fields button
│   ├── backgrounds/                # Panel and header backgrounds
│   │   ├── header_bg.jpg          # Header background
│   │   └── panel_bg.jpg           # Panel background
│   ├── logos/                      # Application and author logos
│   │   ├── app_logo.png           # Application logo
│   │   └── author_logo.png        # Author logo
│   └── charts/                     # Generated chart images
├── lib/                            # Library files
│   ├── sqlite-jdbc-3.36.0.3.jar   # SQLite JDBC driver
│   ├── jfreechart-1.5.4.jar       # Charting library
│   └── jcommon-1.0.24.jar         # Common utilities
├── BazaSQL/                        # Database folder
│   └── BazaSQLite.db              # SQLite database
├── target/                         # Compiled classes
├── pom.xml                         # Maven configuration
├── manifest.txt                    # JAR manifest file
├── README.markdown                 # This documentation file
├── QUICK_GUIDE.md                  # Quick user guide
├── RUN_INSTRUCTIONS.md             # Detailed run instructions
├── GRAPHICS_INTEGRATION.md         # Graphics integration guide
└── CHANGELOG.md                    # Version history
```

## 🎨 Graphics Integration

### Successfully Integrated Graphics:
- 🖼️ **Icons**: `images/icons/` - All button icons (add, export, statistics, clear, import)
- 🖼️ **Backgrounds**: `images/backgrounds/` - Header and panel backgrounds
- 🏷️ **Logos**: `images/logos/` - Application and author logos
- 📊 **Charts**: `images/charts/` - Ready for generated chart images

### Graphics Features:
- 🏷️ **Application Icon**: Professional app icon in window title bar
- 🔘 **Button Icons**: Visual identification for all action buttons
- 🖼️ **Background Images**: Professional header and panel backgrounds
- 🏷️ **Logo Integration**: Application and author logos for branding
- ⚠️ **Error Handling**: Graceful fallback if graphics are missing

### Graphics Specifications:
- 📁 **Format**: PNG, JPG for optimal display
- 📏 **Resolution**: 72-300 DPI for optimal display
- 💾 **Size**: Optimized for desktop use (under 2MB per image)
- 📝 **Naming**: Descriptive names following established conventions

## 📝 Technical Notes

### **SQLite Database**: 
- 🗄️ The application uses a local SQLite database file, making it self-contained once the database is initialized with the correct schema
- 🔧 Automatic database creation and connection management
- 🔗 Proper foreign key relationships and indexing for performance

### **JDBC Driver**: 
- 🔌 Connectivity to the SQLite database is achieved using the `sqlite-jdbc` driver
- 📦 Version 3.36.0.3 or higher recommended
- 🔧 Proper connection pooling and resource management

### **UI Framework**: 
- 🖥️ Built entirely with Java Swing for cross-platform compatibility
- 🎨 Modern styling with custom colors and fonts
- 📱 Responsive layout optimized for 1400x900 resolution
- 🖼️ Professional graphics integration with error handling

### **Data Validation**: 
- ✅ Comprehensive input validation for all fields
- 🌡️ Temperature range validation (above absolute zero)
- 🔒 Database constraint enforcement
- 💬 User-friendly error messages

### **Performance Considerations**: 
- ⚡ Efficient database queries with proper indexing
- 🎨 Optimized table rendering with custom cell renderers
- 💾 Memory-efficient graphics loading
- 🔄 Responsive UI with proper event handling

### **Concurrency**: 
- 🧵 Swing operations performed on the Event Dispatch Thread (EDT)
- 🗄️ Database operations handled efficiently
- 🧹 Proper resource cleanup and memory management

## 🐛 Troubleshooting

### Common Issues:

#### **Database Connection Error**:
- 🔌 Ensure SQLite JDBC driver is in classpath
- 🔐 Check database file permissions
- 📋 Verify database schema is correct
- 📁 Ensure `BazaSQL/` folder exists

#### **UI Display Issues**:
- ☕ Update Java to latest version (8+ recommended)
- 🖥️ Check system display settings
- 📏 Ensure sufficient screen resolution (minimum 1400x900)
- 🔍 If buttons are not visible, try maximizing the window

#### **Graphics Loading Issues**:
- 🖼️ Check that image files exist in correct folders
- ⚠️ Application will work without graphics (graceful fallback)
- 📝 Console messages indicate missing graphics files
- 🔐 Verify file permissions for image access

#### **Performance Issues**:
- 🚫 Close unnecessary applications
- 💾 Ensure adequate RAM (512MB+)
- 📊 Check database file size
- ⚙️ Verify Java heap memory settings

### Error Messages:
- ⚠️ **"All fields must be filled"**: Complete all input fields
- 🔢 **"Temperature must be a valid number"**: Enter numeric temperature value
- ❄️ **"Temperature cannot be below absolute zero"**: Temperature validation error
- 🖼️ **"Could not load button icon"**: Graphics file missing (non-critical)

## 🤝 Contributing

Contributions to the **JavaSwing TempTracker DB** application are welcome! If you have ideas for:

- 🎨 Enhancing the user interface or adding more visual themes
- 📊 Implementing more advanced statistical analysis or charting of temperature data
- 📤 Adding features like data export/import enhancements
- ⚠️ Improving error handling or database interaction efficiency
- 🔧 Refactoring for better separation of concerns (e.g., dedicated data access objects - DAO)
- 🖼️ Adding new graphics or improving existing ones

### Contribution Process:
1. 🍴 Fork the repository
2. 🌿 Create a new branch for your feature (`git checkout -b feature/NewFeature`)
3. ✏️ Make your changes to the Java source files
4. 🖼️ Ensure graphics are properly integrated if adding new UI elements
5. 🧪 Test thoroughly on different platforms
6. 💾 Commit your changes (`git commit -m 'Feature: Add new functionality'`)
7. 📤 Push to the branch (`git push origin feature/NewFeature`)
8. 🔄 Open a Pull Request

### Development Guidelines:
- 📝 Follow Java coding conventions
- 💬 Add comments for complex logic
- 🧪 Test thoroughly before submitting
- 📚 Update documentation for new features
- 🖼️ Ensure graphics are properly integrated
- 🌍 Maintain cross-platform compatibility

## 📃 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## Contact
For questions or feedback, open an issue on GitHub or contact the repository owner.
