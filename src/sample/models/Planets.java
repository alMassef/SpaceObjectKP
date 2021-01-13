package sample.models;

import javafx.scene.image.ImageView;

public class Planets extends SpaceObjects {
    public int radius; // радиус км
    public int m; // масса кг
    public Boolean presenceOfAtmosphere; // наличие атмосферы
    public float g; // сила притяжения    G=6,67⋅10^−11    g = G * M / r^2
    final float G = (float) (6.67f * Math.pow(10, -11));



    public Planets(){}

    public Planets(String image, String title, int distanceFromTheEarth, int radius, Boolean presenceOfAtmosphere, int m) {
        super(image, title, distanceFromTheEarth);
        this.radius = radius;
        this.presenceOfAtmosphere = presenceOfAtmosphere;
        this.m = m;
        this.g = (float) (G * m / Math.pow(radius, 2));
    }

    @Override
    public String getDescription(){
        String radiusString = String.valueOf(this.radius);
        String presenceOfAtmosphereString = this.presenceOfAtmosphere ? "есть" : "нет";
        String mString = String.valueOf(this.m);
        String gString = String.valueOf(this.g);
        return String.format("Радиус(км): %s, Атмосфера: %s, Масса(кг): %s, Cила притяжения(G): %s", radiusString, presenceOfAtmosphereString, mString, gString);
    }
}
