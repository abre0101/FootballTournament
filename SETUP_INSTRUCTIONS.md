# Football Tournament Management System - Setup Guide

## Prerequisites
✅ Java 20 - Installed
✅ MongoDB - Running
✅ Apache Tomcat 9.0 - Configured in Eclipse

## Step 1: Download Required JAR Files

Download these files and place them in `src/main/webapp/WEB-INF/lib/`:

### MongoDB Driver (Download from Maven Central):
1. mongodb-driver-sync-4.11.1.jar
   https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-sync/4.11.1/mongodb-driver-sync-4.11.1.jar

2. mongodb-driver-core-4.11.1.jar
   https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-core/4.11.1/mongodb-driver-core-4.11.1.jar

3. bson-4.11.1.jar
   https://repo1.maven.org/maven2/org/mongodb/bson/4.11.1/bson-4.11.1.jar

### Gson (Download from Maven Central):
4. gson-2.10.1.jar
   https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar

## Step 2: Add JARs to Eclipse Build Path

1. Right-click on your project in Eclipse
2. Select "Build Path" > "Configure Build Path"
3. Click "Add JARs" button
4. Navigate to `FootballTournament/src/main/webapp/WEB-INF/lib`
5. Select all 4 JAR files
6. Click OK

## Step 3: Verify MongoDB Connection

Make sure MongoDB is running on `localhost:27017` (default port)
Database name: `football_tournament`

## Step 4: Deploy to Tomcat

### Option A: Using Eclipse
1. Right-click on project
2. Select "Run As" > "Run on Server"
3. Select your Tomcat 9.0 server
4. Click Finish

### Option B: Manual Deployment
1. Right-click project > Export > WAR file
2. Save as `FootballTournament.war`
3. Copy to Tomcat's `webapps` folder
4. Start Tomcat

## Step 5: Access the Application

Open browser and navigate to:
- Home Page: http://localhost:8080/FootballTournament/
- API Endpoints:
  - GET /api/tournaments - List all tournaments
  - GET /api/teams - List all teams
  - POST /api/tournament - Create tournament
  - POST /api/team - Create team
  - POST /api/match/result - Update match result

## Quick Test

Once deployed, test the API:
```
http://localhost:8080/FootballTournament/api/
```

Should return: `{"message":"Football Tournament Management API"}`

## Troubleshooting

- If MongoDB connection fails, check if MongoDB is running
- If classes not found, verify JAR files are in WEB-INF/lib
- If 404 error, check Tomcat deployment and context path
