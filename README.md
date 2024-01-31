# i18n

> The goal of this project is to provide examples of how we can integrate i18n with Vaadin Flow quickly and easily,
> without any additional configuration.

> In this demonstration, you can observe how we can switch between 4 languages: **English**, **Spanish**, **Finnish**, and **French**.
> The translations to Finnish and French have been made possible thanks to ChatGPT. This project illustrates the ease with
> which we can integrate multiple languages using Vaadin Flow, highlighting the functionality of dynamic language
> switching without the need for additional configurations.

## Running the application

The project is a standard Maven project. To run it from the command line, tab mvnw (Windows), or ./mvnw (Mac & Linux),
then open http://localhost:37186 in your browser.

> The 'defaultGoal' is configured in the pom file.

```shell script
# Mac & Linux
./mvnw
```

```shell script
# Windows
  mvnw
```

## Deploying to Production

To create a production build, call

```shell script
# Mac & Linux
./mvnw clean package -Pproduction
``` 

To create a production build, call

```shell script
# Windows
mvnw clean package -Pproduction
``` 

This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using

```shell script
java -jar target/i18n.jar 
```

## Deploying using Docker

To build the Dockerized version of the project, run

```shell script
# Mac & Linux
./mvnw clean package -Pproduction
``` 

```shell script
# Windows
mvnw clean package -Pproduction
``` 

```shell script
docker build . -t vaadin-i18n-app:latest
```

Once the Docker image is correctly built, you can test it locally using

```shell script
docker run -p 37186:37186 vaadin-i18n-app:latest
```

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Useful links

- Read i18n [documentation](https://vaadin.com/docs/latest/advanced/i18n-localization#provider-sample-for-translation)
- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorial at [vaadin.com/docs/latest/tutorial/overview](https://vaadin.com/docs/latest/tutorial/overview).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples
  at [vaadin.com/docs/latest/components](https://vaadin.com/docs/latest/components).
- View use case applications that demonstrate Vaadin capabilities
  at [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Build any UI without custom CSS by discovering Vaadin's set
  of [CSS utility classes](https://vaadin.com/docs/styling/lumo/utility-classes).
- Find a collection of solutions to common use cases at [cookbook.vaadin.com](https://cookbook.vaadin.com/).
- Find add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join
  our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin).
