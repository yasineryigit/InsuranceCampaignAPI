# Insurance Campaign API

## About the project
This project is a proof-of-concept (POC) that allows users to create and manage campaigns that they can publish and unpublish as they wish, with various campaign-related features. Following feedback from the POC demo, we plan to further develop the project. The POC is designed to be a foundation upon which we can build additional features.

As part of the POC, users can input campaign information, including the campaign title, description, and category. The campaign lifecycle and rules are as follows:

For 'TSS', 'ÖSS', and 'Other' categories, campaigns are set to "Pending Approval" status upon initial submission. For other categories, campaigns are set to "Active." In other words, campaigns in these categories must be approved before they can be published.
When a campaign is submitted with the same category, title, and description as an existing campaign, it is flagged as a repetitive and cannot be updated.
A campaign that is "Pending Approval" will be set to "Active" once approved. 
Users can deactivate "Active" or "Pending Approval" campaigns.
For this project,  JWT authentication and authorization implemented. As a result, only authenticated and authorized users will be able to submit, update, and deactivate campaigns.







## Installation

#### Getting Started
Make sure port 8086,5432 are not in use
#### Installing & Running Application

```bash
mvn clean install
docker-compose -f common.yml -f docker-compose.yml up
```

#### You can see the api documentation on the swagger page after you run the application. Swagger will be available on;

```bash
http://localhost:8086/api/1.0/swagger-ui.html
```

### Basic use cases
#### 1) Signup a new user
#### 2) Login with the created user to get authentication token
#### 3) Create a campaign
#### 4) Update a campaign
#### 5) View statistics

## Endpoints
### Auth:

| Request | Access Point | Explanation                |
| :-------- | :------- | :------------------------- |
| `POST` | `/api/1.0/auth/login` | Authenticate user and return AuthResponse object |
| `POST` | `/api/1.0/auth/refresh-token` | Refresh expired JWT token with valid refresh token |

### Campaign:

| Request | Access Point | Explanation                |
| :-------- | :------- | :------------------------- |
| `POST` | `/api/1.0/campaigns/create` | Create a new campaign |
| `PUT` | `/api/1.0/campaigns/deactivate-campaign-status` | Deactivate campaign status by campaign ID |
| `PUT` | `/api/1.0/campaigns/update-campaign-by-admin` | Update campaign by admin and return CampaignResponse object |
| `PUT` | `/api/1.0/campaigns/update-campaign-by-company` | Update campaign by company |
| `PUT` | `/api/1.0/campaigns/update-campaign-status` | Update campaign status by campaign ID and status |
| `PUT` | `/api/1.0/campaigns/update-multiple-campaign-status` | Update status of multiple campaigns and return list of CampaignResponse objects |

### Dashboard:

| Request | Access Point | Explanation                |
| :-------- | :------- | :------------------------- |
| `GET` | `/api/1.0/dashboard/classifieds/statistics` | Get statistics of classifieds |




#### Considerations During the Writing of the Project

 - Implemented the code to be clean, understandable, and easy to read while following the layered architecture.
 - Designed the project according to monolithic architecture.
 - Utilized Exception Handling throughout the codebase.
 - Followed programming principles, such as SOLID.

#### Future Work
- Implement a CI/CD pipeline to enable continuous integration and deployment.
- Implement a suitable Frontend to enhance the user experience.


