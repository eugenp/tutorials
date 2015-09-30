=========================================================================

## Scheduling in Spring with Quartz Example Project
This is the first example where we configure a basic scheduler.
##### Spring boot application, Main class
###
```
SpringQuartzBasicsApp
```
######

##### Configuration in *application.properties*
####

  - Default: configures scheduler using Spring convenience classes:
    ```
    using.spring.schedulerFactory=true
    ```    
  - To configure scheduler using Quartz API: 
    ```
    using.spring.schedulerFactory=false
    ```