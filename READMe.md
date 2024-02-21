# BikeMed Migration Example

Die BikeMed-Migrationsbeispielanwendung besteht aus verschiedenen Komponenten,
darunter das Vue.js-Frontend (BikeMed), die Bike-Med-API, den Dispatcher (Bike-Med-Dispatcher),
das Lager (Bike-Med-Lager), die Werkstatt (Bike-Med-Werkstatt) und das Office (Bike-Med-Office).

Diese Anwendung ist als On-Premise-Anwendung konzipiert und dient als Beispiel für die Migration in die Cloud.

## Komponentenübersicht


1. **BikeMed (Frontend)**: Eine Vue.js-basierte Frontend-Anwendung, in der Diagnosedaten erfasst werden.

2. **BikeMed-Commons**: Ein gemeinsames Modul, das von der API, dem Dispatcher, dem Lager, der Werkstatt und dem Office verwendet wird.
    Hier sollen Methoden und Klassen definiert werden, die von mehreren Komponenten verwendet werden.

2. **BikeMed-API**: Die API, die die vom Frontend erfassten Diagnosedaten verarbeitet und Events an den Dispatcher sendet.

3. **BikeMed-Dispatcher**: Nimmt Events von der API entgegen und leitet sie an Lager und Werkstatt weiter.
Empfängt auch Events von Lager und Werkstatt und leitet sie an das Office weiter.

4. **BikeMed-Lager**: Empfängt Events vom Dispatcher, fügt benötigte Komponenten hinzu und sendet ein Event zurück an den Dispatcher.

5. **BikeMed-Werkstatt**: Empfängt Events vom Dispatcher, berechnet Stundensätze, benötigte Mitarbeiter anhand der Diagnose und sendet ein Event zurück an den Dispatcher.

6. **BikeMed-Office**: Empfängt Events vom Dispatcher, aggregiert alle Daten und speichert sie. Bietet eine API zum Abrufen von PDF-Berichten basierend auf den gespeicherten Daten.

7. **PostgreSQL**: 3 verschiedene PostgreSQL-Instanzen, die von Lager, Werkstatt und Office verwendet werden.

8. **Erlang/OTP**: Wird von RabbitMQ und den Komponenten der BikeMed-Anwendung verwendet.

8. **RabbitMQ**: Wird von der API, dem Dispatcher, dem Lager, dem Office und der Werkstatt verwendet, um Events zu senden und zu empfangen.

## Idee von der Anwendung

Es soll der User eine Diagnose erfassen können.

Nur die Shops sollen zugang zu bike-med-lager haben.
Nur die Werkstatt soll zugang zu bike-med-werkstatt haben.

Die Diagnose soll an die Lager und Werkstatt weitergeleitet werden,
diese sollen selber die Daten erfassen die dann in der Rechnung an das Office weitergeleitet werden.

Das Office soll eine Rechnung erstellen und diese als PDF über die API bereitstellen.


