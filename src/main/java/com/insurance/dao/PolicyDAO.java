package com.insurance.dao;

import com.insurance.model.Policy;
import com.insurance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyDAO {

    public boolean addPolicy(Policy policy) {
        String sql = "INSERT INTO policies (customer_id, policy_number, policy_type, coverage_amount, premium_amount, start_date, end_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, policy.getCustomerId());
            pstmt.setString(2, policy.getPolicyNumber());
            pstmt.setString(3, policy.getPolicyType());
            pstmt.setBigDecimal(4, policy.getCoverageAmount());
            pstmt.setBigDecimal(5, policy.getPremiumAmount());
            pstmt.setDate(6, new java.sql.Date(policy.getStartDate().getTime()));
            pstmt.setDate(7, new java.sql.Date(policy.getEndDate().getTime()));
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Policy getPolicyById(int policyId) {
        String sql = "SELECT * FROM policies WHERE policy_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, policyId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPolicy(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<>();
        String sql = "SELECT * FROM policies";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                policies.add(mapResultSetToPolicy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policies;
    }

    public List<Policy> getPoliciesByCustomerId(int customerId) {
        List<Policy> policies = new ArrayList<>();
        String sql = "SELECT * FROM policies WHERE customer_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                policies.add(mapResultSetToPolicy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policies;
    }

    public boolean updatePolicy(Policy policy) {
        String sql = "UPDATE policies SET policy_number=?, policy_type=?, coverage_amount=?, premium_amount=?, status=? WHERE policy_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, policy.getPolicyNumber());
            pstmt.setString(2, policy.getPolicyType());
            pstmt.setBigDecimal(3, policy.getCoverageAmount());
            pstmt.setBigDecimal(4, policy.getPremiumAmount());
            pstmt.setString(5, policy.getStatus());
            pstmt.setInt(6, policy.getPolicyId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Policy mapResultSetToPolicy(ResultSet rs) throws SQLException {
        Policy policy = new Policy();
        policy.setPolicyId(rs.getInt("policy_id"));
        policy.setCustomerId(rs.getInt("customer_id"));
        policy.setPolicyNumber(rs.getString("policy_number"));
        policy.setPolicyType(rs.getString("policy_type"));
        policy.setCoverageAmount(rs.getBigDecimal("coverage_amount"));
        policy.setPremiumAmount(rs.getBigDecimal("premium_amount"));
        policy.setStatus(rs.getString("status"));
        return policy;
    }
}
