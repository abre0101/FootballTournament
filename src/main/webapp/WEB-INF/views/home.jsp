<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Football Tournament Management</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f4f4f4; }
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        header { background: #2c3e50; color: white; padding: 20px; text-align: center; }
        .nav { background: #34495e; padding: 15px; margin-bottom: 20px; }
        .nav button { background: #3498db; color: white; border: none; padding: 10px 20px; 
                      margin: 0 5px; cursor: pointer; border-radius: 5px; }
        .nav button:hover { background: #2980b9; }
        .section { background: white; padding: 20px; margin-bottom: 20px; border-radius: 5px; 
                   box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: none; }
        .section.active { display: block; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #3498db; color: white; }
        tr:hover { background: #f5f5f5; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input, .form-group select { width: 100%; padding: 8px; border: 1px solid #ddd; 
                                                 border-radius: 4px; }
        .btn { background: #27ae60; color: white; border: none; padding: 10px 20px; 
               cursor: pointer; border-radius: 5px; }
        .btn:hover { background: #229954; }
    </style>
</head>
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
