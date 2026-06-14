# 🌍 Travel Assistant API

Travel Assistant API är en Spring Boot-applikation som hjälper användaren att hitta aktiviteter baserat på aktuellt väder i en vald stad.

När användaren anger en stad hämtar applikationen väderinformation från WeatherAPI. Baserat på väderförhållandena väljs en kategori av aktiviteter, till exempel utomhus, inomhus eller blandade aktiviteter. Därefter hämtas relevanta rekommendationer från Geoapify och returneras till användaren.

## 🚀 Funktioner

- Hämtar aktuellt väder för en stad
- Rekommenderar aktiviteter baserat på väderförhållanden
- Integration med WeatherAPI och Geoapify
- Query Authentication med API-nycklar
- Bearer Authentication demonstrerad via httpbin
- Fallback-lösningar om externa tjänster inte svarar
- Swagger/OpenAPI-dokumentation
- Automatiserade tester

## 🛠️ Tekniker

- Java 21
- Spring Boot
- Maven
- WebClient
- Lombok
- Swagger / OpenAPI

## 📡 API Endpoints

### Hämta rekommendationer

```http
GET /api/recommendations?city=Malmo
```

Exempel på svar:

```json
{
  "city": "Malmo",
  "weather": {
    "condition": "Partly cloudy",
    "category": "Mixed"
  },
  "recommendedCategory": "Mixed",
  "activities": [
    {
      "name": "Kungsparken",
      "category": "tourism.attraction",
      "address": "Malmö, Sweden"
    }
  ]
}
```

### Bearer Authentication Demo

```http
GET /api/auth-demo/bearer
```

Exempel på svar:

```json
{
  "authenticated": true,
  "token": "demo-token"
}
```

## 🔐 Autentisering

### Query Authentication

WeatherAPI och Geoapify använder API-nycklar som skickas som query-parametrar i URL:en.

```http
https://api.weatherapi.com/v1/current.json?key=API_KEY&q=Malmo
```

### Bearer Authentication

Bearer Authentication demonstreras genom ett anrop till httpbin där en Bearer Token skickas i Authorization-headern.

```http
Authorization: Bearer demo-token
```

## 🛡️ Fallback-hantering

Om WeatherAPI inte svarar eller returnerar ett fel används en fallback-lösning så att applikationen fortfarande kan ge rekommendationer.

Exempel på fallback-svar:

```json
{
  "condition": "Sunny",
  "category": "Outdoor"
}
```

På så sätt fortsätter applikationen fungera även om den externa vädertjänsten är nere.

## ▶️ Starta projektet

```bash
mvn spring-boot:run
```

## 📖 Swagger

När applikationen körs finns dokumentationen på:

```text
http://localhost:8080/swagger-ui.html
```

## ✅ Tester

Kör testerna med:

```bash
mvn test
```
