package eventswipe;

public class Event {

    public Event(String title, String date, String id) {
        this.title = title;
        this.startDate = date;
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String startDate;
    private String title;
    private String id;

}
