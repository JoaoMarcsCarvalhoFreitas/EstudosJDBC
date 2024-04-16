package devdojo.jdbc;

import devdojo.jdbc.dominio.Producer;
import devdojo.jdbc.service.ProducerService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Producer producer = new Producer("MAPPA");
        //ProducerService.save(producer);
        //ProducerService.delete(1);
        //ProducerService.update("Ufotable", 2);

        //List<Producer> pr = ProducerService.findAll();

        List<Producer> pr = ProducerService.findByName("MAP");

        for (Producer produce : pr) {
            System.out.println(produce);
        }

    }
}