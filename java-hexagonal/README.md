## Hexagonal Architecture Example Using Java

### Introduction
This contains code which shows the practical example of how to build java application using
Hexagonal Architecture.

To improve visibility, we’ve combined various layers in a single module and used the layer name as the package names. 
In a real-world scenario, each layer may be represented using individual modules.

### How to Run
This spring boot maven web project can be run by executing ``mvn springboot:run`` command in your terminal 
from your project root folder. To use database based data store to fetch data then pass ``-Pdb`` along with
the command by default the data will be fetched from file based data store (``file`` is the default maven profile).

### How to Check
Once the above command is executed you can use either of the below two two approaches.
Open a web browser window and enter ``http://localhost:8080/web/users``
or
Open the terminal window and type ``curl localhost:8080/rest/users``
 



