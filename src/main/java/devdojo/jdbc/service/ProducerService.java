package devdojo.jdbc.service;

import devdojo.jdbc.dominio.Producer;
import devdojo.jdbc.repository.ProcuderRepository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProducerService {

    public static void save(Producer producer) {
        ProcuderRepository.save(producer);
    }

    public static void delete(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Value for ID is invalid");
        } else {
            ProcuderRepository.delete(id);
        }
    }

    public static List<Producer> findAll() {
        return ProcuderRepository.findAll();
    }

    public static List<Producer> findByName(String name) {
        return ProcuderRepository.findByName(name);
    }

    public static void update(String name, int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Value for ID invalid");
        } else {
            ProcuderRepository.update(name, id);
        }
    }
}
