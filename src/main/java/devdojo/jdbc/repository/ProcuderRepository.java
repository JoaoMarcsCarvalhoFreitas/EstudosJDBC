package devdojo.jdbc.repository;

import devdojo.jdbc.ConnectionFactory;
import devdojo.jdbc.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


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


    public static void showTypeScrollWorking(){

        String sql = "SELECT * FROM anime_store.Producer;";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("is last? %s".formatted(rs.last()));
            System.out.println("What row number? %s".formatted(rs.getRow()));

            Producer p = new Producer(rs.getInt("idProducer"), rs.getString("nameProducer"));

            System.out.println(p);
//
//            rs.relative(-1);
//            rs.previous();
//            rs.first();
//            System.out.println(rs.getRow());
//
//            Producer p1 = new Producer(rs.getInt("idProducer"), rs.getString("nameProducer"));
//
//            System.out.println(p1);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static List<Producer> findByNameAndUpdateToUpperCase(String name) {

        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM anime_store.Producer WHERE nameProducer LIKE '%%%s%%';".formatted(name);

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {

                // Aqui ele retorna o conteúdo daquela coluna e altera a String para UpperCase
                rs.updateString("nameProducer", rs.getString("nameProducer").toUpperCase());

                // Atualiza o conteúdo da linha que está apontada no momento
                rs.updateRow();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producers;
    }

    public static List<Producer> findByNameOrInsertNotFound(String name){

        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM anime_store.Producer WHERE nameProducer LIKE '%%%s%%';".formatted(name);


        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.next()){
                rs.moveToInsertRow();
                rs.updateString("nameProducer", name);
                rs.insertRow();

                producers = findByName(name);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return producers;
    }

    public static void findByNameAndDelete(String name){

        String sql = "SELECT * FROM anime_store.Producer WHERE nameProducer LIKE '%%%s%%';".formatted(name);

        try (Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql)){


            if (rs.next()){
                rs.deleteRow();
                System.out.println("Deleting Sucess");
            }


        }catch (SQLException e){
            System.out.println(e.getErrorCode());

        }
    }

    public static void updatePreparedStatement(Producer producer){

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = preparedStatementUpdate(conn, producer)) {

            int rowsAffected = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static PreparedStatement preparedStatementUpdate(Connection conn, Producer producer) throws SQLException{

        String sql = ("UPDATE anime_store.Producer SET nameProducer = '?' WHERE idProducer = '?'");

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        ps.setInt(2, producer.getId());

        return ps;


    }


    // Usar quando chamar funções ou procedures
    public static CallableStatement findByNameCallableStatement(Connection conn, String name) throws SQLException{

        String sql = "CALL `anime_store`.`get_find_name`(?);";

        CallableStatement cs = conn.prepareCall(sql);
        cs.setString(1, name);
        return cs;


    }

    public static List<Producer> findByNameCallableStatement(String name) {

        List<Producer> producers = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = findByNameCallableStatement(conn, name);
             ResultSet rs = ps.executeQuery()) {

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
