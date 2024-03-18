# Food Delivery Service implemented using Conductor
Sample Java Application demonstrating Saga microservice architecture pattern for a food delivery app.

## Running this Example

### Environment setup
1. Install JAVA 17 - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
2. Install Gradle - https://gradle.org/install/
3. Generate Gradle Wrapper - https://www.baeldung.com/gradle-wrapper
4. Install sqlite (Only if necessary, i.e if SQLite JDBC Driver is not installed. If you are on Mac OS X or Linux, then SQLite is normally pre-installed) - https://www.tutorialspoint.com/sqlite/sqlite_installation.htm.

### Workflow Setup on Orkes Playground

#### Use existing out-of-the-box workflows
We have already setup these workflows with all the necessary permissions. These can be used directly, if you don't need to modify these workflows.
1. Food Delivery Workflow - https://play.orkes.io/workflowDef/FoodDeliveryWorkflow
2. Cancellation workflow - https://play.orkes.io/workflowDef/FoodDeliveryFailureWorkflow

### Running the application and workers locally

1. Clone the project to your local
2. Update following properties in [application.properties](src/main/resources/application.properties)   
   1. `conductor.security.client.key-id` and `conductor.security.client.secret` are **NOT** set, please set them
      * When connecting to playground - refer to this [article](https://orkes.io/content/how-to-videos/access-key-and-secret) to get a key and secret
      * When connecting locally - follow the instructions [here (Install and Run Locally)](https://orkes.io/content/get-orkes-conductor)
   2. `conductor.worker.all.domain` is set to 'saga' by default, please change it to <yourname> or something else to avoid conflicts with workflows and workers spun up by others
3. From the root project folder, run `gradlew bootRun`

### Order Creation

We can use two approaches:
1. Call the triggerRideBookingFlow API from within the Application
   ```
    curl --location 'http://localhost:8081/triggerFoodDeliveryFlow' \
    --header 'Content-Type: application/json' \
    --data '{
        "customerEmail": "tester.qa@example.com",
        "customerName": "Tester QA",
        "customerContact": "+1(605)123-5674",
        "address": "350 East 62nd Street, NY 10065",
        "restaurantId": 2,
        "foodItems": [
            {
                "item": "Chicken with Broccoli",
                "quantity": 1
            },
            {
                "item": "Veggie Fried Rice",
                "quantity": 1
            },
            {
                "item": "Egg Drop Soup",
                "quantity": 2
            }
        ],
        "additionalNotes": [
            "Do not put spice.",
            "Send cutlery."
        ],
        "paymentMethod" : {
            "type": "Credit Card",
            "details": {
                "number": "1234 4567 3325 1345",
                "cvv": "123",
                "expiry": "05/2022"
            }
        },
        "paymentAmount": 45.34,
        "deliveryInstructions": "Leave at the door!"
     }'
   ```
2. Directly call the Orkes API for creating a workflow
   1. Generate a JWT token by following steps mentioned [here](https://orkes.io/content/access-control-and-security/applications#generating-token)
   2. Make an HTTPS request from postman/curl similar to below after replacing \<JWT Token\>:
``` 
curl --location 'https://play.orkes.io/api/workflow' \
--header "Content-Type: application/json" \
--header 'X-Authorization: <JWT Token>' \
--request POST \
--data \
'
{
    "name": "FoodDeliveryWorkflow",
    "version": 1,
    "input": {
        "customerEmail": "tester.qa@example.com",
        "customerName": "Tester QA",
        "customerContact": "+1(605)123-5674",
        "address": "350 East 62nd Street, NY 10065",
        "restaurantId": 2,
        "foodItems": [
        {
          "item": "Chicken with Broccoli",
          "quantity": 1 
        },
        {
          "item": "Veggie Fried Rice",
          "quantity": 1 
        }, 
        {
          "item": "Egg Drop Soup",
          "quantity": 2
        }],
        "additionalNotes": [
            "Do not put spice.",
            "Send cutlery."
        ],
        "deliveryInstructions": "Leave at the door!",
        "paymentAmount": 45.34,
        "paymentMethod": {
            "type" : "Credit Card",
            "details" : {
                "number": "1234 4567 3325 1345",
                "expiry": "05/2025",
                "cvv": "123"
            }
        }
    }
    "taskToDomain": {
        "*": "saga"
    }
}
'
```
   
A successful order creation workflow run will look like this:

![Screenshot 2024-02-26 at 11 38 14 AM](https://github.com/conductor-sdk/conductor-examples-food-delivery/assets/127052609/377549ec-58b0-425c-922b-6318a20b68c8)


#### Triggering the cancellation workflow to simulate rollback of distributed transactions

* Create an order with invalid payment expiry
``` 
curl --location 'https://play.orkes.io/api/workflow' \
--header "Content-Type: application/json" \
--header 'X-Authorization: <JWT Token>' \
--request POST \
--data \
'
{
    "name": "FoodDeliveryWorkflow",
    "version": 1,
    "input": {
        "customerEmail": "tester.qa@example.com",
        "customerName": "Tester QA",
        "customerContact": "+1(605)123-5674",
        "address": "350 East 62nd Street, NY 10065",
        "restaurantId": 2,
        "foodItems": [
        {
          "item": "Chicken with Broccoli",
          "quantity": 1 
        },
        {
          "item": "Veggie Fried Rice",
          "quantity": 1 
        }, 
        {
          "item": "Egg Drop Soup",
          "quantity": 2
        }],
        "additionalNotes": [
            "Do not put spice.",
            "Send cutlery."
        ],
        "deliveryInstructions": "Leave at the door!",
        "paymentAmount": 45.34,
        "paymentMethod": {
            "type" : "Credit Card",
            "details" : {
                "number": "1234 4567 3325 1345",
                "expiry": "05/2022",
                "cvv": "123"
            }
        }
    }
    "taskToDomain": {
        "*": "saga"
    }
}
'
```

* This will cause the workflow to fail and trigger the cancellation workflow.
* Failed workflow run will look like this:

![Screenshot 2024-02-26 at 1 14 02 PM](https://github.com/conductor-sdk/conductor-examples-food-delivery/assets/127052609/6078320f-f4d6-406e-98b9-7df110c70cb3)



* A cancellation workflow run will look like this:

![Screenshot 2024-02-26 at 1 15 07 PM](https://github.com/conductor-sdk/conductor-examples-food-delivery/assets/127052609/68c15035-82c1-4415-b502-93217ce40c80)


* In the above workflow diagram, the simulated distributed rollback can be seen. The rollback sequence in case of failure occurring while payment processing is as follows:
  1. Shipment is cancelled in the Shipment Service
  2. Payment is cancelled in the Payment Service
  3. Order is cancelled in the Order Service
