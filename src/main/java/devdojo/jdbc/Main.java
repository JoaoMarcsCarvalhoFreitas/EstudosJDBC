package devdojo.jdbc;

import devdojo.jdbc.dominio.Producer;
import devdojo.jdbc.service.ProducerService;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Producer producer = new Producer("MAPPA");
        //ProducerService.save(producer);
//        ProducerService.delete(4);
        //ProducerService.update("Ufotable", 2);

        //List<Producer> pr = ProducerService.findAll();

//        List<Producer> pr = ProducerService.findByName("MAP");
//
//        for (Producer produce : pr) {
//            System.out.println(produce);
//        }

        //ProducerService.showTypeScrollWorking();

        //ProducerService.findByNameUpdtade("Ufotable");
//        List<Producer> producers = ProducerService.findByNameOrInsertNotFound("Nicklodeon");
//        System.out.println(producers);

//        ProducerService.findByNameAndDelete("Nicklodeon");

        List<Producer> producers = ProducerService.callableStatement("Cartoon");
        System.out.println(producers);
    }
}