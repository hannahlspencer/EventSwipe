package eventswipe.models;

public class Student {

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String stuNumber;
    private Integer id;
    private String firstName;
    private String lastName;

}
