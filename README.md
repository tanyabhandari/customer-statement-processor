# Customer Statement Processor #

### What is this repository for? ###

* Quick summary

  Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated. based on below conditions
  
     * all transaction references should be unique
     * end balance needs to be validated 

* Version 1.0


**1. Clone the repository** 

```bash
https://github.com/tanyabhandari/customer-statement-processor.git
```

**2. Run the app using maven**

```bash
cd customer-statement-processor
mvn spring-boot:run
```

The application can be accessed at `http://localhost:8080`.

That's it! The application can be accessed at `http://localhost:8080/swagger-ui.html`.

### Credits ###

* Tanya Bhandari
* tanyabhandari120@gmail.com