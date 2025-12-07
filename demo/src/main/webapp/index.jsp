<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🎮 Tournoi e-Sport Platform</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
        }
        
        .container {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 40px;
            max-width: 800px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
        }
        
        h1 {
            font-size: 3em;
            margin-bottom: 20px;
            text-align: center;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }
        
        .subtitle {
            text-align: center;
            font-size: 1.2em;
            margin-bottom: 40px;
            opacity: 0.9;
        }
        
        .services {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
            margin-top: 30px;
        }
        
        .service-card {
            background: rgba(255, 255, 255, 0.15);
            padding: 30px;
            border-radius: 15px;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
        }
        
        .service-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
        }
        
        .service-card h3 {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        
        .service-card p {
            opacity: 0.8;
            margin-bottom: 15px;
        }
        
        .service-card a {
            display: inline-block;
            background: white;
            color: #667eea;
            padding: 10px 20px;
            border-radius: 25px;
            text-decoration: none;
            font-weight: bold;
            transition: background 0.3s;
        }
        
        .service-card a:hover {
            background: #f0f0f0;
        }
        
        .status {
            background: rgba(76, 175, 80, 0.2);
            border: 2px solid #4CAF50;
            border-radius: 10px;
            padding: 15px;
            margin-top: 30px;
            text-align: center;
        }
        
        .status-dot {
            display: inline-block;
            width: 10px;
            height: 10px;
            background: #4CAF50;
            border-radius: 50%;
            margin-right: 10px;
            animation: pulse 2s infinite;
        }
        
        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.5; }
        }
        
        .footer {
            text-align: center;
            margin-top: 30px;
            opacity: 0.7;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🎮 TOURNOI E-SPORT</h1>
        <p class="subtitle">Plateforme de Gestion de Tournoi Professionnel</p>
        
        <div class="services">
            <div class="service-card">
                <h3>🧑‍💻 Service SOAP</h3>
                <p>Gestion des Joueurs</p>
                <a href="services/players?wsdl" target="_blank">Voir WSDL</a>
            </div>
            
            <div class="service-card">
                <h3>⚔️ Service REST</h3>
                <p>Gestion des Matchs</p>
                <a href="api/matches" target="_blank">API Endpoint</a>
            </div>
            
            <div class="service-card">
                <h3>📊 Base de Données</h3>
                <p>H2 Console</p>
                <a href="http://localhost:8080/h2-console" target="_blank">Accéder</a>
            </div>
            
            <div class="service-card">
                <h3>📄 Documentation</h3>
                <p>API & Guides</p>
                <a href="#" onclick="alert('Consultez le README.md du projet')">Docs</a>
            </div>
        </div>
        
        <div class="status">
            <span class="status-dot"></span>
            <strong>Serveurs en ligne</strong> - Tous les services sont opérationnels
        </div>
        
        <div class="footer">
            <p>🏆 SOA Mini-Projet 2025 | Architecture Orientée Services</p>
            <p style="margin-top: 10px;">
                <strong>Technologies:</strong> SOAP • REST • JPA • XML • XPath • JavaFX
            </p>
        </div>
    </div>
</body>
</html>