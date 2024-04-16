package devdojo.jdbc.dominio;

public class Producer {

    private Integer id;
    private String name;

    public Producer(String name) {
        this.name = name;
    }

    public Producer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Producer = " +
                " [ id = " + id +
                " name = '" + name + '\'' +
                ']';
    }
}
