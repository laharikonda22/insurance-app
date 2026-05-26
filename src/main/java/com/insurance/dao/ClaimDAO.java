package com.insurance.dao;

import com.insurance.model.Claim;
import com.insurance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    public boolean addClaim(Claim claim) {
        String sql = "INSERT INTO claims (policy_id, claim_number, claim_amount, claim_date, claim_description) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, claim.getPolicyId());
            pstmt.setString(2, claim.getClaimNumber());
            pstmt.setBigDecimal(3, claim.getClaimAmount());
            pstmt.setDate(4, new java.sql.Date(claim.getClaimDate().getTime()));
            pstmt.setString(5, claim.getClaimDescription());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Claim getClaimById(int claimId) {
        String sql = "SELECT * FROM claims WHERE claim_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, claimId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToClaim(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM claims";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                claims.add(mapResultSetToClaim(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Claim> getClaimsByPolicyId(int policyId) {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM claims WHERE policy_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, policyId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                claims.add(mapResultSetToClaim(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }

    public boolean approveClaim(int claimId, java.math.BigDecimal approvedAmount) {
        String sql = "UPDATE claims SET status='Approved', approved_amount=?, approval_date=? WHERE claim_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBigDecimal(1, approvedAmount);
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setInt(3, claimId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectClaim(int claimId) {
        String sql = "UPDATE claims SET status='Rejected' WHERE claim_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, claimId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Claim mapResultSetToClaim(ResultSet rs) throws SQLException {
        Claim claim = new Claim();
        claim.setClaimId(rs.getInt("claim_id"));
        claim.setPolicyId(rs.getInt("policy_id"));
        claim.setClaimNumber(rs.getString("claim_number"));
        claim.setClaimAmount(rs.getBigDecimal("claim_amount"));
        claim.setClaimDescription(rs.getString("claim_description"));
        claim.setStatus(rs.getString("status"));
        claim.setApprovedAmount(rs.getBigDecimal("approved_amount"));
        return claim;
    }
}
