<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Football Tournament Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; min-height: 100vh; color: #FFFFFF; background-color: #0f0f0f; background-image: radial-gradient(ellipse 1000px 500px at 50% 0%, rgba(50, 50, 60, 0.4), transparent), radial-gradient(circle 400px at 20% 80%, rgba(255, 215, 0, 0.03), transparent), radial-gradient(circle 400px at 80% 80%, rgba(0, 217, 255, 0.03), transparent); }
        header { background: linear-gradient(135deg, rgba(30, 30, 30, 0.98), rgba(20, 20, 20, 0.98)); color: #FFFFFF; padding: 60px 30px; text-align: center; box-shadow: 0 10px 50px rgba(0, 0, 0, 0.8); border-bottom: 5px solid #FFD700; }
        header h1 { font-size: 3em; font-weight: 800; color: #FFD700; text-shadow: 3px 3px 10px rgba(0, 0, 0, 0.8); }
        .container { max-width: 1500px; margin: 50px auto; padding: 0 30px; }
        .nav { background: rgba(25, 25, 25, 0.98); padding: 30px; border-radius: 25px; box-shadow: 0 15px 60px rgba(0, 0, 0, 0.6); margin-bottom: 50px; display: flex; gap: 20px; flex-wrap: wrap; border: 2px solid rgba(255, 215, 0, 0.3); }
        .nav button { flex: 1; min-width: 200px; background: rgba(40, 40, 40, 0.9); color: #FFFFFF; border: 2px solid rgba(255, 215, 0, 0.4); padding: 20px 40px; cursor: pointer; border-radius: 18px; font-size: 1.15em; font-weight: 700; text-transform: uppercase; letter-spacing: 2px; transition: all 0.3s ease; }
        .nav button:hover { transform: translateY(-5px); box-shadow: 0 15px 40px rgba(255, 215, 0, 0.5); border-color: #FFD700; background: rgba(50, 50, 50, 0.9); }
        .section { background: rgba(25, 25, 25, 0.98); padding: 50px; border-radius: 30px; box-shadow: 0 20px 70px rgba(0, 0, 0, 0.7); margin-bottom: 50px; border: 2px solid rgba(255, 215, 0, 0.3); display: none; }
        .section.active { display: block; }
        .section h2 { color: #FFD700; font-size: 2.8em; font-weight: 800; margin-bottom: 35px; padding-bottom: 20px; border-bottom: 4px solid #FFD700; }
        .form-group { margin-bottom: 25px; }
        .form-group label { display: block; margin-bottom: 12px; font-weight: 700; color: #FFFFFF; text-transform: uppercase; letter-spacing: 1.5px; }
        .form-group input, .form-group select { width: 100%; padding: 18px; border: 2px solid rgba(255, 215, 0, 0.3); border-radius: 12px; font-size: 1.1em; background: rgba(0, 0, 0, 0.4); color: #FFFFFF; }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #FFD700; box-shadow: 0 0 25px rgba(255, 215, 0, 0.4); }
        .btn { background: linear-gradient(135deg, #00D9FF, #00A8CC); color: #FFFFFF; border: none; padding: 20px 50px; cursor: pointer; border-radius: 15px; font-size: 1.2em; font-weight: 700; text-transform: uppercase; letter-spacing: 2px; box-shadow: 0 10px 30px rgba(0, 217, 255, 0.4); }
        .btn:hover { transform: translateY(-5px); box-shadow: 0 15px 45px rgba(0, 217, 255, 0.6); }
        table { width: 100%; border-collapse: collapse; margin-top: 35px; background: rgba(30, 30, 30, 0.95); border-radius: 20px; overflow: hidden; box-shadow: 0 15px 50px rgba(0, 0, 0, 0.6); border: 2px solid rgba(255, 215, 0, 0.3); }
        th { background: rgba(40, 40, 40, 1); color: #FFD700; padding: 25px 20px; text-align: left; font-weight: 800; text-transform: uppercase; font-size: 0.95em; letter-spacing: 2px; border-bottom: 4px solid #FFD700; }
        td { padding: 20px; border-bottom: 1px solid rgba(255, 215, 0, 0.1); color: #FFFFFF; font-size: 1.05em; }
        tr:hover { background: rgba(255, 215, 0, 0.1); }
    </style>
<body>
    <header>
        <h1>⚽ Football Tournament Management System</h1>
    </header>
    
    <div class="container">
        <div class="nav">
            <button onclick="showSection('tournaments')">Tournaments</button>
            <button onclick="showSection('teams')">Teams</button>
            <button onclick="showSection('matches')">Matches</button>
            <button onclick="showSection('standings')">Standings</button>
        </div>
        
        <div id="tournaments" class="section active">
            <h2>Tournaments</h2>
            <div class="form-group">
                <label>Tournament Name:</label>
                <input type="text" id="tournamentName" placeholder="Enter tournament name">
            </div>
            <div class="form-group">
                <label>Format:</label>
                <select id="tournamentFormat">
                    <option value="LEAGUE">League</option>
                    <option value="KNOCKOUT">Knockout</option>
                    <option value="GROUP_STAGE">Group Stage</option>
                </select>
            </div>
            <button class="btn" onclick="createTournament()">Create Tournament</button>
            <table id="tournamentsTable">
                <thead>
                    <tr><th>Name</th><th>Format</th><th>Status</th><th>Actions</th></tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        
        <div id="teams" class="section">
            <h2>Teams</h2>
            <div class="form-group">
                <label>Team Name:</label>
                <input type="text" id="teamName" placeholder="Enter team name">
            </div>
            <div class="form-group">
                <label>Coach:</label>
                <input type="text" id="teamCoach" placeholder="Enter coach name">
            </div>
            <button class="btn" onclick="createTeam()">Create Team</button>
            <table id="teamsTable">
                <thead>
                    <tr><th>Name</th><th>Coach</th><th>W</th><th>D</th><th>L</th><th>GF</th><th>GA</th><th>Pts</th></tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        
        <div id="matches" class="section">
            <h2>Matches</h2>
            <table id="matchesTable">
                <thead>
                    <tr><th>Home</th><th>Score</th><th>Away</th><th>Date</th><th>Venue</th><th>Status</th></tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        
        <div id="standings" class="section">
            <h2>Standings</h2>
            <table id="standingsTable">
                <thead>
                    <tr><th>Pos</th><th>Team</th><th>P</th><th>W</th><th>D</th><th>L</th><th>GF</th><th>GA</th><th>GD</th><th>Pts</th></tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    
    <script>
        function showSection(sectionId) {
            document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
            document.getElementById(sectionId).classList.add('active');
            if (sectionId === 'tournaments') loadTournaments();
            if (sectionId === 'teams') loadTeams();
        }
        
        function createTournament() {
            const name = document.getElementById('tournamentName').value;
            const format = document.getElementById('tournamentFormat').value;
            fetch('/api/tournament', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: 'name=' + name + '&format=' + format
            }).then(() => loadTournaments());
        }
        
        function createTeam() {
            const name = document.getElementById('teamName').value;
            const coach = document.getElementById('teamCoach').value;
            fetch('/api/team', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: 'name=' + name + '&coach=' + coach
            }).then(() => loadTeams());
        }
        
        function loadTournaments() {
            fetch('/api/tournaments')
                .then(r => r.json())
                .then(data => {
                    const tbody = document.querySelector('#tournamentsTable tbody');
                    tbody.innerHTML = data.map(t => 
                        '<tr><td>' + t.name + '</td><td>' + t.format + '</td><td>' + t.status + '</td><td>View</td></tr>'
                    ).join('');
                });
        }
        
        function loadTeams() {
            fetch('/api/teams')
                .then(r => r.json())
                .then(data => {
                    const tbody = document.querySelector('#teamsTable tbody');
                    tbody.innerHTML = data.map(t => 
                        '<tr><td>' + t.name + '</td><td>' + t.coach + '</td><td>' + t.wins + '</td><td>' + 
                        t.draws + '</td><td>' + t.losses + '</td><td>' + t.goalsScored + '</td><td>' + 
                        t.goalsConceded + '</td><td>' + ((t.wins*3) + t.draws) + '</td></tr>'
                    ).join('');
                });
        }
        
        loadTournaments();
    </script>
</body>
</html>
