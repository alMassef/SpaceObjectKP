package sample.models;

public class Comets extends SpaceObjects {
    public int speed; // скорость км/ч
    public int periodOfPassageThroughTheSolarSystem; // период прохождения через солнечную систему
    public int orbitLength; //  длинна орбиты

    public Comets(){}

    public Comets(String title, int distanceFromTheEarth, int periodOfPassageThroughTheSolarSystem, int speed) {
        super(title, distanceFromTheEarth);
        this.speed = speed;
        this.periodOfPassageThroughTheSolarSystem = periodOfPassageThroughTheSolarSystem;
        this.orbitLength = speed * 8766 * periodOfPassageThroughTheSolarSystem;
    }

    @Override
    public String getDescription(){
        String speedString = String.valueOf(this.speed);
        String periodOfPassageThroughTheSolarSystemString = String.valueOf(this.periodOfPassageThroughTheSolarSystem);
        String orbitLengthString = String.valueOf(this.orbitLength);
        return String.format("Скорость(км/ч): %s, Период прохождения(лет): %s, Длинна орбиты(км): %s", speedString, periodOfPassageThroughTheSolarSystemString, orbitLengthString);
    }
}
