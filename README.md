# Cardápio Digital

Sistema de cardápio digital para restaurantes, com painel administrativo e menu interativo por mesa.

## Visão Geral

A aplicação permite que clientes acessem o cardápio pelo número da mesa e realizem pedidos, enquanto administradores gerenciam produtos, usuários e pedidos por um painel dedicado.

## Tecnologias

**Back-end**
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- MySQL
- Lombok

**Front-end**
- React 19 + TypeScript
- Vite
- React Router DOM v7
- TanStack React Query
- Axios

## Estrutura do Projeto

```
TRABALHO_PROJETOWEB/
├── Menu-Back-End/   # API REST (Spring Boot)
└── Menu-Front/      # Interface web (React + Vite)
```

## Pré-requisitos

- Java 17+
- Maven
- Node.js 18+
- MySQL 8+

## Configuração e Execução

### Banco de Dados

É necessário ter um servidor MySQL rodando localmente. O banco `menu` e as tabelas são criados automaticamente na primeira execução (`createDatabaseIfNotExist=true` + `ddl-auto=update`), não sendo necessário criá-los manualmente.

As credenciais ficam em `Menu-Back-End/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/menu?createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

Por padrão o projeto usa o usuário `root` sem senha. Ajuste `spring.datasource.username` e `spring.datasource.password` conforme as credenciais do seu MySQL local.

### Back-end

```bash
cd Menu-Back-End
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

### Front-end

```bash
cd Menu-Front
npm install
npm run dev
```

A aplicação ficará disponível em `http://localhost:5173`.

## Endpoints da API

| Método | Rota             | Descrição                  |
|--------|------------------|----------------------------|
| GET    | /produtos        | Lista todos os produtos    |
| POST   | /produtos        | Cria um produto            |
| GET    | /produtos/{id}   | Busca produto por ID       |
| PUT    | /produtos/{id}   | Atualiza produto           |
| DELETE | /produtos/{id}   | Remove produto             |
| GET    | /usuarios        | Lista usuários             |
| POST   | /usuarios        | Cria usuário               |
| GET    | /pedidos         | Lista pedidos              |
| POST   | /pedidos         | Cria pedido                |

## Rotas do Front-end

| Rota          | Descrição                              |
|---------------|----------------------------------------|
| `/login`      | Tela de login do administrador         |
| `/adm`        | Painel administrativo                  |
| `/mesa/:mesa` | Cardápio interativo para a mesa        |

## Funcionalidades

- Login de administrador
- Gerenciamento de produtos (nome, descrição, preço, categoria, disponibilidade, imagem)
- Gerenciamento de usuários
- Cardápio por mesa com carrinho de compras
- Realização e acompanhamento de pedidos
