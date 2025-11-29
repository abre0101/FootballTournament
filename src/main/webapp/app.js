// Football Tournament Management System - JavaScript
const API_BASE = '/FootballTournament/api';
let currentMatchId = null;
let allTeams = [];
let allTournaments = [];

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
    loadDashboard();
    setInterval(updateStats, 30000); // Update stats every 30 seconds
});

// Navigation
function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('.nav button').forEach(b => b.classList.remove('active'));
    
    document.getElementById(sectionId).classList.add('active');
    event.target.classList.add('active');
    
    if (sectionId === 'dashboard') loadDashboard();
    if (sectionId === 'tournaments') loadTournaments();
    if (sectionId === 'teams') loadTeams();
    if (sectionId === 'matches') { loadMatches(); loadMatchFormData(); }
    if (sectionId === 'standings') loadStandings();
}

// Dashboard
async function loadDashboard() {
    const content = document.getElementById('dashboardContent');
    content.innerHTML = '<div class="loading"><div class="spinner"></div><p>Loading...</p></div>';
    
    try {
        const [teams, tournaments] = await Promise.all([
            fetch(API_BASE + '/teams').then(r => r.json()),
            fetch(API_BASE + '/tournaments').then(r => r.json())
        ]);
        
        const totalGoals = teams.reduce((sum, t) => sum + t.goalsScored, 0);
        const totalMatches = teams.reduce((sum, t) => sum + t.wins + t.draws + t.losses, 0) / 2;
        
        // Sort teams by points
        teams.sort((a, b) => {
            const ptsA = (a.wins * 3) + a.draws;
            const ptsB = (b.wins * 3) + b.draws;
            return ptsB - ptsA;
        });
        
        const topTeams = teams.slice(0, 5);
        
        content.innerHTML = `
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px;">
                <div class="card">
                    <div class="card-header"><i class="fas fa-trophy"></i> Top Teams</div>
                    ${topTeams.length > 0 ? topTeams.map((t, i) => `
                        <div style="display: flex; align-items: center; padding: 10px; border-bottom: 1px solid #f0f0f0;">
                            <span class="position-badge position-${i < 3 ? i + 1 : 'other'}">${i + 1}</span>
                            <div style="margin-left: 15px; flex: 1;">
                                <strong>${t.name}</strong><br>
                                <small>${t.wins}W ${t.draws}D ${t.losses}L</small>
                            </div>
                            <strong style="font-size: 1.2em; color: var(--success);">${(t.wins * 3) + t.draws} pts</strong>
                        </div>
                    `).join('') : '<p class="empty-state">No teams yet</p>'}
                </div>
                
                <div class="card">
                    <div class="card-header"><i class="fas fa-chart-bar"></i> Statistics</div>
                    <div style="padding: 15px;">
                        <div style="margin-bottom: 15px;">
                            <strong>Total Tournaments:</strong> ${tournaments.length}
                        </div>
                        <div style="margin-bottom: 15px;">
                            <strong>Total Teams:</strong> ${teams.length}
                        </div>
                        <div style="margin-bottom: 15px;">
                            <strong>Matches Played:</strong> ${totalMatches}
                        </div>
                        <div style="margin-bottom: 15px;">
                            <strong>Total Goals:</strong> ${totalGoals}
                        </div>
                        <div>
                            <strong>Avg Goals/Match:</strong> ${totalMatches > 0 ? (totalGoals / totalMatches).toFixed(2) : '0.00'}
                        </div>
                    </div>
                </div>
                
                <div class="card">
                    <div class="card-header"><i class="fas fa-fire"></i> Recent Activity</div>
                    <div style="padding: 15px;">
                        <p><i class="fas fa-check-circle" style="color: var(--success);"></i> System is running smoothly</p>
                        <p style="margin-top: 10px;"><i class="fas fa-database" style="color: var(--secondary);"></i> MongoDB connected</p>
                        <p style="margin-top: 10px;"><i class="fas fa-users" style="color: var(--warning);"></i> ${teams.length} teams registered</p>
                    </div>
                </div>
            </div>
        `;
        
        updateStats();
    } catch (error) {
        content.innerHTML = '<div class="message error"><i class="fas fa-exclamation-circle"></i> Error loading dashboard</div>';
    }
}

