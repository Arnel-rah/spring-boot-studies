package mg.arnel.spring_studies;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final DbConnection dbConnection;

    public OrderService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void findAll() {
        String sql = "SELECT o.id, u.name AS client, o.total, o.status, o.created_at " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "ORDER BY o.created_at DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID      : " + rs.getInt("id"));
                System.out.println("Client  : " + rs.getString("client"));
                System.out.println("Total   : " + rs.getDouble("total") + " €");
                System.out.println("Status  : " + rs.getString("status"));
                System.out.println("Date    : " + rs.getTimestamp("created_at"));
                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Erreur findAll orders : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void findById(int orderId) {
        String sql = "SELECT o.id, u.name AS client, o.total, o.status, o.created_at " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "WHERE o.id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("===== COMMANDE #" + orderId + " =====");
                    System.out.println("Client  : " + rs.getString("client"));
                    System.out.println("Total   : " + rs.getDouble("total") + " €");
                    System.out.println("Status  : " + rs.getString("status"));
                    System.out.println("Date    : " + rs.getTimestamp("created_at"));
                } else {
                    System.out.println("Commande #" + orderId + " introuvable.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur findById order : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void findByStatus(String status) {
        String sql = "SELECT o.id, u.name AS client, o.total, o.status " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "WHERE o.status = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toUpperCase());

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("===== COMMANDES [" + status.toUpperCase() + "] =====");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                            " | Client: " + rs.getString("client") +
                            " | Total: " + rs.getDouble("total") + " €");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur findByStatus : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void findOrderItems(int orderId) {
        String sql = "SELECT p.name AS produit, oi.quantity, oi.unit_price, " +
                "(oi.quantity * oi.unit_price) AS sous_total " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id " +
                "WHERE oi.order_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("===== DETAILS COMMANDE #" + orderId + " =====");
                while (rs.next()) {
                    System.out.println("Produit    : " + rs.getString("produit"));
                    System.out.println("Quantite   : " + rs.getInt("quantity"));
                    System.out.println("Prix unit. : " + rs.getDouble("unit_price") + " €");
                    System.out.println("Sous-total : " + rs.getDouble("sous_total") + " €");
                    System.out.println("-------------------------------");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur findOrderItems : " + e.getMessage());
            e.printStackTrace();
        }
    }
}