# test_task_comparus

# Build and start application
1. Via Docker
   * Open folder with docker-compose file and run command ```docker compose up --build```.
     After build app will be able at porn localhost:8080, and dbs(postgres on 5432 and mariadb on porn 3306)
2. Via Intelij IDEA
    * must be installed Java 21
    * started dbs(postgres on 5432 and mariadb on porn 3306)
    * and change application yaml
   instead this
```
      url: ${APP_DS_POSTGRES_URL:jdbc:postgresql://localhost:5432/comparus}
      username: ${APP_DS_POSTGRES_USER:appuser}
      password: ${APP_DS_POSTGRES_PASS:apppass}
```
paste this 
```
      url: jdbc:postgresql://localhost:5432/comparus
      username: postgres
      password: 1234qwer
```
and intead this
```
      url: ${APP_DS_MARIADB_URL:jdbc:mariadb://localhost:3306/comparus}
      username: ${APP_DS_MARIADB_USER:admin}
      password: ${APP_DS_MARIADB_PASS:admin123}
```
paste this
```
      url: jdbc:mariadb://localhost:3306/comparus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      username: admin
      password: admin123
```

also must be created users and db for each db
* For postgres
```
CREATE USER admin WITH PASSWORD 'admin';

CREATE DATABASE comparus OWNER admin;
```
* MariaDB
```
CREATE DATABASE IF NOT EXISTS comparus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin123';

GRANT ALL PRIVILEGES ON comparus.* TO 'admin'@'%';

FLUSH PRIVILEGES;

```

# Remark 
In TestTaskComparusApplication before SpringApplication.run(TestTaskComparusApplication.class, args) is placed line of code for resolve bug with Java version,
with TimeZone for Kyiv, in newest version of db is set Kyiv but in Java Kiev
