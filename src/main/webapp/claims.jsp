<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.insurance.dao.ClaimDAO" %>
<%@ page import="com.insurance.model.Claim" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claims</title>
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
    <h1>Claims</h1>
    <table>
        <tr>
            <th>Claim Number</th>
            <th>Policy ID</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Approved Amount</th>
        </tr>
        <% 
            ClaimDAO claimDAO = new ClaimDAO();
            List<Claim> claims = claimDAO.getAllClaims();
            for (Claim claim : claims) {
        %>
        <tr>
            <td><%= claim.getClaimNumber() %></td>
            <td><%= claim.getPolicyId() %></td>
            <td>$<%= claim.getClaimAmount() %></td>
            <td><span style="background: <%= claim.getStatus().equals("Pending") ? "#ff9800" : claim.getStatus().equals("Approved") ? "#4CAF50" : "#f44336" %>; color: white; padding: 4px 8px; border-radius: 3px;"><%= claim.getStatus() %></span></td>
            <td><%= claim.getApprovedAmount() != null ? "$" + claim.getApprovedAmount() : "N/A" %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
