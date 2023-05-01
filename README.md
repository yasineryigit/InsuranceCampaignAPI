# Insurance Campaign API

## About the project
This project is a proof-of-concept (POC) that allows users to create and manage campaigns that they can publish and unpublish as they wish, with various campaign-related features. Following feedback from the POC demo, we plan to further develop the project. The POC is designed to be a foundation upon which we can build additional features.

As part of the POC, users can input campaign information, including the campaign title, description, and category. The campaign lifecycle and rules are as follows:

For 'Complementary Health Insurance', 'Private Health Insurance', and 'Other' categories, campaigns are set to "Pending Approval" status upon initial submission. For 'Life Insurance' category, campaigns are set to "Active." In other words, campaigns in some categories must be approved before they can be published.
When a campaign is submitted with the same category, title, and description as an existing campaign, it is flagged as a repetitive and cannot be updated.
A campaign that is "Pending Approval" will be set to "Active" once approved. 
Users can deactivate "Active" or "Pending Approval" campaigns.
For this project,  JWT authentication and authorization implemented. As a result, only authenticated and authorized users will be able to submit, update, and deactivate campaigns.

### Tech
- Spring Boot
- Spring Security 
- JWT Authentication/Authorization
- Docker
- Validation
- Custom Error Handler (Multi-Language Messages)
- Lombok
- SwaggerUI
- PostgreSQL
- Timeout logging


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

```bash
'campaignCategoryId' example values:

1: Complementary Health Insurance
2: Private Health Insurance
3: Life Insurance
4: Others
```

### Basic use cases
#### 1) Signup a new user
#### 2) Login with the created user to get authentication token
#### 3) Create a campaign
#### 4) Update a campaign
#### 5) View statistics

## Endpoints
### Auth:

| Request | Access Point | Explanation                | User Role |
| :-------- | :------- | :------------------------- |:----------|
| `POST` | `/api/1.0/auth/login` | Authenticate user and return AuthResponse object | All |
| `POST` | `/api/1.0/auth/refresh-token` | Refresh expired JWT token with valid refresh token | All |

### User:

| Request | Access Point | Explanation                | User Role |
| :-------- | :------- | :------------------------- |:-----------|
| `POST` | `/api/1.0/users/create` | Create a new user and return UserResponse object | All |
| `PUT` | `/api/1.0/users/update-user-enabled` | Update user's enabled status | Admin |


### Campaign:

| Request | Access Point | Explanation                | User Role   |
| :-------- | :------- | :------------------------- |:--------------|
| `POST` | `/api/1.0/campaigns/create` | Create a new campaign | Admin, Company |
| `PUT` | `/api/1.0/campaigns/deactivate-campaign-status` | Deactivate campaign status by campaign ID | Admin, Company |
| `PUT` | `/api/1.0/campaigns/update-campaign-by-admin` | Update campaign by admin and return CampaignResponse object | Admin |
| `PUT` | `/api/1.0/campaigns/update-campaign-by-company` | Update campaign by company | Admin, Company |
| `PUT` | `/api/1.0/campaigns/update-campaign-status` | Update campaign status by campaign ID and status | Admin |
| `PUT` | `/api/1.0/campaigns/update-multiple-campaign-status` | Update status of multiple campaigns and return list of CampaignResponse objects | Admin |

### Dashboard:

| Request | Access Point | Explanation                | User Role   |
| :-------- | :------- | :------------------------- |:--------------|
| `GET` | `/api/1.0/dashboard/classifieds/statistics` | Get statistics of classifieds | Admin |




## Screenshoots

#### Swagger UI

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/SwaggerUI.png" width="800" height="400" />

#### Create Company User

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/UsersCreate.png" width="800" height="400" />

#### Login as Company

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/AuthLogin.png" width="800" height="400" />

#### Refresh Token

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/AuthRefreshToken.png" width="800" height="400" />

#### Update User Enabled

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/UsersUpdateUserEnabled.png" width="800" height="400" />

#### Create Campaign

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsCreate.png" width="800" height="400" />

#### Update Campaign By Admin

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsUpdateCampaignByAdmin.png" width="800" height="400" />

#### Update Campaign By Company

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsUpdateCampaignByCompany.png" width="800" height="400" />

#### Update Campaign By Company

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsUpdateCampaignByCompany.png" width="800" height="400" />

#### Update Campaign Status

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsUpdateCampaignStatus.png" width="800" height="400" />

#### Update Multiple Campaign Status

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsUpdateMultipleCampaignStatus.png" width="800" height="400" />

#### Deactivate Campaign Status

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/CampaignsDeactivateCampaignStatus.png" width="800" height="400" />

#### Statistics

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/Statistics.png" width="800" height="400" />

### Timeout Logging

<img src="https://raw.githubusercontent.com/yasineryigit/InsuranceCampaignAPI/master/docs/screenshoots/Timeout.png" width="800" height="24" />


#### Considerations During the Writing of the Project

 - Implemented the code to be clean, understandable, and easy to read while following the layered architecture.
 - Designed the project according to monolithic architecture.
 - Utilized Exception Handling throughout the codebase.
 - Followed programming principles, such as SOLID.

#### Future Work
- Implement a CI/CD pipeline to enable continuous integration and deployment.
- Implement a suitable Frontend to enhance the user experience.


















