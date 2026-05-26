<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.insurance.dao.PolicyDAO" %>
<%@ page import="com.insurance.model.Policy" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Policies</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        h1 { color: #333; }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #667eea;
            color: white;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        a { color: #667eea; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 20px;
            background-color: #667eea;
            color: white;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <a href="index.jsp" class="back-link">← Back to Home</a>
    <h1>Insurance Policies</h1>
    <table>
        <tr>
            <th>Policy Number</th>
            <th>Type</th>
            <th>Coverage</th>
            <th>Premium</th>
            <th>Status</th>
        </tr>
        <% 
            PolicyDAO policyDAO = new PolicyDAO();
            List<Policy> policies = policyDAO.getAllPolicies();
            for (Policy policy : policies) {
        %>
        <tr>
            <td><%= policy.getPolicyNumber() %></td>
            <td><%= policy.getPolicyType() %></td>
            <td>$<%= policy.getCoverageAmount() %></td>
            <td>$<%= policy.getPremiumAmount() %></td>
            <td><span style="background: <%= policy.getStatus().equals("Active") ? "#4CAF50" : "#ff9800" %>; color: white; padding: 4px 8px; border-radius: 3px;"><%= policy.getStatus() %></span></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