// Update stats bar
async function updateStats() {
    try {
        const [teams, tournaments] = await Promise.all([
            fetch(API_BASE + '/teams').then(r => r.json()),
            fetch(API_BASE + '/tournaments').then(r => r.json())
        ]);
        
        const totalGoals = teams.reduce((sum, t) => sum + t.goalsScored, 0);
        const totalMatches = teams.reduce((sum, t) => sum + t.wins + t.draws + t.losses, 0) / 2;
        
        document.getElementById('totalTournaments').textContent = tournaments.length;
        document.getElementById('totalTeams').textContent = teams.length;
        document.getElementById('totalMatches').textContent = Math.floor(totalMatches);
        document.getElementById('totalGoals').textContent = totalGoals;
    } catch (error) {
        console.error('Error updating stats:', error);
    }
}

// Messages
function showMessage(elementId, message, isError = false) {
    const el = document.getElementById(elementId);
    el.innerHTML = `<div class="message ${isError ? 'error' : 'success'}">
        <i class="fas fa-${isError ? 'exclamation-circle' : 'check-circle'}"></i> ${message}
    </div>`;
    setTimeout(() => el.innerHTML = '', 5000);
}

// Tournaments
async function createTournament() {
    const name = document.getElementById('tournamentName').value.trim();
    const format = document.getElementById('tournamentFormat').value;
    
    if (!name) {
        showMessage('tournamentMessage', 'Please enter tournament name', true);
        return;
    }
    
    try {
        const response = await fetch(API_BASE + '/tournament', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `name=${encodeURIComponent(name)}&format=${format}`
        });
        
        if (response.ok) {
            showMessage('tournamentMessage', 'Tournament created successfully!');
            document.getElementById('tournamentName').value = '';
            loadTournaments();
            updateStats();
        } else {
            throw new Error('Failed to create tournament');
        }
    } catch (error) {
        showMessage('tournamentMessage', 'Error creating tournament', true);
    }
}

