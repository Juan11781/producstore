# Product Store - Sistema de Ventas basado en Microservicios
Este proyecto implementa un sistema completo de ventas utilizando una arquitectura de microservicios en Java con Spring Boot. Esta disenado para gestionar autenticacion, productos, ordenes, historial de stock y 
notificaciones, utilizando tecnologias modernas como kafka, Docker y JWT


## Tecnologias y herramientas

-**Java 17**
-**Spring Boot 3.5**
-**Spring Security + JWT**
-**Kafka + Zookeper**
-**Docker + Docker Compose**
-**SendGrind (Servicio de correo)**
-**Eureka (Service de Discovery)**W
-**Swagger/OpenApi**
-**CI/CD y monitoreo**

## 🧱 Microservicios

| Servicio              | Descripcion                                                             |
|----------------------|--------------------------------------------------------------------------|
| `auth-service`       | Registro, login, validacion de tokens JWT.                              |
| `product-service`    | Gestion de productos y stock. Incluye historial de movimientos.         |
| `order-service`      | Creacion de ordenes de compra, validacion de stock.                     |
| `notification-service`| Envia correos de confirmacion a traves de Kafka y SendGrid.             |
| `stock-history-service`| [En desarrollo] Almacenar y consultar el historial de stock de manera independiente.. |


## ⚙️ Configuracion

### Variables sensibles (`.env`)
