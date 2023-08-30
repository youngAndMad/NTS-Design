# NTS Design Java Backend Technical task

## Used technologies
* Spring boot, WebSocket, Spring Data JPA
* PostgreSQl database
* HTML
* CSS
* AmazonS3
* JavaScript
* JUnit
* IntegrationTests
* TestContainers

* ## Requirements -> task.txt  


* ## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/youngAndMad/NTS-Design
```

**2. If you want to use S3 as storage provider, change FILE_MODE_AWS(docker-compose.yml) to true and provide credentials**

**3. Run on docker**

```bash
cd NTS-Design
docker compose up -d
```
The app will start running at <http://localhost:8888>
Open API Docs: <resources/api-docs.json>
