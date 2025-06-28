@echo off
echo Downloading SQLite JDBC driver...
powershell -Command "Invoke-WebRequest -Uri 'https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar' -OutFile 'lib/sqlite-jdbc-3.36.0.3.jar'"
echo Driver downloaded successfully!
pause 