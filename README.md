# Pet Adoption App

Disclaimer: if the pushes seems like from another person is because of the pc i pushed the repo

This is a full-stack pet adoption application with a Vue.js frontend, a Spring Boot (Kotlin) backend, and a MySQL database.  
Everything runs easily with Docker Compose.

---

## Features

- **User authentication** (JWT)
- **Role-based access:** Admin and Client
- **CRUD for pets:** Create, view, update, and delete pets (admin only)
- **Adoption process:** Clients can view pets and (optionally) request adoption
- **Responsive and modern UI**

---

## How to Run (Any OS)

### **Requirements**

- [Docker](https://www.docker.com/products/docker-desktop/) and Docker Compose installed  
  (On Mac, you can also use [Colima](https://github.com/abiosoft/colima) if Docker Desktop is not available)

---

### **Steps**

1. **Clone this repository:**

   ```bash
   git clone https://github.com/eargot1/Tech_test_amadeus_2.git
   cd Tech_test_amadeus_2
   ```

2. **Build and start all services:**

   ```bash
   docker-compose up --build
   ```

3. **Open the app in your browser:**
   ```
   http://localhost:5173
   ```

---

### **If you can't use Docker Desktop (Mac/Windows):**

- **Mac:**  
  Use [Colima](https://github.com/abiosoft/colima) as a Docker alternative.
- **Windows:**  
  Use [WSL2 + Docker Engine](https://docs.docker.com/engine/install/ubuntu/) or a Linux VM.

---

## **How to Test**

- **Frontend:**  
  Open [http://localhost:5173](http://localhost:5173) and use the app.
- **Backend:**  
  Test API endpoints with [Postman](https://www.postman.com/) at `http://localhost:8080`.
- **Database:**  
  Connect to MySQL at `localhost:3306` (user: `admin`, password: `local`, database: `pet_adoption`).

---

## **Main Functionalities**

- **Login:**  
  Authenticate as admin or client.
- **Admin:**
  - Create, update, and delete pets.
  - Change pet status (IN_SHELTER, IN_ADOPTION_PROCESS, ADOPTED).
- **Client:**
  - View pets.
  - (Optionally) Request adoption if enabled.
- **Role-based UI:**  
  Buttons and actions are shown/hidden depending on user role.

---

## **Notes**

- All data is reset every time you restart the database container (no persistence).
- For any issues, check the logs with:
  ```bash
  docker-compose logs backend
  docker-compose logs frontend
  docker-compose logs db
  ```
