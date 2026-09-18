# Digital Menu System

A full-stack web application for restaurant table ordering. Customers scan their table number, browse the menu, and place orders directly from their device. Admins manage products, users, and incoming orders through a dedicated dashboard.

## Tech Stack

**Backend**
- Java 17 + Spring Boot 3.5
- Spring Data JPA + Hibernate
- MySQL 8
- Lombok
- JUnit 5 + Mockito (unit tests)

**Frontend**
- React 19 + TypeScript
- Vite + React Router DOM v7
- TanStack React Query + Axios
- Vitest + Testing Library (unit tests)

**Infrastructure**
- Docker + Docker Compose

## Features

- Table-based menu access — customers navigate to `/mesa/:number` to view the menu and add items to cart
- Order placement with real-time cart management
- Admin dashboard for full CRUD on products, users, and orders
- Product availability toggle
- Role-based views (admin vs. customer)

## Project Structure

```
├── Menu-Back-End/      # REST API (Spring Boot)
│   ├── controller/     # HTTP layer
│   ├── service/        # Business logic
│   ├── repository/     # Data access (JPA)
│   ├── model/          # Entities
│   └── dto/            # Request/Response DTOs
│
├── Menu-Front/         # Web interface (React + Vite)
│   ├── pages/          # Login, AdminPanel, UserMenu
│   ├── componentes/    # CartaoProduto, Carrinho, Forms
│   ├── hooks/          # React Query hooks (produtos, pedidos, usuarios)
│   └── interfaces/     # TypeScript types
│
└── docker-compose.yml  # One-command setup
```

## Getting Started

### With Docker (recommended)

Requires [Docker](https://www.docker.com/) installed.

```bash
git clone https://github.com/eduardo-ramires/TRABALHO_PROJETOWEB.git
cd TRABALHO_PROJETOWEB
docker compose up
```

| Service  | URL                      |
|----------|--------------------------|
| Frontend | http://localhost:5173    |
| Backend  | http://localhost:8080    |

The database is created automatically on first run. To create an initial admin user:

```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Admin","mesa":0,"tipo":"ADM","senha":"admin123"}'
```

### Local Development

**Prerequisites:** Java 17+, Maven, Node.js 18+, MySQL 8

**Backend**

Configure your database credentials in `Menu-Back-End/src/main/resources/application.properties`, then:

```bash
cd Menu-Back-End
./mvnw spring-boot:run
```

API available at `http://localhost:8080`.

**Frontend**

```bash
cd Menu-Front
npm install
npm run dev
```

App available at `http://localhost:5173`.

## API Reference

### Products

| Method | Endpoint          | Description          |
|--------|-------------------|----------------------|
| GET    | /produtos         | List all products    |
| GET    | /produtos/{id}    | Get product by ID    |
| POST   | /produtos         | Create product       |
| PUT    | /produtos/{id}    | Update product       |
| DELETE | /produtos/{id}    | Delete product       |

### Users

| Method | Endpoint          | Description          |
|--------|-------------------|----------------------|
| GET    | /usuarios         | List all users       |
| GET    | /usuarios/{id}    | Get user by ID       |
| POST   | /usuarios         | Create user          |
| PUT    | /usuarios/{id}    | Update user          |
| DELETE | /usuarios/{id}    | Delete user          |

### Orders

| Method | Endpoint          | Description          |
|--------|-------------------|----------------------|
| GET    | /pedidos          | List all orders      |
| GET    | /pedidos/{id}     | Get order by ID      |
| POST   | /pedidos          | Create order         |
| PUT    | /pedidos/{id}     | Update order         |
| DELETE | /pedidos/{id}     | Delete order         |

**Example — Create a product:**

```http
POST /produtos
Content-Type: application/json

{
  "nome": "Margherita Pizza",
  "descricao": "Tomato sauce, mozzarella and fresh basil",
  "preco": 35.90,
  "categoria": "Pizza",
  "disponibilidade": true,
  "imagem": "https://example.com/pizza.jpg"
}
```

## Running Tests

**Backend (JUnit 5 + Mockito):** 72 tests covering service and controller layers with mocked dependencies.

```bash
docker compose exec backend mvn test
# or locally: cd Menu-Back-End && ./mvnw test
```

**Frontend (Vitest + Testing Library):** Component unit tests for `CartaoProduto` and `Carrinho`.

```bash
docker compose exec frontend npm test
# or locally: cd Menu-Front && npm test
```

## Frontend Routes

| Route          | Description                         |
|----------------|-------------------------------------|
| `/login`       | Authentication page                 |
| `/adm`         | Admin dashboard (products & orders) |
| `/mesa/:mesa`  | Customer menu for a given table     |

## Author

Eduardo Ramires — [github.com/eduardo-ramires](https://github.com/eduardo-ramires)
