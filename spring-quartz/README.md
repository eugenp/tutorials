## Spring Quartz

This module contains code about Spring with Quartz

## #Scheduling in Spring with Quartz Example Project
This is the first example where we configure a basic scheduler.

##### Spring boot application, Main class


`org.baeldung.springquartz.SpringQuartzApp`

##### Configuration in *application.properties*

  - Default: configures scheduler using Spring convenience classes:

    `using.spring.schedulerFactory=true`
   
  - To configure scheduler using Quartz API: 
  
    `using.spring.schedulerFactory=false`
