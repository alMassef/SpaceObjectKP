package sample.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

public class Stars extends SpaceObjects {
    public float density; // плотность г/см³
    public enum Color {red, yellow, blue, white;} // какие цвета бывают
    public Color color; // цвет
    public int temperature; // температура
    public float lightFlightTime; // время полета света мин
    final int c = 1079252848 / 60; // скорость света км/ч


    public Stars(){}

    public Stars(String title, int distanceFromTheEarth, float density, Color color, int temperature) {
        super(title, distanceFromTheEarth);
        this.density = density;
        this.color = color;
        this.temperature = temperature;
        this.lightFlightTime = (float) (distanceFromTheEarth / c);
    }

    @Override
    public String getDescription(){
        String densityString = String.valueOf(this.density);
        String colorString = "";
        switch (this.color)
        {
            case red:
                colorString = "красный";
                break;
            case yellow:
                colorString = "желтый";
                break;
            case blue:
                colorString = "синий";
                break;
            case white:
                colorString = "белый";
                break;
        }
        String temperatureString = String.valueOf(this.temperature);
        String lightFlightTimeString = String.valueOf(this.lightFlightTime);
        return String.format("Плотность(г/см³): %s, Цвет: %s, Температура(C): %s, Время полета света(мин): %s", densityString, colorString, temperatureString, lightFlightTimeString);
    }
}
