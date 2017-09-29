package eventswipe.models;

/**
 * Contains the data for a student attendee at an event.
 *
 * @author Matt Wildman http://bitbucket.org/mattwildman
 */
public class Student {

    /**
     * Returns the student number of the Student.
     * <p>
     * This is an institution-specific identifier which can be read from
     * student cards. It is not necessarily a number.
     * 
     * @return The student number of the Student
     */
    public String getStuNumber() {
        return stuNumber;
    }

    /**
     * Sets the student number of the Student.
     * <p>
     * This is an institution-specific identifier which can be read from
     * student cards. It is not necessarily a number.
     *
     * @param stuNumber The student number
     */
    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    /**
     * @return The unique identifier of the Student in the booking system
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the Student in the booking system.
     *
     * @param id The unique id of the Student
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The first name of the Student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the Student.
     *
     * @param firstName The first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The last name of the Student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the Student.
     *
     * @param lastName The last name
     */
    public void setLastName(String lastName) {
          this.lastName = lastName;
    }

    private String stuNumber;
    private Integer id;
    private String firstName;
    private String lastName = "";

}
