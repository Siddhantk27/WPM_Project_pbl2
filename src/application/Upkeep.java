package application;

import java.time.LocalTime;
import java.util.Date;

public class Upkeep {
    private String name;
    private int upkeepId;
    private String slides;
    private String chlorination;
    private Date date;
    private String startTimeAsString;

    public Upkeep(String name, int upkeepId, String slides, String chlorination, Date date, String startTimeAsString) {
        this.name = name;
        this.upkeepId = upkeepId;
        this.slides = slides;
        this.chlorination = chlorination;
        this.date = date;
        this.startTimeAsString = startTimeAsString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpkeepId() {
        return upkeepId;
    }

    public void setUpkeepId(int upkeepId) {
        this.upkeepId = upkeepId;
    }

    public String getSlides() {
        return slides;
    }

    public void setSlides(String slides) {
        this.slides = slides;
    }

    public String getChlorination() {
        return chlorination;
    }

    public void setChlorination(String chlorination) {
        this.chlorination = chlorination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTimeAsString() {
        return startTimeAsString;
    }

    public void setStartTimeAsString(String startTimeAsString) {
        this.startTimeAsString = startTimeAsString;
    }
}
