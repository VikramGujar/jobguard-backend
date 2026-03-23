CREATE DATABASE jobguard_report;
CREATE DATABASE jobguard_admin;
CREATE DATABASE jobguard_notification;

GRANT ALL PRIVILEGES ON DATABASE jobguard_auth TO jobguard;
GRANT ALL PRIVILEGES ON DATABASE jobguard_report TO jobguard;
GRANT ALL PRIVILEGES ON DATABASE jobguard_admin TO jobguard;
GRANT ALL PRIVILEGES ON DATABASE jobguard_notification TO jobguard;
```

Your root folder should now look like this:
```
JobGuard/
├── init-db.sql           ← just created
├── docker-compose.yml
├── pom.xml
├── discovery-server/
├── api-gateway/
├── auth-service/
├── report-service/
├── admin-service/
└── notification-service/