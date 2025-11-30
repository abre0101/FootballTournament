<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Tournament - Amateur Football</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <style>
        .error {
            color: #FF0000;
            font-size: 14px;
            margin-top: 5px;
        }
        .success {
            color: #00AA00;
            font-size: 14px;
            margin-top: 5px;
        }
        .form-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background: #f9f9f9;
            border-radius: 8px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-row {
            display: flex;
            gap: 20px;
        }
        .form-row .form-group {
            flex: 1;
        }
        .btn-group {
            display: flex;
            gap: 10px;
            justify-content: center;
            margin-top: 30px;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn-primary {
            background-color: #5c3ab7;
            color: white;
        }
        .btn-secondary {
            background-color: #aaa;
            color: white;
        }
        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <header id="header">
        <h1><a href="${pageContext.request.contextPath}/">Amateur Football</a></h1>
        <nav id="nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/tournament/list">Tournaments</a></li>
                <li><a href="${pageContext.request.contextPath}/team/list">Teams</a></li>
            </ul>
        </nav>
    </header>

    <div class="form-container">
        <h2>Create Tournament</h2>
        <form id="tournamentForm">
            <div class="form-group">
                <label for="tournamentname">Tournament Name:</label>
                <input type="text" id="tournamentname" name="tournamentname" required>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="tournamentstart">Start Date:</label>
                    <input type="date" id="tournamentstart" name="tournamentstart" required>
                </div>
                <div class="form-group">
                    <label for="tournamentend">End Date:</label>
                    <input type="date" id="tournamentend" name="tournamentend" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="format">Tournament Format:</label>
                <select id="format" name="format">
                    <option value="LEAGUE">League</option>
                    <option value="KNOCKOUT">Knockout</option>
                    <option value="GROUP_STAGE">Group Stage</option>
                </select>
            </div>
            
            <div id="message"></div>
            
            <div class="btn-group">
                <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/tournament/list'">Cancel</button>
                <button type="submit" class="btn btn-primary">Create Tournament</button>
            </div>
        </form>
    </div>

    <script>
        document.getElementById('tournamentForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            const messageDiv = document.getElementById('message');
            
            fetch('${pageContext.request.contextPath}/tournament/create', {
                method: 'POST',
                body: new URLSearchParams(formData)
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    messageDiv.innerHTML = '<p class="success">' + data.message + '</p>';
                    setTimeout(() => {
                        window.location.href = '${pageContext.request.contextPath}/tournament/list';
                    }, 1500);
                } else {
                    messageDiv.innerHTML = '<p class="error">' + data.error + '</p>';
                }
            })
            .catch(error => {
                messageDiv.innerHTML = '<p class="error">Error creating tournament. Please try again.</p>';
                console.error('Error:', error);
            });
        });
    </script>
</body>
</html>
