## About
This project is a Front End in Vaadin framework created for [DentCLinicApp Rest API](https://github.com/AlbertHeesch/DentClinicApp).  
It has been made in microservices technology.

## Preview
[![](https://i.snipboard.io/2C6eZG.jpg)](https://i.snipboard.io/pHEf43.jpg)
[![](https://i.snipboard.io/ymx4Gq.jpg)](https://i.snipboard.io/2CophI.jpg)
[![](https://i.snipboard.io/nsETfJ.jpg)](https://i.snipboard.io/QjR4O0.jpg)
[![](https://i.snipboard.io/x9cg6J.jpg)](https://i.snipboard.io/JVeli1.jpg)
[![](https://i.snipboard.io/XJtPWp.jpg)](https://i.snipboard.io/cQUwoO.jpg)

## Access
At the bottom of the log in view there are all necessary credentials you need to explore my application.  
User has access only to the dentist view.  
Admin has unlimited access.

## Requirements
Java 11

Gradle

## Microservices
In order to run my application properly please use these projects:
- Configuration server - https://github.com/AlbertHeesch/cloud,
- Discovery - https://github.com/AlbertHeesch/discovery,
- Gateway - https://github.com/AlbertHeesch/gateway,
- Main Back End - https://github.com/AlbertHeesch/DentClinicApp,
- Rates Back End - https://github.com/AlbertHeesch/DentClinicAppRates.

## How to run
Build your gradle with `gradlew build` in terminal.

Run the projects in order:
- Configuration server,
- Discovery,
- Gateway,
- DentAppClinic & DentAppClinicRates,
- DentAppClinicView.

Enter the http://localhost:8085/home page in your browser.