package devdojo.jdbc.repository;

import devdojo.jdbc.ConnectionFactory;
import devdojo.jdbc.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProcuderRepository {

    public static void save(Producer producer) {

        String sql = "INSERT INTO Producer(nameProducer) VALUES ('%s')".formatted(producer.getName());

        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {

            Integer linhas = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void delete(int id) {

        String sql = "DELETE FROM Producer WHERE (idProducer = '%d');".formatted(id);

        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {

            Integer linhas = stmt.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public static void update(String name, int id) {

        String sql = ("UPDATE anime_store.Producer SET nameProducer = '%s' " +
                "WHERE idProducer = '%d'").formatted(name, id);

        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Producer> findAll() {
        return findByName("");
//        List<Producer> producers = new ArrayList<>();
//        String sql = "SELECT * FROM anime_store.Producer;";
//
//        try (Connection conn = ConnectionFactory.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql);) {
//
//            while (rs.next()) {
//                producers.add(new Producer(rs.getInt("idProducer"),
//                        rs.getString("nameProducer")));
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return producers;
    }

    public static List<Producer> findByName(String name) {

        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM anime_store.Producer WHERE nameProducer LIKE '%%%s%%';".formatted(name);

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                producers.add(new Producer(rs.getInt("idProducer"),
                        rs.getString("nameProducer")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producers;
    }
}
