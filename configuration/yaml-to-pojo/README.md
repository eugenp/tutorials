This is a demo application for using YAML configuration for defining values in a POJO class. 

The application has an endpoint to provide T-shirt size conversion for label and countrycode.

If you run this service locally you can call this endpoint on:

`localhost:8080/convertSize?label=M&countryCode=fr`

It should return the size as int. 