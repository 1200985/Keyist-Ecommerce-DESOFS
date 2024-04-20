## Abuse Cases for UC1 (Register a new account)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC1_1 | FEATURE_UC1         | SQL injection during account registration                                               | A1:2017-Injection           | HIGH        | Technical          | Validate input data                               | To Address        |
| ABUSE_CASE_UC1_2 | FEATURE_UC1         | Bypass input validation for malicious email or password during registration             | A7:2017-Cross-Site Scripting (XSS) | HIGH   | Technical          | Implement input sanitization                       | To Address        |

## Abuse Cases for UC2 (Sign In to an existent account)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC2_1 | FEATURE_UC2         | Brute force attack to guess valid credentials during sign-in                            | A2:2017-Broken Authentication | MEDIUM    | Technical          | Implement account lockout after multiple failed attempts | To Address        |
| ABUSE_CASE_UC2_2 | FEATURE_UC2         | Credential stuffing attack using leaked username and password combinations            | A2:2017-Broken Authentication | HIGH      | Technical          | Implement multi-factor authentication               | To Address        |

## Abuse Cases for UC3 (Change password)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC3_1 | FEATURE_UC3         | Exploit insufficient access controls to change another user's password                   | A5:2017-Broken Access Control | HIGH     | Technical          | Implement proper access controls and validation checks | To Address        |
| ABUSE_CASE_UC3_2 | FEATURE_UC3         | Manipulate password reset tokens or URLs to change a user's password                    | A5:2017-Broken Access Control | MEDIUM    | Technical          | Implement secure password reset mechanisms         | To Address        |

## Abuse Cases for UC4 (View and edit profile)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC4_1 | FEATURE_UC4         | View or edit another user's profile by manipulating URLs or session tokens               | A5:2017-Broken Access Control | HIGH     | Technical          | Implement proper session management                | To Address        |
| ABUSE_CASE_UC4_2 | FEATURE_UC4         | XSS attack by injecting malicious scripts into profile fields                            | A7:2017-Cross-Site Scripting (XSS) | HIGH    | Technical          | Implement input validation and output encoding      | To Address        |

## Abuse Cases for UC5 (List of available Products)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC5_1 | FEATURE_UC5         | View non-activated Products by bypassing access controls                                | A5:2017-Broken Access Control | MEDIUM    | Technical          | Implement proper access controls and data validation | To Address        |
| ABUSE_CASE_UC5_2 | FEATURE_UC5         | Injection attack by manipulating product search parameters                              | A1:2017-Injection           | HIGH        | Technical          | Validate input data                               | To Address        |

## Abuse Case for UC6 (Search for Products by name)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC6   | FEATURE_UC6         | Perform a SQL injection or XSS attack by searching with malicious input                  | A1:2017-Injection, A7:2017-Cross-Site Scripting (XSS) | HIGH | Technical          | Validate input data and implement proper output encoding | To Address        |

## Abuse Case for UC7 (Filter Products)

| Abuse Case ID    | Feature ID impacted | Attack Description                                                                      | Attack referential ID       | risk rating | Kind of abuse case | Countermeasure                                    | Handling decision |
|------------------|---------------------|----------------------------------------------------------------------------------------|-----------------------------|-------------|--------------------|---------------------------------------------------|-------------------|
| ABUSE_CASE_UC7   | FEATURE_UC7         | Bypass filters by manipulating request parameters or exploiting insecure configurations | A5:2017-Broken Access Control, A6:2017-Security Misconfiguration | MEDIUM | Technical          | Implement proper input validation and secure configurations | To Address        |


 
