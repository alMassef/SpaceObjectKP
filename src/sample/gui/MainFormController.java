package sample.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable{
    public TableView mainTable;
    public ComboBox cmbSpaceObjectType;
    SpaceObjectModel spaceObjectsModel = new SpaceObjectModel(); // создали экземпляр класса модели

    // список с возможным набором значений
    ObservableList<Class<? extends SpaceObjects>> spaceObjectTypes = FXCollections.observableArrayList(
            SpaceObjects.class,
            Planets.class,
            Stars.class,
            Comets.class
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // добавляем реакцию на изменения данных
        spaceObjectsModel.addDataChangedListener(spaceObjects -> {
            mainTable.setItems(FXCollections.observableArrayList(spaceObjects));
        });

        spaceObjectsModel.load(); // добавляем вызов метода загрузить данные

        // создаем столбец, указываем что столбец преобразует SpaceObjects в String,
        // указываем заголовок колонки как "Название"
        TableColumn<SpaceObjects, String> titleColumn = new TableColumn<>("Название");
        // указываем какое поле брать у модели SpaceObjects, в данном случае title
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // столбец для Удалённость от земли
        TableColumn<SpaceObjects, Integer> distanceFromTheEarthColumn = new TableColumn<>("Удалённость от земли, км");
        distanceFromTheEarthColumn.setCellValueFactory(new PropertyValueFactory<>("distanceFromTheEarth"));

        // столбец для Описания объекта
        TableColumn<SpaceObjects, String> descriptionColumn = new TableColumn<>("Описание");
        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });



        // подцепляем столбцы к таблице
        mainTable.getColumns().addAll(titleColumn, distanceFromTheEarthColumn, descriptionColumn);

        // привязали список
        cmbSpaceObjectType.setItems(spaceObjectTypes);
        // выбрали первый элемент в списке
        cmbSpaceObjectType.getSelectionModel().select(0);

        // переопределил метод преобразования имени класса в текст
        cmbSpaceObjectType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                // перебираем все возможные варианты
                if (SpaceObjects.class.equals(object)) {
                    return "Все";
                } else if (Planets.class.equals(object)) {
                    return "Планеты";
                } else if (Stars.class.equals(object)) {
                    return "Звезды";
                } else if (Comets.class.equals(object)) {
                    return "Кометы";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });

        // реакция на переключения выбранного значения
        cmbSpaceObjectType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.spaceObjectsModel.setSpaceObjectFilter((Class<? extends SpaceObjects>) newValue);
        });
    }

    // кнопка добавления
    public void onAddClick(ActionEvent actionEvent) throws IOException {
        // эти три строчки создюат форму из fxml файлика
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SpaceObjectsForm.fxml"));
        Parent root = loader.load();

        // тут создаем новое окно
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL); // указываем что оно модальное
        stage.initOwner(this.mainTable.getScene().getWindow());

        // вытаскиваем контроллер который привязан к форме
        SpaceObjectsFormController controller = loader.getController();
        // передаем модель
        controller.spaceObjectsModel = spaceObjectsModel;

        // открываем окно и ждем пока его закроют
        stage.showAndWait();
    }

    // кнопка редактирование
    public void onEditClick(ActionEvent actionEvent) throws IOException {
        // эти три строчки создюат форму из fxml файлика
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SpaceObjectsForm.fxml"));
        Parent root = loader.load();

        // тут создаем новое окно
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL); // указываем что оно модальное
        stage.initOwner(this.mainTable.getScene().getWindow());

        // передаем выбранный объект
        SpaceObjectsFormController controller = loader.getController();
        controller.setSpaceObjects((SpaceObjects) this.mainTable.getSelectionModel().getSelectedItem());
        controller.spaceObjectsModel = spaceObjectsModel; // передаем модель в контроллер

        // открываем окно и ждем пока его закроют
        stage.showAndWait();
    }

    // кнопка удаление
    public void onDeleteClick(ActionEvent actionEvent) {
        // берем выбранный на форме объект
        SpaceObjects spaceObjects = (SpaceObjects) this.mainTable.getSelectionModel().getSelectedItem();

        // выдаем подтверждающее сообщение
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(String.format("Точно удалить %s?", spaceObjects.getTitle()));

        // если пользователь нажал OK
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            // удаляем строку из таблицы
            spaceObjectsModel.delete(spaceObjects.id);
        }
    }
}
