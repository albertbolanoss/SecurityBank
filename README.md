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

#How create a self signed certificate
1. Execute the following command in the Java bin folder.  
1. Put the generated self signed certificate "springsecury.p12" in the folder "src/resources/certificates".  See the
"application.yml" to set the "ssl" configuration.

```sh
keytool.exe -genkey -alias springsecurity -storetype PKCS12 -keyalg RSA -keysize 2048 springsecury.p12 -validity 3650
```

## How run the application  
1. Create the following environment variables or set it in application.properties file:
    - "server_ssl_key-store-password": the key store password
    - "spring_datasource_password": the database password.
    - "default_admin_password": the administrator password to login to the application. 
1. Run the application

## Authentication

|Host                   | https://localhost:8443     |
|-----------------------|----------------------------| 
|Type of Authentication | Basic                      |
|Username               | admin@springsecurebasic.net|
|Password               | "default_admin_password".  |
 
The server response will create two cookies:
1. JSESSIONID: the user session id that keep the session authenticated.
1. XSRF-TOKEN: the XSRF or CSRF token that help to prevent the CSRF attack.


##  To Access to other endpoint
1. Add JSESSIONID to the request cookie.
1. Add XSRF-TOKEN to the header or as request parameter. 