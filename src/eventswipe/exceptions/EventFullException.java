package eventswipe.exceptions;

public class EventFullException extends RuntimeException {

    public EventFullException(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    private String stuNum;

}
