<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .error-container {
            background: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 600px;
        }
        h1 { color: #dc3545; }
        p { color: #666; }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>⚠️ An Error Occurred</h1>
        <p><%= exception != null ? exception.getMessage() : "An unexpected error occurred." %></p>
        <p><a href="index.jsp">Return to Home</a></p>
    </div>
</body>
</html>
