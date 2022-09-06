package request;

import java.io.Serializable;

/**
 * @author ansonglin
 */
public class Test1Request implements Serializable {

    private static final long serialVersionUID = 5657637470584412831L;
    String name;

    Integer age;

    public Test1Request(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Test1Request{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
