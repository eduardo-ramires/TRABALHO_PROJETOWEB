# 🍔 Menu-Back-End

Este projeto é uma API REST simples desenvolvida com **Spring Boot**, **JPA** e **MySQL**, que permite o cadastro e gerenciamento de produtos para uma hamburgueria. A API oferece operações de CRUD (Create, Read, Update, Delete) e está pronta para integração com um frontend.

---

## 🔧 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok (opcional)
- Maven

---

## 🛠️ Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/Herysson/Menu-Back-End.git
cd Menu-Back-End
````

### 2. Configure o `application.properties`

Atualize o arquivo `src/main/resources/application.properties` com as credenciais do seu banco MySQL:

```properties
# Configuração do banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/menu?createDatabaseIfNotExist=true
spring.datasource.username=SEU_USUÁRIO
spring.datasource.password=SUA_SENHA

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🚀 Executando o Projeto

```bash
./mvnw spring-boot:run
```

A API estará disponível em:
[http://localhost:8080/produtos](http://localhost:8080/produtos)

---

## 📌 Endpoints da API

| Método | Endpoint         | Descrição                     |
| ------ | ---------------- | ----------------------------- |
| GET    | `/produtos`      | Lista todos os produtos       |
| GET    | `/produtos/{id}` | Busca um produto por ID       |
| POST   | `/produtos`      | Cria um novo produto          |
| PUT    | `/produtos/{id}` | Atualiza um produto existente |
| DELETE | `/produtos/{id}` | Remove um produto pelo ID     |

---

## 🧪 Exemplo de Requisição `POST`

```http
POST /produtos
Content-Type: application/json

{
  "nome": "Clássico Bacon",
  "descricao": "Pão brioche, hambúrguer 180g, queijo cheddar, bacon crocante e maionese especial.",
  "preco": 29.9,
  "categoria": "Hambúrguer",
  "disponibilidade": true,
  "imagem": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNMEyVsK2Sb0hq8ZJDPBRQkKzwZ2lICuPAqw&s"
}
```

---

## 🍽️ Populando o Banco de Dados

Você pode popular o banco com os seguintes comandos SQL:

```sql
INSERT INTO produto (id, nome, descricao, preco, categoria, disponibilidade, imagem) VALUES
(1, 'Clássico Bacon', 'Pão brioche, hambúrguer 180g, queijo cheddar, bacon crocante e maionese especial.', 29.9, 'Hambúrguer', true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNMEyVsK2Sb0hq8ZJDPBRQkKzwZ2lICuPAqw&s'),
(2, 'Cheddar Duplo', 'Dois hambúrgueres 150g, cheddar derretido e cebola caramelizada.', 34.5, 'Hambúrguer', true, 'https://api-middleware-mcd.mcdonaldscupones.com/media/image/product$kzXWKJ6A/200/200/original?country=br'),
(3, 'Veggie Delight', 'Hambúrguer de grão-de-bico, alface, tomate e molho vegano no pão integral.', 27.0, 'Hambúrguer', true, 'https://goldenparadisebd.com/wp-content/uploads/2024/12/Veggie-Delight-Burger.jpg'),
(4, 'Refrigerante Lata', 'Coca-Cola, Guaraná ou Fanta – 350ml.', 6.0, 'Bebida', true, 'https://zaffari.vtexassets.com/arquivos/ids/276576/1007841-00.jpg?v=638802406334870000'),
(5, 'Suco Natural', 'Suco natural de laranja ou limão, feito na hora – 300ml.', 8.0, 'Bebida', true, 'https://cantinagoodlanche.com.br/wp-content/uploads/2020/07/beneficios-dos-sucos-naturais-1-alfa-hotel.jpg'),
(6, 'Água Mineral', 'Garrafa 500ml, com ou sem gás.', 4.0, 'Bebida', true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmE-kl1CimSZ8Z6wd2ff0GYQJVXgHAntYFbg&s'),
(7, 'Brownie com Sorvete', 'Brownie de chocolate com bola de sorvete de creme e calda de chocolate.', 14.9, 'Sobremesa', true, 'https://www.specialita.com/wp-content/uploads/2022/07/brownie-de-chocoavela-com-sorvete.jpg'),
(8, 'Milkshake Morango', 'Milkshake cremoso de morango com chantilly.', 12.0, 'Sobremesa', true, 'https://img.cdndsgni.com/preview/10101275.jpg'),
(9, 'Petit Gateau', 'Bolo quente com recheio de chocolate e sorvete de creme.', 15.9, 'Sobremesa', true, 'https://receitatodahora.com.br/wp-content/uploads/2022/02/petit-gateau-scaled.jpg');
```

---

## 👨‍💻 Autor

Desenvolvido por [Herysson Figueiredo](https://github.com/Herysson)

---

## 📜 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).




