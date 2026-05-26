package com.insurance.servlet;

import com.insurance.dao.PaymentDAO;
import com.insurance.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {
    private PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        
        try {
            if (action == null || action.equals("list")) {
                listPayments(out);
            } else if (action.equals("view")) {
                int paymentId = Integer.parseInt(request.getParameter("id"));
                viewPayment(out, paymentId);
            }
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        
        try {
            if (action.equals("add")) {
                addPayment(request, out);
            } else if (action.equals("complete")) {
                completePayment(request, out);
            }
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    private void listPayments(PrintWriter out) {
        out.println("<html><head><title>Payments</title></head><body>");
        out.println("<h1>Payments</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Policy ID</th><th>Amount</th><th>Date</th><th>Status</th></tr>");

        List<Payment> payments = paymentDAO.getAllPayments();
        for (Payment payment : payments) {
            out.println("<tr>");
            out.println("<td>" + payment.getPaymentId() + "</td>");
            out.println("<td>" + payment.getPolicyId() + "</td>");
            out.println("<td>$" + payment.getAmount() + "</td>");
            out.println("<td>" + payment.getPaymentDate() + "</td>");
            out.println("<td>" + payment.getStatus() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    private void viewPayment(PrintWriter out, int paymentId) {
        Payment payment = paymentDAO.getPaymentById(paymentId);
        out.println("<html><head><title>Payment Details</title></head><body>");
        
        if (payment != null) {
            out.println("<h1>Payment #" + payment.getPaymentId() + "</h1>");
            out.println("<p><b>Policy ID:</b> " + payment.getPolicyId() + "</p>");
            out.println("<p><b>Amount:</b> $" + payment.getAmount() + "</p>");
            out.println("<p><b>Status:</b> " + payment.getStatus() + "</p>");
        } else {
            out.println("<p>Payment not found</p>");
        }
        
        out.println("</body></html>");
    }

    private void addPayment(HttpServletRequest request, PrintWriter out) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Payment payment = new Payment();
        payment.setPolicyId(Integer.parseInt(request.getParameter("policyId")));
        payment.setAmount(new BigDecimal(request.getParameter("amount")));
        payment.setPaymentDate(sdf.parse(request.getParameter("paymentDate")));
        payment.setPaymentMethod(request.getParameter("paymentMethod"));

        boolean success = paymentDAO.addPayment(payment);
        out.println("<h2>" + (success ? "Payment recorded successfully!" : "Error recording payment") + "</h2>");
    }

    private void completePayment(HttpServletRequest request, PrintWriter out) throws Exception {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        boolean success = paymentDAO.updatePaymentStatus(paymentId, "Completed");
        out.println("<h2>" + (success ? "Payment completed successfully!" : "Error completing payment") + "</h2>");
    }
}
