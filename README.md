# ⚽ Football Tournament Management System

A professional web-based application for managing football tournaments, teams, matches, and player statistics with real-time updates and beautiful UI.

> **Note**: This is a complete rewrite of the original PHP-based tournament manager, now built with Java, MongoDB, and modern web technologies.

## 🌟 Features

### Core Functionality
- **Tournament Management**: Create and manage multiple tournaments with different formats (League, Knockout, Group Stage)
- **Team Management**: Register teams with coaches and track comprehensive statistics
- **Match Scheduling**: Schedule matches with venues and dates
- **Live Standings**: Real-time league tables with automatic point calculations
- **Player Management**: Track individual player statistics including goals, assists, and cards
- **Dashboard**: Interactive dashboard with key metrics and top performers

### Technical Features
- **RESTful API**: Clean API architecture for all operations
- **MongoDB Integration**: NoSQL database for flexible data storage
- **Responsive Design**: Mobile-friendly interface with modern UI/UX
- **Real-time Updates**: Automatic statistics calculation and updates
- **Professional UI**: Gradient backgrounds, animations, and Font Awesome icons

## 🏗️ Architecture

### Technology Stack
- **Backend**: Java Servlets (Java 20)
- **Database**: MongoDB 4.11.1
- **Frontend**: HTML5, CSS3, JavaScript (ES6+)
- **Server**: Apache Tomcat 9.0
- **Build Tool**: Manual compilation with javac
- **Libraries**: 
  - MongoDB Java Driver (Sync)
  - Gson for JSON processing
  - Font Awesome for icons

### Project Structure
```
FootballTournament/
├── src/main/
│   ├── java/
│   │   ├── config/
│   │   │   └── MongoDBConnection.java
│   │   ├── dao/
│   │   │   ├── TeamDAO.java
│   │   │   ├── TournamentDAO.java
│   │   │   ├── MatchDAO.java
│   │   │   └── PlayerDAO.java
│   │   ├── model/
│   │   │   ├── Team.java
│   │   │   ├── Tournament.java
│   │   │   ├── Match.java
│   │   │   └── Player.java
│   │   ├── service/
│   │   │   └── TournamentService.java
│   │   └── FootballTournamentManager.java
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── lib/
│       │   │   ├── mongodb-driver-sync-4.11.1.jar
│       │   │   ├── mongodb-driver-core-4.11.1.jar
│       │   │   ├── bson-4.11.1.jar
│       │   │   └── gson-2.10.1.jar
│       │   └── web.xml
│       ├── index.html
│       └── app.js
└── build/
    ├── classes/
    └── FootballTournament.war
```

## 🚀 Installation & Setup

