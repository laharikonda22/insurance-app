<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insurance Management System</title>
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
            align-items: center;
            justify-content: center;
        }
        
        .container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 600px;
        }
        
        h1 {
            color: #333;
            margin-bottom: 20px;
            font-size: 2.5em;
        }
        
        .subtitle {
            color: #666;
            margin-bottom: 40px;
            font-size: 1.1em;
        }
        
        .menu {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-bottom: 20px;
        }
        
        .menu-item {
            display: inline-block;
            padding: 15px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .menu-item:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        .info-box {
            background: #f0f4ff;
            padding: 20px;
            border-left: 4px solid #667eea;
            margin-top: 30px;
            text-align: left;
            border-radius: 5px;
        }
        
        .info-box h3 {
            color: #667eea;
            margin-bottom: 10px;
        }
        
        .info-box p {
            color: #666;
            line-height: 1.6;
            margin-bottom: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🛡️ Insurance Management System</h1>
        <p class="subtitle">Manage policies, claims, and payments efficiently</p>
        
        <div class="menu">
            <a href="customers" class="menu-item">👥 Customers</a>
            <a href="policies" class="menu-item">📋 Policies</a>
            <a href="claims" class="menu-item">📝 Claims</a>
            <a href="payments" class="menu-item">💳 Payments</a>
        </div>
        
        <div class="info-box">
            <h3>System Overview</h3>
            <p><strong>Version:</strong> 1.0.0</p>
            <p><strong>Database:</strong> MySQL (insurance_db)</p>
            <p><strong>Technology:</strong> Java WAR on Apache Tomcat</p>
            <p><strong>Status:</strong> ✓ Ready to Deploy</p>
        </div>
    </div>
</body>
</html>
