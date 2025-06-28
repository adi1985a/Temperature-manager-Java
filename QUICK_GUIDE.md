# 🚀 Krótka Instrukcja Użytkownika

## 🌡️ Temperature Management System
**Autor: Adrian Lesniak**

---

## 📋 Jak Uruchomić
```bash
java -cp "src;lib/sqlite-jdbc-3.36.0.3.jar" TemperaturaUI
```

---

## 🎯 Jak Używać Programu

### 1. **Dodawanie Pomiaru Temperatury**
1. **Nazwisko**: Wpisz nazwisko (np. Smith, Kowalski)
2. **Imię**: Wpisz imię (np. John, Jan)
3. **Temperatura**: Wpisz temperaturę w stopniach Celsjusza (np. 36.5)
4. Kliknij **"➕ Add Measurement"**

### 2. **Przeglądanie Danych**
- **Tabela** pokazuje średnie temperatury dla każdej osoby
- **Kliknij dwukrotnie** na wiersz, aby zobaczyć szczegóły
- **Sortuj** klikając na nagłówki kolumn

### 3. **Dodatkowe Funkcje**
- **📊 Export Data**: Eksportuj dane do pliku CSV
- **📈 Statistics**: Zobacz statystyki (średnie, min/max)
- **🗑️ Clear Fields**: Wyczyść pola formularza

---

## ⚠️ Ważne Informacje
- Temperatura musi być liczbą (np. 36.5)
- Nie może być niższa niż -273.15°C (zero absolutne)
- Wszystkie pola muszą być wypełnione
- Dane są automatycznie zapisywane w bazie SQLite

---

## 🆘 Rozwiązywanie Problemów
- **Błąd "No suitable driver"**: Upewnij się, że plik `lib/sqlite-jdbc-3.36.0.3.jar` istnieje
- **Błąd "ClassNotFoundException"**: Sprawdź czy pliki .class są w folderze `src`
- **Baza danych nie działa**: Sprawdź czy `BazaSQL/BazaSQLite.db` istnieje

---

## 📞 Wsparcie
- Sprawdź `README.markdown` dla pełnej dokumentacji
- Sprawdź `RUN_INSTRUCTIONS.md` dla szczegółowych instrukcji uruchomienia
- Sprawdź `CHANGELOG.md` dla listy zmian

---

**🎉 Miłego korzystania z programu!** 