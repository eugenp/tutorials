# Spring Cloud Archaius

#### Basic Config
This service has the basic, out-of-the-box Spring Cloud Netflix Archaius configuration.

#### Extra Configs
This service customizes some properties supported by Archaius.

These properties are set up on the main method, since Archaius uses System properties, but they could be added as command line arguments when launching the app.

#### Additional Sources
In this service we create a new AbstractConfiguration bean, setting up a new Configuration Properties source.

These properties have precedence over all the other properties in the Archaius Composite Configuration.