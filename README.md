# A lost & found application

“Lost & Found” application using Java and Springboot.

## Requirement

### Functional Requirements
    
- Upload & Store Data: 

A REST API Admin endpoint to upload the lost items with
details from a file. The application should extract and store the following information
from the uploaded file: LostItem: ItemName, Quantity, Place:

- Read LostItems: 

A REST API user endpoint to read the saved Lost Items

- Claim LostItem Data and save: 

A REST API user endpoint for users to claim the lost item.
For eg: User 1001 claimed certain items and certain quantities from the retreived list (2) and
store them with their user id.
Note: One LostItem can be claimed by more than one user.

- Retrieved LostItems claimed by people: 

A REST API Admin endpoint to read all the Lost items and Users (userId and name) associated with that.

- Retrieve user information from User Service: 

This will be another service (a mock service) to get user details like Name of the user. Consider writing least code as this is only a mock.

## TL;DR - Build and Run
 
	 mvn clean install
	 
#### Technical landscape.

My Goal of with this assignment was to keep it simple yet effective, bug-proof, user-friendly, maintainable and readable. To achieve this I have tried to use as many latest features as possible. I tried not to go beyond what is asked for as assignment (business requirement). To make the code and application more reliable, I have achieved 86.2% code coverage. I used SonarQube to scan the code smell and solved all major bugs and issues. I also avoided importing unused libraries to keep the jar light-weighted. 

## Code Quality Factors that are taken care:

- Total 86.2% code coverage by Junit
- Using SonarLint all "Major" issues are resolved. 	 
- No external library from untrusted source dependency is used.
- Code smell is as low as possible.

## Flowchart

![path to flowchart](https://github.com/rupesh42/lost-found/blob/main/src/main/resources/Lost-Found.jpg)

## SWAGGER URL:
	http://localhost:8081/swagger-ui/index.html
	
## Next Steps / Improvement areas:
##### 1. Java 23 can be used.
##### 2. Security can be upgraded to JWT token based.