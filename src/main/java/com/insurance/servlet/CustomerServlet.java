package com.insurance.servlet;

import com.insurance.dao.CustomerDAO;
import com.insurance.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        
        try {
            if (action == null || action.equals("list")) {
                listCustomers(out);
            } else if (action.equals("view")) {
                int customerId = Integer.parseInt(request.getParameter("id"));
                viewCustomer(out, customerId);
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
                addCustomer(request, out);
            } else if (action.equals("update")) {
                updateCustomer(request, out);
            } else if (action.equals("delete")) {
                deleteCustomer(request, out);
            }
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    private void listCustomers(PrintWriter out) {
        out.println("<html><head><title>Customers</title></head><body>");
        out.println("<h1>Insurance Customers</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>City</th></tr>");

        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            out.println("<tr>");
            out.println("<td>" + customer.getCustomerId() + "</td>");
            out.println("<td>" + customer.getFullName() + "</td>");
            out.println("<td>" + customer.getEmail() + "</td>");
            out.println("<td>" + customer.getPhone() + "</td>");
            out.println("<td>" + customer.getCity() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    private void viewCustomer(PrintWriter out, int customerId) {
        Customer customer = customerDAO.getCustomerById(customerId);
        out.println("<html><head><title>Customer Details</title></head><body>");
        
        if (customer != null) {
            out.println("<h1>" + customer.getFullName() + "</h1>");
            out.println("<p><b>Email:</b> " + customer.getEmail() + "</p>");
            out.println("<p><b>Phone:</b> " + customer.getPhone() + "</p>");
            out.println("<p><b>Address:</b> " + customer.getAddress() + "</p>");
            out.println("<p><b>City:</b> " + customer.getCity() + "</p>");
        } else {
            out.println("<p>Customer not found</p>");
        }
        
        out.println("</body></html>");
    }

    private void addCustomer(HttpServletRequest request, PrintWriter out) {
        Customer customer = new Customer();
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));
        customer.setCity(request.getParameter("city"));
        customer.setState(request.getParameter("state"));

        boolean success = customerDAO.addCustomer(customer);
        out.println("<h2>" + (success ? "Customer added successfully!" : "Error adding customer") + "</h2>");
    }

    private void updateCustomer(HttpServletRequest request, PrintWriter out) {
        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));

        boolean success = customerDAO.updateCustomer(customer);
        out.println("<h2>" + (success ? "Customer updated successfully!" : "Error updating customer") + "</h2>");
    }

    private void deleteCustomer(HttpServletRequest request, PrintWriter out) {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        boolean success = customerDAO.deleteCustomer(customerId);
        out.println("<h2>" + (success ? "Customer deleted successfully!" : "Error deleting customer") + "</h2>");
    }
}
