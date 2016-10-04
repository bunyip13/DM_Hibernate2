package hibernate;

//import javax.persistence.Entity;

import javax.persistence.*;

@Entity
public class RegularClient extends Client {
    private String pet;
    private Integer age;

    public RegularClient() {
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
