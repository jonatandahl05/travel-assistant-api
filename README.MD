# Travel Assistant API

Travel Assistant API är en Spring Boot-applikation som hjälper användaren att hitta aktiviteter baserat på väderförhållanden i en vald stad.

När användaren skickar in en stad hämtar applikationen aktuell väderinformation från WeatherAPI. Baserat på vädret avgör systemet om aktiviteterna bör vara främst utomhus-, inomhus- eller blandade aktiviteter. Därefter hämtas relevanta platser och sevärdheter från Geoapify och presenteras som rekommendationer.

Projektet utvecklades för att öva på arbete med externa API:er, autentisering, felhantering och dokumentation i Spring Boot.

## Funktioner

- Hämtar aktuellt väder för en stad
- Rekommenderar aktiviteter baserat på väderförhållanden
- Integrerar med externa API:er
- Query Authentication med API-nycklar
- Bearer Authentication demonstrerad via httpbin
- Fallback-lösningar om externa tjänster inte svarar
- Swagger/OpenAPI-dokumentation
- Automatiserade tester

## Tekniker

- Java 21
- Spring Boot
- Maven
- WebClient
- Lombok
- Swagger / OpenAPI

## API Endpoints

### Hämta rekommendationer

http GET /api/recommendations?city=Malmo 

Exempel på svar:

json {   "city": "Malmo",   "weather": {     "condition": "Partly cloudy",     "category": "Mixed"   },   "recommendedCategory": "Mixed" } 

### Bearer Authentication Demo

http GET /api/auth-demo/bearer 

## Autentisering

### Query Authentication

WeatherAPI och Geoapify använder API-nycklar som skickas som query-parametrar i URL:en.

### Bearer Authentication

Bearer Authentication demonstreras genom ett anrop till httpbin där en Bearer Token skickas i Authorization-headern.

## Starta projektet

bash mvn spring-boot:run 

## Swagger

När applikationen körs finns dokumentationen på:

text http://localhost:8080/swagger-ui.html 

## Tester

Kör testerna med:

bash mvn test 
