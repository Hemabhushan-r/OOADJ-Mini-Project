# Stock-Portfolio-OOADJ-Mini-Project

Semester 6 Object Oriented Analysis and Design using Java Mini Project.

## To Develop

1. Install Java Spring Boot from [Spring Boot Installation Guide](https://spring.io/guides/gs/spring-boot) :

* Install [Gradle](https://gradle.org/install/) by downloading the zip file and add the bin folder ``pathto\gradle-8.7-all\gradle-8.7\bin`` to PATH environment variable
* Install [Maven](https://maven.apache.org/download.cgi) by downloading the zip file and add the bin folder ``pathto\apache-maven-3.9.6\bin`` to PATH environment variable

2. Install JavaFX:

* Download [JavaFX](https://gluonhq.com/products/javafx/) with
  Version: 22   Operating System: Windows    Architecture:x64    Type:SDK
  Extract zip file and add ``pathto\javafx-sdk-22\bin`` to PATH environment variable
* Add `pathto\javafx-sdk-22\lib` to a new environment variable PATH_TO_FX or use
  ```shell
  set PATH_TO_FX="path\to\javafx-sdk-22\lib"
  ```
* In VSCode with the Repo open, once Java Project is initialized
  * Expand JAVA PROJECTS on Side Bar
  * Click  + next to Referenced Libraries and add all jar files in `pathto\javafx-sdk-22\lib` to resolve dependencies in VSCode
* Create a database named stock_portfolio_app_ooad_mp in MySQL and execute the below
  * ```shell
    mysql -u root -p stock_portfolio_app_ooad_mp < pathto\stock_portfolio_app_ooad_mp.sql
    ```
* Ensure you are running JDK 17 or later
* In case the installed version of Java is different from JDK 17 use IntelliJ for proper exectution of the backend
## To Run Frontend

Run

```shell
cd Frontend/stock-portfolio-frontend
```

```shell
mvn clean javafx:run
```

## To Run Backend

Run

```shell
cd Backend\stock-service
mvnw spring-boot:run
```

The server would run at [locahost:8081](http:\\localhost:8081)

## Contributors

<a href="https://github.com/Hemabhushan-r/OOADJ-Mini-Project/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Hemabhushan-r/OOADJ-Mini-Project" />
</a>
