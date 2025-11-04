# 🔐 Proyecto Fullstack --- Sistema de Código OTP

**React (Frontend) + Spring Boot (Backend) + PostgreSQL**

Este proyecto implementa un sistema completo para la generación, envío y
validación de códigos OTP (One-Time Password), integrando un frontend en
React y un backend REST en Spring Boot con PostgreSQL como base de
datos.

## 📁 Estructura del Proyecto

    root/
    │── backend/        # API REST con Spring Boot
    │── frontend/       # Aplicación React
    └── README.md

# 🖥️ Frontend --- React

El frontend se encarga de:

✅ Solicitar la generación del OTP\
✅ Mostrar formularios para ingresar el OTP\
✅ Enviar solicitudes al backend mediante Axios\
✅ Validar resultados\
✅ Estilos con CSS puro

### Tecnologías usadas

-   React
-   Axios
-   CSS

### Ejecutar frontend

``` bash
cd frontend
npm install
npm run dev
```

### Variables de entorno

    REACT_APP_API_URL=http://localhost:8080/api

# 🔧 Backend --- Spring Boot

✅ Genera OTPs\
✅ Maneja expiración\
✅ Guarda en PostgreSQL\
✅ API REST

### Tecnologías

-   Java 17+
-   Spring Boot 3+
-   Spring Web
-   Spring Data JPA
-   PostgreSQL

### Configuración DB

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/otp_db
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

# 🔗 API REST

  Método   Endpoint              Descripción
  -------- --------------------- -----------------------
  POST     `/api/otp/generate`   Genera OTP
  POST     `/api/otp/validate`   Valida OTP
  DELETE   `/api/otp/clear`      Limpia OTPs expirados

# ▶️ Ejecutar Backend

``` bash
cd backend
mvn clean install
mvn spring-boot:run
```

# 📦 Producción

Frontend y backend se despliegan por separado.

# ✅ Funcionalidades

✅ Generación y validación OTP\
✅ Persistencia en PostgreSQL\
✅ Frontend simple\
✅ API REST segura

# 🧱 Arquitectura

    React + Axios 
         ↓
    REST API (Spring Boot)
         ↓
    PostgreSQL

# 📄 Licencia

MIT
