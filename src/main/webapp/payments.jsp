<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.insurance.dao.PaymentDAO" %>
<%@ page import="com.insurance.model.Payment" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payments</title>
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
    <h1>Payments</h1>
    <table>
        <tr>
            <th>Payment ID</th>
            <th>Policy ID</th>
            <th>Amount</th>
            <th>Payment Date</th>
            <th>Method</th>
            <th>Status</th>
        </tr>
        <% 
            PaymentDAO paymentDAO = new PaymentDAO();
            List<Payment> payments = paymentDAO.getAllPayments();
            for (Payment payment : payments) {
        %>
        <tr>
            <td><%= payment.getPaymentId() %></td>
            <td><%= payment.getPolicyId() %></td>
            <td>$<%= payment.getAmount() %></td>
            <td><%= payment.getPaymentDate() %></td>
            <td><%= payment.getPaymentMethod() %></td>
            <td><span style="background: <%= payment.getStatus().equals("Completed") ? "#4CAF50" : payment.getStatus().equals("Pending") ? "#ff9800" : "#f44336" %>; color: white; padding: 4px 8px; border-radius: 3px;"><%= payment.getStatus() %></span></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
