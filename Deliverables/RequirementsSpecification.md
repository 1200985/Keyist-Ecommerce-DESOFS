
# Functional Requirements

## User Management
- The system (**Keyist**) shall allow users to create an account, using a unique email and password.
- **Keyist** shall allow registered users to sign in with their email and password.
- Users shall be able to change their password, when successfully authenticated.
- Administrator shall be able to remove user accounts (manager or client accounts) from the system.

## Product Management
- The system shall allow Managers to add new products, given some details:
  - name
  - description
  - price
  - category
  - variants
- **Keyist** shall allow managers to edit product details.

## Product Details
- The system shall allow users to see a list of all available products.
- **Keyist** shall allow users to perform a product search by name.
- Users shall be able to filter the a product list by category, price range and color
- Users shall be able to sort a product list by price (ascending or descending order).

## Shopping Cart
- The system shall allow clients to add products to their shopping cart.
- **Keyist** shall allow client to view their shopping cart items.
- Clients shall be able to remove items from their shopping cart.
- Client shall be able to apply discount coupons/codes to their shopping cart.

## Orders
- The system shall allow clients to make the checkout of their shopping cart and proceed with the order.
- **Keyist** shall allow clients to see their order history.
- Managers shall be able to generate sales reports for a given date interval.

---

# Security Requirements

## User Authentication and Authorization
- The system shall implement authentication mechanisms, requiring users to provide valid credentials for access.
- **Keyist** shall impose minimum password requirements (minimum length, inclusion of uppercase, lowercase, digits, and special characters).
- Passwords shall be securely hashed before storage in the database to mitigate the risk of password data breaches.
- The system shall ask users to re-authenticate after a period of inactivity to prevent unauthorized access.
- **Keyist** shall implement access tokens, securely generated and managed for authenticated users, with appropriate expiration times and validation mechanisms (token-based attacks).
- Role-based access shall be ensured to restrict certain functionalities and data access based on the user's role inside the system.


## Secure Software Development Policies
- The system shall support the use of secure communication protocols (HTTPS) for all data transmission over the network, to prevent interception of sensitive data.
- **Keyist** shall validate all user inputs to ensure they meet the expected format, type, length and other constraints before processing it (preventing attacks like SQL injection or command injection).
- The system shall apply output encoding to user-generated content to mitigate the execution of injected scripts in users' browsers (XSS).
- Use of third-party libraries and dependencies shall be carefully evaluated for security risks. Only reputable and well-maintained dependencies shall be integrated into the system.
- Configuration files containing sensitive information (database credentials, API keys) shall be stored securely.

---

# User Requirements
**As a Client:**
- **UC1:** I want to register a new account, using an email and password.
- **UC2:** I want to Sign In to an existent account, using an email and password.
- **UC3:** I want to change my password.
- **UC4:** I want to be able to see a list of available Products
- **UC5:** I want to search for Product by name.
- **UC6:** I want to filter Products, by category, price range or color.
- **UC7:** I want to sort Products by price, in ascending or descending order.
- **UC8:** I want to add Product to the Shopping Cart.
  - **UC8.1:** I want to be able to add discount coupons/codes.
  - **UC8.2:** I want to checkout the Order
- **UC9:** I want to see my previous Orders.

**As a Manager:**
- **UC10:** I want to add a new Product, giving a name, description, price, category and product variants.
- **UC11:** I want to edit the details of a Product.
- **UC12:** I want to generate a Sales Report for a given date interval.

**As an Admin:**
- **UC13:** I want to remove client or manager accounts from the system.
