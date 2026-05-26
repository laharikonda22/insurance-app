package com.insurance.servlet;

import com.insurance.dao.ClaimDAO;
import com.insurance.model.Claim;

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

@WebServlet("/claims")
public class ClaimServlet extends HttpServlet {
    private ClaimDAO claimDAO = new ClaimDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        
        try {
            if (action == null || action.equals("list")) {
                listClaims(out);
            } else if (action.equals("view")) {
                int claimId = Integer.parseInt(request.getParameter("id"));
                viewClaim(out, claimId);
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
                addClaim(request, out);
            } else if (action.equals("approve")) {
                approveClaim(request, out);
            } else if (action.equals("reject")) {
                rejectClaim(request, out);
            }
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    private void listClaims(PrintWriter out) {
        out.println("<html><head><title>Claims</title></head><body>");
        out.println("<h1>Insurance Claims</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>Claim #</th><th>Amount</th><th>Status</th><th>Approved Amount</th></tr>");

        List<Claim> claims = claimDAO.getAllClaims();
        for (Claim claim : claims) {
            out.println("<tr>");
            out.println("<td>" + claim.getClaimNumber() + "</td>");
            out.println("<td>$" + claim.getClaimAmount() + "</td>");
            out.println("<td>" + claim.getStatus() + "</td>");
            out.println("<td>" + (claim.getApprovedAmount() != null ? "$" + claim.getApprovedAmount() : "N/A") + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    private void viewClaim(PrintWriter out, int claimId) {
        Claim claim = claimDAO.getClaimById(claimId);
        out.println("<html><head><title>Claim Details</title></head><body>");
        
        if (claim != null) {
            out.println("<h1>" + claim.getClaimNumber() + "</h1>");
            out.println("<p><b>Policy ID:</b> " + claim.getPolicyId() + "</p>");
            out.println("<p><b>Claim Amount:</b> $" + claim.getClaimAmount() + "</p>");
            out.println("<p><b>Status:</b> " + claim.getStatus() + "</p>");
            out.println("<p><b>Description:</b> " + claim.getClaimDescription() + "</p>");
        } else {
            out.println("<p>Claim not found</p>");
        }
        
        out.println("</body></html>");
    }

    private void addClaim(HttpServletRequest request, PrintWriter out) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Claim claim = new Claim();
        claim.setPolicyId(Integer.parseInt(request.getParameter("policyId")));
        claim.setClaimNumber(request.getParameter("claimNumber"));
        claim.setClaimAmount(new BigDecimal(request.getParameter("claimAmount")));
        claim.setClaimDate(sdf.parse(request.getParameter("claimDate")));
        claim.setClaimDescription(request.getParameter("claimDescription"));

        boolean success = claimDAO.addClaim(claim);
        out.println("<h2>" + (success ? "Claim filed successfully!" : "Error filing claim") + "</h2>");
    }

    private void approveClaim(HttpServletRequest request, PrintWriter out) throws Exception {
        int claimId = Integer.parseInt(request.getParameter("claimId"));
        BigDecimal approvedAmount = new BigDecimal(request.getParameter("approvedAmount"));

        boolean success = claimDAO.approveClaim(claimId, approvedAmount);
        out.println("<h2>" + (success ? "Claim approved successfully!" : "Error approving claim") + "</h2>");
    }

    private void rejectClaim(HttpServletRequest request, PrintWriter out) throws Exception {
        int claimId = Integer.parseInt(request.getParameter("claimId"));
        boolean success = claimDAO.rejectClaim(claimId);
        out.println("<h2>" + (success ? "Claim rejected successfully!" : "Error rejecting claim") + "</h2>");
    }
}
