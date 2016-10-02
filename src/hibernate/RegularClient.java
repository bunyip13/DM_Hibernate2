package hibernate;

import javax.persistence.Entity;

@Entity
public class RegularClient extends Client {
    private String name;

    public RegularClient() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
