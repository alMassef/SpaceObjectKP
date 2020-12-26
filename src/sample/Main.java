package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/MainForm.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

/*
Объекты космоса (название, удалённость от земли)

Планеты (радиус, масса, наличие атмосферы, сила притяжения g - расчитывается) G=6,67⋅10^−11    g = G * M / r^2
Звезды (плотность, температура, цвет, сколько будет лететь свет от этого объекта до земли) скорость света = 1 079 252 848,8 км/ч
Кометы (скорость, период прохождения через солнечную систему, длинна орбиты) длинна орбиты = скорость * периуд прохождения
 */
