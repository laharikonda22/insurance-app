package com.insurance.servlet;

import com.insurance.dao.PolicyDAO;
import com.insurance.model.Policy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/policies")
public class PolicyServlet extends HttpServlet {
    private PolicyDAO policyDAO = new PolicyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        
        try {
            if (action == null || action.equals("list")) {
                listPolicies(out);
            } else if (action.equals("view")) {
                int policyId = Integer.parseInt(request.getParameter("id"));
                viewPolicy(out, policyId);
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
                addPolicy(request, out);
            } else if (action.equals("update")) {
                updatePolicy(request, out);
            }
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    private void listPolicies(PrintWriter out) {
        out.println("<html><head><title>Policies</title></head><body>");
        out.println("<h1>Insurance Policies</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>Policy #</th><th>Type</th><th>Coverage</th><th>Premium</th><th>Status</th></tr>");

        List<Policy> policies = policyDAO.getAllPolicies();
        for (Policy policy : policies) {
            out.println("<tr>");
            out.println("<td>" + policy.getPolicyNumber() + "</td>");
            out.println("<td>" + policy.getPolicyType() + "</td>");
            out.println("<td>$" + policy.getCoverageAmount() + "</td>");
            out.println("<td>$" + policy.getPremiumAmount() + "</td>");
            out.println("<td>" + policy.getStatus() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    private void viewPolicy(PrintWriter out, int policyId) {
        Policy policy = policyDAO.getPolicyById(policyId);
        out.println("<html><head><title>Policy Details</title></head><body>");
        
        if (policy != null) {
            out.println("<h1>" + policy.getPolicyNumber() + "</h1>");
            out.println("<p><b>Type:</b> " + policy.getPolicyType() + "</p>");
            out.println("<p><b>Coverage Amount:</b> $" + policy.getCoverageAmount() + "</p>");
            out.println("<p><b>Premium Amount:</b> $" + policy.getPremiumAmount() + "</p>");
            out.println("<p><b>Status:</b> " + policy.getStatus() + "</p>");
        } else {
            out.println("<p>Policy not found</p>");
        }
        
        out.println("</body></html>");
    }

    private void addPolicy(HttpServletRequest request, PrintWriter out) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Policy policy = new Policy();
        policy.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        policy.setPolicyNumber(request.getParameter("policyNumber"));
        policy.setPolicyType(request.getParameter("policyType"));
        policy.setCoverageAmount(new BigDecimal(request.getParameter("coverageAmount")));
        policy.setPremiumAmount(new BigDecimal(request.getParameter("premiumAmount")));
        policy.setStartDate(sdf.parse(request.getParameter("startDate")));
        policy.setEndDate(sdf.parse(request.getParameter("endDate")));

        boolean success = policyDAO.addPolicy(policy);
        out.println("<h2>" + (success ? "Policy created successfully!" : "Error creating policy") + "</h2>");
    }

    private void updatePolicy(HttpServletRequest request, PrintWriter out) throws Exception {
        Policy policy = new Policy();
        policy.setPolicyId(Integer.parseInt(request.getParameter("policyId")));
        policy.setPolicyNumber(request.getParameter("policyNumber"));
        policy.setPolicyType(request.getParameter("policyType"));
        policy.setStatus(request.getParameter("status"));

        boolean success = policyDAO.updatePolicy(policy);
        out.println("<h2>" + (success ? "Policy updated successfully!" : "Error updating policy") + "</h2>");
    }
}
