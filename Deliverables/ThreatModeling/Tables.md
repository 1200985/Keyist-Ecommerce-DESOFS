# Tables

## External Dependencies

### Non-applicable. The system is not deployed to any external entity

| ID  | Description |
| --- | ----------- |

## Trust Levels

| ID  | Name             | Description                                                                                        |
| --- | ---------------- | -------------------------------------------------------------------------------------------------- |
| 01  | Anonymous Client | A user who has entered the website but has not created an account.                                 |
| 02  | Logged-in Client | A user who has provided valid login credentials. Can access previous Orders.                       |
| 03  | Manager          | A user who is able to manage products, by editing them, adding new ones or removing existent ones. |
| 04  | Administrator    | A user who has full access to the system, being able to add and remove system accounts.            |

## Assets

| ID  | Name                       | Description                                                                                                                               | Trust Level                              |
| --- | -------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------- |
| 01  | User Login Credentials     | Credentials used by users to log in into the system. Critical for preventing unauthorized access.                                         | Logged-in Client, Manager, Administrator |
| 02  | Email Communication        | Emails sent to the users on the moment of account creation or when they change passwords. Needs protection to prevent phishing attacks.   | Logged-in Client, Manager                |
| 03  | User Personal Information  | Personal information of users, which needs protection to prevent identity theft.                                                          | Logged-in Client, Manager, Administrator |
| 04  | Order Checkout Information | Sensitive checkout details including order pricing and payment info. Protection needed to prevent fraud and ensure transaction integrity. | Logged-in Client                         |

## Entry points

| ID  | Name                         | Description                                                                                                                                 | Trust Levels |
| --- | ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| 1   | HTTP Port                    | The website for the store will only be accessible through HTTP                                                                              | 1, 2, 3, 4   |
| 2   | Login Page                   | The website requires a login in order to order items and carry out most operations at the exception of only browsing the items on the store | 1, 2, 3, 4   |
| 3   | Discount Code                | The website offers a discount code system with user input                                                                                   | 2            |
| 4   | Forgot password              | The website allows users to change password without logging in, through their email                                                         | 1            |
| 5   | Billing and Shipping address | The website requires the users to insert their billing and shipping address to receive the orders                                           | 2            |

## Exit points

| ID  | Name                  | Description                                                                                                                                        | Trust Levels |
| --- | --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| 1   | Order History         | The order history page provides users with a record of their past orders                                                                           | 2            |
| 2   | User account settings | Users utilize the account settings interface to manage their account details and preferences                                                       | 2, 3, 4      |
| 3   | User login            | Login page might be vulnerable to attacks like cross-site scripting (XSS) where attackers could exploit it to steal session cookies or credentials | 1, 2, 3, 4   |
