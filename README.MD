# 🧩 Plataforma de Gestión de Cuentas

Este proyecto contiene una solución basada en microservicios para la gestión de clientes, cuentas y autenticación. Está diseñada para ejecutarse completamente en Docker.

---

## 📦 Microservicios incluidos

- `ms-customers`: Gestión de clientes
- `ms-accounts`: Gestión de cuentas y movimientos
- `ms-token`: Servicio de autenticación y emisión de tokens

---

## 🚀 Requisitos

- Docker 29.1.2+
- Docker Compose v5.0.0+
- Java 21 (para desarrollo local)
- SQL Server (se incluye en Docker)
- Kafka y Zookeeper (se incluye en Docker)
- Redis (se incluye en Docker)

---

## 🐳 Despliegue con Docker

Para levantar toda la solución:

```bash
docker compose up -d
```
----
Si existiria algun conflicto entre otros contenedores ya preinstalados puede utilizar, para que se creen los de este proyecto


```bash
docker compose up --build --force-recreate --remove-orphans
```