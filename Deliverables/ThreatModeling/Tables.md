## External Dependencies

### Non-applicable. The system is not deployed to any external entity.
| ID | Description                                           |
|----|-------------------------------------------------------|

## Trust Levels

| ID | Name             | Description                                                                                        |
|----|------------------|----------------------------------------------------------------------------------------------------|
| 01 | Anonymous Client | A user who has entered the website but has not created an account.                                 |
| 02 | Logged-in Client | A user who has provided valid login credentials. Can access previous Orders.                       |
| 03 | Manager          | A user who is able to manage products, by editing them, adding new ones or removing existent ones. |
| 04 | Administrator    | A user who has full access to the system, being able to add and remove system accounts.            |


## Assets

| ID | Name                       | Description                                                                                                                               | Trust Level                              |
|----|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------|
| 01 | User Login Credentials     | Credentials used by users to log in into the system. Critical for preventing unauthorized access.                                         | Logged-in Client, Manager, Administrator |
| 02 | Email Communication        | Emails sent to the users on the moment of account creation or when they change passwords. Needs protection to prevent phishing attacks.   | Logged-in Client, Manager                |
| 03 | User Personal Information  | Personal information of users, which needs protection to prevent identity theft.                                                          | Logged-in Client, Manager, Administrator |
| 04 | Order Checkout Information | Sensitive checkout details including order pricing and payment info. Protection needed to prevent fraud and ensure transaction integrity. | Logged-in Client                         |
