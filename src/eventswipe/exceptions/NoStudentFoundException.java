package eventswipe.exceptions;

public class NoStudentFoundException extends RuntimeException {

    public NoStudentFoundException(String stuNum) {
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
