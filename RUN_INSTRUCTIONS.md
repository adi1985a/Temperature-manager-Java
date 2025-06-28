# 🚀 Instrukcje Uruchomienia Programu

## ✅ Problem Rozwiązany!

Program został pomyślnie ulepszony i jest gotowy do uruchomienia. Oto jak go uruchomić:

## 📋 Wymagania

- **Java 8 lub nowsza** (sprawdź: `java -version`)
- **Sterownik SQLite** (już pobrany do folderu `lib/`)
- **Baza danych SQLite** (już istnieje w `BazaSQL/BazaSQLite.db`)

## 🎯 Sposoby Uruchomienia

### Metoda 1: Uruchomienie z Kompilowanego Kodu (Zalecane)
```bash
# Uruchom z nowym kodem (ulepszona wersja)
java -cp "target/classes;lib/sqlite-jdbc-3.36.0.3.jar" TemperaturaUI
```

### Metoda 2: Uruchomienie z Oryginalnego JAR
```bash
# Uruchom oryginalną wersję (polska)
java -jar Adrian_Lesniak_Zadanie4.jar
```

### Metoda 3: Kompilacja i Uruchomienie
```bash
# Kompilacja
javac -cp "lib/sqlite-jdbc-3.36.0.3.jar" src/TemperaturaUI.java

# Uruchomienie
java -cp "src;lib/sqlite-jdbc-3.36.0.3.jar" TemperaturaUI
```

## 🔧 Rozwiązywanie Problemów

### Problem: "No suitable driver found"
**Rozwiązanie**: Upewnij się, że sterownik SQLite jest w classpath:
```bash
java -cp "target/classes;lib/sqlite-jdbc-3.36.0.3.jar" TemperaturaUI
```

### Problem: "ClassNotFoundException"
**Rozwiązanie**: Sprawdź czy pliki .class istnieją:
```bash
dir target\classes
```

### Problem: Baza danych nie działa
**Rozwiązanie**: Sprawdź czy baza istnieje:
```bash
dir BazaSQL\BazaSQLite.db
```

## 📁 Struktura Plików

```
Exercise 4/
├── src/TemperaturaUI.java          # Źródłowy kod (ulepszony)
├── target/classes/                 # Skompilowane pliki
├── lib/sqlite-jdbc-3.36.0.3.jar   # Sterownik SQLite
├── BazaSQL/BazaSQLite.db          # Baza danych
├── images/                         # Folder na grafiki
└── Adrian_Lesniak_Zadanie4.jar    # Oryginalny JAR
```

## 🎨 Nowe Funkcje

Po uruchomieniu zobaczysz:
- **Nowoczesny interfejs** w języku angielskim
- **Kolorowe przyciski** z efektami hover
- **Dodatkowe funkcje**:
  - 📊 Eksport danych do CSV
  - 📈 Statystyki
  - 🗑️ Czyszczenie pól
  - 💡 Komunikaty statusu

## 🖼️ Dodawanie Grafiki

Aby dodać własne obrazy:
1. Umieść pliki JPG/PNG w odpowiednich folderach:
   - `images/icons/` - ikony
   - `images/backgrounds/` - tła
   - `images/logos/` - loga
   - `images/charts/` - wykresy
2. Sprawdź `images/README.md` dla szczegółów

## 📞 Wsparcie

Jeśli masz problemy:
1. Sprawdź czy Java jest zainstalowana: `java -version`
2. Sprawdź czy wszystkie pliki są na miejscu
3. Użyj dokładnie tych komend z classpath
4. Sprawdź `README.markdown` dla pełnej dokumentacji

---

**🎉 Program jest gotowy do użycia!** 