### Prerequisites
- Java JDK 20 or higher
- MongoDB 4.0 or higher (running on localhost:27017)
- Apache Tomcat 9.0
- Modern web browser

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/abre0101/FootballTournament.git
   cd FootballTournament
   ```

2. **Download required JAR files**
   Place these files in `src/main/webapp/WEB-INF/lib/`:
   - [mongodb-driver-sync-4.11.1.jar](https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-sync/4.11.1/mongodb-driver-sync-4.11.1.jar)
   - [mongodb-driver-core-4.11.1.jar](https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-core/4.11.1/mongodb-driver-core-4.11.1.jar)
   - [bson-4.11.1.jar](https://repo1.maven.org/maven2/org/mongodb/bson/4.11.1/bson-4.11.1.jar)
   - [gson-2.10.1.jar](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar)

3. **Ensure MongoDB is running**
   ```bash
   # MongoDB should be running on localhost:27017
   # Database name: football_tournament
   ```

4. **Compile the project**
   ```bash
   javac -cp "src/main/webapp/WEB-INF/lib/*;C:/Program Files/Apache Software Foundation/Tomcat 9.0/lib/servlet-api.jar" -d build/classes src/main/java/config/*.java src/main/java/model/*.java src/main/java/dao/*.java src/main/java/service/*.java src/main/java/*.java
   ```

5. **Create WAR file**
   ```bash
   cd build/war
   jar -cvf ../FootballTournament.war *
   ```

6. **Deploy to Tomcat**
   ```bash
   copy build\FootballTournament.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"
   ```

7. **Start Tomcat**
   ```bash
   $env:JAVA_HOME="C:\Program Files\Java\jdk-20"
   $env:CATALINA_HOME="C:\Program Files\Apache Software Foundation\Tomcat 9.0"
   & "$env:CATALINA_HOME\bin\catalina.bat" start
   ```

8. **Access the application**
   ```
   http://localhost:8080/FootballTournament/
   ```

## 📡 API Documentation

### Base URL
```
http://localhost:8080/FootballTournament/api
```

### Endpoints

#### Tournaments
- `GET /tournaments` - List all tournaments
- `GET /tournament/{id}` - Get tournament by ID
- `POST /tournament` - Create tournament
  - Parameters: `name`, `format` (LEAGUE/KNOCKOUT/GROUP_STAGE)

#### Teams
- `GET /teams` - List all teams
- `GET /team/{id}` - Get team by ID
- `POST /team` - Create team
  - Parameters: `name`, `coach`

#### Players
- `GET /players` - List all players
- `GET /players/team/{teamId}` - Get players by team
- `GET /players/topscorers` - Get top 10 scorers
- `POST /player` - Create player
  - Parameters: `name`, `jerseyNumber`, `position`, `teamId`

#### Matches
- `GET /matches` - List all matches
- `GET /matches/{tournamentId}` - Get matches by tournament
- `POST /match` - Schedule a match
  - Parameters: `tournamentId`, `homeTeamId`, `awayTeamId`, `homeTeamName`, `awayTeamName`, `venue`, `matchDate`
- `POST /match/result` - Update match result
  - Parameters: `matchId`, `homeScore`, `awayScore`

#### Standings
- `GET /standings/{tournamentId}` - Get tournament standings

## 💡 Usage Examples

### Creating a Tournament
```javascript
fetch('/FootballTournament/api/tournament', {
    method: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    body: 'name=Premier League 2025&format=LEAGUE'
});
```

### Registering a Team
```javascript
fetch('/FootballTournament/api/team', {
    method: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    body: 'name=Manchester United&coach=Erik ten Hag'
});
```

### Scheduling a Match
```javascript
fetch('/FootballTournament/api/match', {
    method: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    body: 'tournamentId=123&homeTeamId=456&awayTeamId=789&homeTeamName=Man+Utd&awayTeamName=Liverpool&venue=Old+Trafford&matchDate=2025-12-15T15:00'
});
```

### Updating Match Result
```javascript
fetch('/FootballTournament/api/match/result', {
    method: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    body: 'matchId=123&homeScore=3&awayScore=1'
});
```

## 🎨 Features Showcase

### Dashboard
- Real-time statistics display
- Top teams leaderboard
- Quick access to all sections
- Visual metrics with icons

### Tournament Management
- Multiple tournament formats
- Status tracking (Upcoming, Ongoing, Completed)
- Team registration per tournament

### Team Management
- Comprehensive team statistics
- Win/Draw/Loss records
- Goals scored and conceded
- Goal difference calculation
- Points calculation (3 for win, 1 for draw)

### Standings
- Automatic sorting by points, goal difference, and goals scored
- Position badges (Gold, Silver, Bronze)
- Complete match statistics
- Visual highlighting for top positions

## 🔧 Configuration

### MongoDB Connection
Edit `src/main/java/config/MongoDBConnection.java`:
```java
private static final String CONNECTION_STRING = "mongodb://localhost:27017";
private static final String DATABASE_NAME = "football_tournament";
```

### Server Port
Default Tomcat port: 8080
To change, edit Tomcat's `server.xml`

## 📊 Database Schema

### Collections
- **tournaments**: Tournament information
- **teams**: Team details and statistics
- **matches**: Match schedules and results
- **players**: Player information and statistics

### Sample Document (Team)
```json
{
  "_id": ObjectId("..."),
  "name": "Manchester United",
  "coach": "Erik ten Hag",
  "wins": 15,
  "draws": 5,
  "losses": 3,
  "goalsScored": 45,
  "goalsConceded": 20
}
```

## 🎯 Best Practices Implemented

1. **Separation of Concerns**: DAO, Service, and Controller layers
2. **RESTful API Design**: Clean, predictable endpoints
3. **Error Handling**: Comprehensive try-catch blocks
4. **Responsive Design**: Mobile-first approach
5. **Code Reusability**: Modular JavaScript functions
6. **Database Indexing**: Efficient MongoDB queries
7. **Clean Code**: Well-commented and organized

## 🚀 Future Enhancements

- [ ] User authentication and authorization
- [ ] Match live commentary
- [ ] Player transfer system
- [ ] Advanced statistics and analytics
- [ ] Export data to PDF/Excel
- [ ] Email notifications
- [ ] Multi-language support
- [ ] Mobile app integration

## 📝 License

This project is open source and available for educational purposes.

## 👨‍💻 Author

**Abraham Worku**
- GitHub: [@abre0101](https://github.com/abre0101)
- Email: abrahamworku10a@gmail.com

Created with ❤️ for football enthusiasts and tournament organizers at Debre Markos University.

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! See [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## 📞 Support

For support, please open an issue in the repository or contact the development team.

---

**Made with Java, MongoDB, and passion for football! ⚽**
