# client_server_tcp

Das vorliegende Programm soll die Anforderungen der Aufgabe 1 der Programmierübung
Kommunikationssysteme WS17/18 (Client/Server Paradigma) erfüllen.

## Allgemein
Der Server antwortet auf Anfragen des Clients, die einen Staedtenamen beinhalten,
mit den Temperaturinformationen die die [Open Weather Map Api](http://openweathermap.org/api)
(OWM) breitstellt. Hierzu muss beim Ausführen der Klasse main.java.WeatherServer neben der
ServerSocket Port-Nummer auch ein valider OWM Key mitgegeben werden.

* valider OWM-Key: XXXXXXXXX

Desweiteren ist zu beachten dass das Programm zur Verarbeitung der OWM-Api Response
die [JSON-Java-Referenz-Implementation](https://github.com/stleary/JSON-java) des Github Accounts
'stleary' nutzt. Sie befindet sich im 'lib' Ordner der Anwendung. 