async function loadTournaments() {
    try {
        const data = await fetch(API_BASE + '/tournaments').then(r => r.json());
        allTournaments = data;
        const tbody = document.querySelector('#tournamentsTable tbody');
        
        if (data.length === 0) {
            tbody.innerHTML = `<tr><td colspan="5" class="empty-state">
                <i class="fas fa-trophy"></i><br>No tournaments yet. Create one above!
            </td></tr>`;
        } else {
            tbody.innerHTML = data.map(t => `
                <tr>
                    <td><strong>${t.name}</strong></td>
                    <td><span class="badge badge-info">${t.format}</span></td>
                    <td><span class="badge badge-${t.status === 'ONGOING' ? 'success' : 'warning'}">${t.status}</span></td>
                    <td>${t.teamIds ? t.teamIds.length : 0} teams</td>
                    <td>${new Date(t.startDate).toLocaleDateString()}</td>
                </tr>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading tournaments:', error);
    }
}

// Teams
async function createTeam() {
    const name = document.getElementById('teamName').value.trim();
    const coach = document.getElementById('teamCoach').value.trim();
    
    if (!name || !coach) {
        showMessage('teamMessage', 'Please enter team name and coach', true);
        return;
    }
    
    try {
        const response = await fetch(API_BASE + '/team', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `name=${encodeURIComponent(name)}&coach=${encodeURIComponent(coach)}`
        });
        
        if (response.ok) {
            showMessage('teamMessage', 'Team registered successfully!');
            document.getElementById('teamName').value = '';
            document.getElementById('teamCoach').value = '';
            loadTeams();
            updateStats();
        } else {
            throw new Error('Failed to create team');
        }
    } catch (error) {
        showMessage('teamMessage', 'Error registering team', true);
    }
}

async function loadTeams() {
    try {
        const data = await fetch(API_BASE + '/teams').then(r => r.json());
        allTeams = data;
        const tbody = document.querySelector('#teamsTable tbody');
        
        if (data.length === 0) {
            tbody.innerHTML = `<tr><td colspan="9" class="empty-state">
                <i class="fas fa-users"></i><br>No teams yet. Register one above!
            </td></tr>`;
        } else {
            tbody.innerHTML = data.map(t => {
                const pts = (t.wins * 3) + t.draws;
                const gd = t.goalsScored - t.goalsConceded;
                return `
                    <tr>
                        <td><strong><i class="fas fa-shield-alt"></i> ${t.name}</strong></td>
                        <td>${t.coach}</td>
                        <td><span class="badge badge-success">${t.wins}</span></td>
                        <td><span class="badge badge-warning">${t.draws}</span></td>
                        <td><span class="badge badge-danger">${t.losses}</span></td>
                        <td>${t.goalsScored}</td>
                        <td>${t.goalsConceded}</td>
                        <td style="color: ${gd >= 0 ? 'green' : 'red'}; font-weight: bold;">${gd >= 0 ? '+' : ''}${gd}</td>
                        <td><strong style="font-size: 1.2em; color: var(--success);">${pts}</strong></td>
                    </tr>
                `;
            }).join('');
        }
    } catch (error) {
        console.error('Error loading teams:', error);
    }
}

// Matches
async function loadMatchFormData() {
    try {
        const [teams, tournaments] = await Promise.all([
            fetch(API_BASE + '/teams').then(r => r.json()),
            fetch(API_BASE + '/tournaments').then(r => r.json())
        ]);
        
        const tournamentSelect = document.getElementById('matchTournament');
        const homeTeamSelect = document.getElementById('matchHomeTeam');
        const awayTeamSelect = document.getElementById('matchAwayTeam');
        
        tournamentSelect.innerHTML = '<option value="">Select Tournament</option>' + 
            tournaments.map(t => `<option value="${t.id.timestamp}">${t.name}</option>`).join('');
        
        const teamOptions = '<option value="">Select Team</option>' + 
            teams.map(t => `<option value="${t.id.timestamp}">${t.name}</option>`).join('');
        
        homeTeamSelect.innerHTML = teamOptions;
        awayTeamSelect.innerHTML = teamOptions;
    } catch (error) {
        console.error('Error loading form data:', error);
    }
}

async function scheduleMatch() {
    const tournamentId = document.getElementById('matchTournament').value;
    const homeTeamId = document.getElementById('matchHomeTeam').value;
    const awayTeamId = document.getElementById('matchAwayTeam').value;
    const venue = document.getElementById('matchVenue').value.trim();
    const matchDate = document.getElementById('matchDate').value;
    
    if (!tournamentId || !homeTeamId || !awayTeamId || !venue || !matchDate) {
        showMessage('matchMessage', 'Please fill all fields', true);
        return;
    }
    
    if (homeTeamId === awayTeamId) {
        showMessage('matchMessage', 'Home and away teams must be different', true);
        return;
    }
    
    // Find team names
    const homeTeam = allTeams.find(t => t.id.timestamp == homeTeamId);
    const awayTeam = allTeams.find(t => t.id.timestamp == awayTeamId);
    
    if (!homeTeam || !awayTeam) {
        showMessage('matchMessage', 'Error finding teams', true);
        return;
    }
    
    try {
        const response = await fetch(API_BASE + '/match', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `tournamentId=${tournamentId}&homeTeamId=${homeTeamId}&awayTeamId=${awayTeamId}&homeTeamName=${encodeURIComponent(homeTeam.name)}&awayTeamName=${encodeURIComponent(awayTeam.name)}&venue=${encodeURIComponent(venue)}&matchDate=${encodeURIComponent(matchDate)}`
        });
        
        if (response.ok) {
            showMessage('matchMessage', 'Match scheduled successfully!');
            document.getElementById('matchVenue').value = '';
            document.getElementById('matchDate').value = '';
            loadMatches();
            updateStats();
        } else {
            throw new Error('Failed to schedule match');
        }
    } catch (error) {
        showMessage('matchMessage', 'Error scheduling match', true);
        console.error('Error:', error);
    }
}

async function loadMatches() {
    try {
        const data = await fetch(API_BASE + '/matches').then(r => r.json());
        const tbody = document.querySelector('#matchesTable tbody');
        
        if (data.length === 0) {
            tbody.innerHTML = `<tr><td colspan="7" class="empty-state">
                <i class="fas fa-futbol"></i><br>No matches scheduled yet. Schedule one above!
            </td></tr>`;
        } else {
            tbody.innerHTML = data.map(m => {
                const statusBadge = m.status === 'COMPLETED' ? 'success' : 
                                   m.status === 'ONGOING' ? 'warning' : 'info';
                const matchDate = m.matchDate ? new Date(m.matchDate).toLocaleString() : 'TBD';
                const score = m.status === 'COMPLETED' ? 
                    `${m.homeScore} - ${m.awayScore}` : 
                    `<button class="btn btn-small" onclick="openMatchResultModal('${m.id.timestamp}', '${m.homeTeamName}', '${m.awayTeamName}')">Set Result</button>`;
                
                return `
                    <tr>
                        <td><strong>${m.homeTeamName}</strong></td>
                        <td style="text-align: center;">${score}</td>
                        <td><strong>${m.awayTeamName}</strong></td>
                        <td>${matchDate}</td>
                        <td><i class="fas fa-map-marker-alt"></i> ${m.venue}</td>
                        <td><span class="badge badge-${statusBadge}">${m.status}</span></td>
                        <td>
                            ${m.status !== 'COMPLETED' ? 
                                `<button class="btn btn-small btn-warning" onclick="openMatchResultModal('${m.id.timestamp}', '${m.homeTeamName}', '${m.awayTeamName}')">
                                    <i class="fas fa-edit"></i> Update
                                </button>` : 
                                '<span style="color: green;"><i class="fas fa-check-circle"></i> Completed</span>'}
                        </td>
                    </tr>
                `;
            }).join('');
        }
    } catch (error) {
        console.error('Error loading matches:', error);
        const tbody = document.querySelector('#matchesTable tbody');
        tbody.innerHTML = `<tr><td colspan="7" class="empty-state">
            <i class="fas fa-exclamation-circle"></i><br>Error loading matches
        </td></tr>`;
    }
}

function openMatchResultModal(matchId, homeTeam, awayTeam) {
    currentMatchId = matchId;
    document.getElementById('modalMatchInfo').innerHTML = `
        <p style="text-align: center; font-size: 1.2em; margin-bottom: 20px;">
            <strong>${homeTeam}</strong> vs <strong>${awayTeam}</strong>
        </p>
    `;
    document.getElementById('matchResultModal').classList.add('active');
}

function closeModal() {
    document.getElementById('matchResultModal').classList.remove('active');
    currentMatchId = null;
}

async function submitMatchResult() {
    const homeScore = document.getElementById('modalHomeScore').value;
    const awayScore = document.getElementById('modalAwayScore').value;
    
    try {
        const response = await fetch(API_BASE + '/match/result', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `matchId=${currentMatchId}&homeScore=${homeScore}&awayScore=${awayScore}`
        });
        
        if (response.ok) {
            showMessage('matchMessage', 'Match result updated successfully!');
            closeModal();
            loadMatches();
            updateStats();
        } else {
            throw new Error('Failed to update match result');
        }
    } catch (error) {
        showMessage('matchMessage', 'Error updating match result', true);
    }
}

// Standings
async function loadStandings() {
    try {
        const data = await fetch(API_BASE + '/teams').then(r => r.json());
        
        // Sort by points, then goal difference, then goals scored
        data.sort((a, b) => {
            const ptsA = (a.wins * 3) + a.draws;
            const ptsB = (b.wins * 3) + b.draws;
            if (ptsB !== ptsA) return ptsB - ptsA;
            
            const gdA = a.goalsScored - a.goalsConceded;
            const gdB = b.goalsScored - b.goalsConceded;
            if (gdB !== gdA) return gdB - gdA;
            
            return b.goalsScored - a.goalsScored;
        });
        
        const tbody = document.querySelector('#standingsTable tbody');
        
        if (data.length === 0) {
            tbody.innerHTML = `<tr><td colspan="10" class="empty-state">
                <i class="fas fa-chart-line"></i><br>No teams in standings yet.
            </td></tr>`;
        } else {
            tbody.innerHTML = data.map((t, idx) => {
                const pts = (t.wins * 3) + t.draws;
                const gd = t.goalsScored - t.goalsConceded;
                const played = t.wins + t.draws + t.losses;
                const posClass = idx < 3 ? `position-${idx + 1}` : 'position-other';
                
                return `
                    <tr style="${idx < 4 ? 'background: #f0f9ff;' : ''}">
                        <td><span class="position-badge ${posClass}">${idx + 1}</span></td>
                        <td><strong><i class="fas fa-shield-alt"></i> ${t.name}</strong></td>
                        <td>${played}</td>
                        <td>${t.wins}</td>
                        <td>${t.draws}</td>
                        <td>${t.losses}</td>
                        <td>${t.goalsScored}</td>
                        <td>${t.goalsConceded}</td>
                        <td style="color: ${gd >= 0 ? 'green' : 'red'}; font-weight: bold;">${gd >= 0 ? '+' : ''}${gd}</td>
                        <td><strong style="font-size: 1.3em; color: var(--success);">${pts}</strong></td>
                    </tr>
                `;
            }).join('');
        }
    } catch (error) {
        console.error('Error loading standings:', error);
    }
}

// Close modal on outside click
document.getElementById('matchResultModal')?.addEventListener('click', (e) => {
    if (e.target.id === 'matchResultModal') closeModal();
});
