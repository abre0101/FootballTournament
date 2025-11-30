<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tournaments - Amateur Football</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <style>
        .container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 30px;
        }
        .header-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        .btn {
            padding: 12px 24px;
            background-color: #5c3ab7;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn:hover {
            opacity: 0.9;
        }
        .tournament-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .tournament-card {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .tournament-card h3 {
            margin-top: 0;
            color: #5c3ab7;
        }
        .tournament-info {
            margin: 10px 0;
        }
        .tournament-info label {
            font-weight: bold;
        }
        .status-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-upcoming {
            background-color: #ffd700;
            color: #333;
        }
        .status-ongoing {
            background-color: #00aa00;
            color: white;
        }
        .status-completed {
            background-color: #888;
            color: white;
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

    <div class="container">
        <div class="header-row">
            <h2>Tournaments</h2>
            <a href="${pageContext.request.contextPath}/tournament/create" class="btn">Create New Tournament</a>
        </div>
        
        <c:choose>
            <c:when test="${empty tournaments}">
                <p>No tournaments found. Create your first tournament!</p>
            </c:when>
            <c:otherwise>
                <div class="tournament-grid">
                    <c:forEach var="tournament" items="${tournaments}">
                        <div class="tournament-card">
                            <h3>${tournament.name}</h3>
                            <div class="tournament-info">
                                <label>Format:</label> ${tournament.format}
                            </div>
                            <div class="tournament-info">
                                <label>Start Date:</label> <fmt:formatDate value="${tournament.startDate}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="tournament-info">
                                <label>End Date:</label> <fmt:formatDate value="${tournament.endDate}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div class="tournament-info">
                                <label>Status:</label> 
                                <span class="status-badge status-${tournament.status.toLowerCase()}">${tournament.status}</span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
