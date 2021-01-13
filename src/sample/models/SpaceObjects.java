package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"description"}) // указали что свойство description нужно игнорировать
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")

public class SpaceObjects {
    public String title; // название
    private int distanceFromTheEarth; // удалённость от земли
    public Integer id = null; // идентификатор

    private ImageView image;
    private String url;

    public SpaceObjects(){}

    public SpaceObjects(String image, String title, int distanceFromTheEarth){
        this.setImage(image);
        this.setTitle(title);
        this.setDistanceFromTheEarth(distanceFromTheEarth);
        this.setUrl(image);
    }

    @Override
    public String toString(){
        return String.format("%s: %s км", this.getTitle(), this.getDistanceFromTheEarth());
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(String url) {
        Image images = new Image(url);
        this.image = new ImageView(images);
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
