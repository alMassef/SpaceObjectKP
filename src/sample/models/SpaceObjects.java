package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"description"}) // указали что свойство description нужно игнорировать
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")

public class SpaceObjects {
    public String title; // название
    private int distanceFromTheEarth; // удалённость от земли
    public Integer id = null; // идентификатор

    public SpaceObjects(){}

    public SpaceObjects(String title, int distanceFromTheEarth){
        this.setTitle(title);
        this.setDistanceFromTheEarth(distanceFromTheEarth);
    }

    @Override
    public String toString(){
        return String.format("%s: %s км", this.getTitle(), this.getDistanceFromTheEarth());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDistanceFromTheEarth() {
        return distanceFromTheEarth;
    }

    public void setDistanceFromTheEarth(int distanceFromTheEarth) {
        this.distanceFromTheEarth = distanceFromTheEarth;
    }

    public String getDescription() {
        return "";
    }
}
