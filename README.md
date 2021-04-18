# Spring Security

This project is a practice to Spring Security configuration.

This covers the following features:
1. Basic authentication
1. Authorization
1. In Memory User Details Manager
1. JDBC User Details Manager
1. CORS
1. CSRF
1. HTTPS using a self signed certificate

# How create a self signed certificate
1. Execute the following command in the Java bin folder.  
1. Put the generated self signed certificate "springsecury.p12" in the folder "src/resources/certificates".  See the
"application.yml" to set the "ssl" configuration.

```sh
keytool -genkeypair -alias springsecureapp -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore springsecureapp.p12 -validity 3650
```

For more information visit [This page](https://www.baeldung.com/spring-boot-https-self-signed-certificate) 

## How run the application  
1. Create the following environment variables or set it in application.properties file:
    - "spring_datasource_password": the database password.
    - "application_admin_password": the encrypted administrator user password for the authentication.
    Go to this link to encrypt using BCrypt: https://bcrypt-generator.com/
    - "spring_profiles_active": the application profile, the default will be "dev". To run with SSL set to "prod".
    If run with "prod" profile set the following property. 
    - "server_ssl_key-store-password": the created key store password
    
1. Run the application

## Authentication

|Host                   | https://localhost:8443                |
|-----------------------|-------------------------------------- | 
|Type of Authentication | Basic Authentication                  |
|Username               | admin@springsecurebasic.net           |
|Password               | Decrypted administrator user password |

Example:
```
GET /user HTTP/1.1
Host: localhost:8080
Authorization: Basic ENCODE_BASE_64(USERNAME:PASSWORD)
```

Use the following link to encode base 64: https://www.base64decode.org/ 

The server response will create two cookies:

1. JSESSIONID: the user session id that keep the session authenticated.
1. XSRF-TOKEN: the XSRF or CSRF token that help to prevent the CSRF attack.


##  To Access to other endpoint
1. Add JSESSIONID and Add XSRF-TOKEN to the request cookie.

```
GET /user HTTP/1.1
Host: localhost:8080
Cookie: JSESSIONID=E03C90C0ED8299412A61AF6DE6BED060; XSRF-TOKEN=6e88df41-621d-441a-9be9-e3c2e30c3fe7
```