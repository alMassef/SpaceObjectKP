package sample.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SpaceObjectsFormController implements Initializable {
    @FXML
    private ChoiceBox cmbSpaceObjectType;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDistanceFromTheEarth;

    @FXML
    private VBox planetPane;
    @FXML
    private TextField txtRadius;
    @FXML
    private CheckBox chkPresenceOfAtmosphere;
    @FXML
    private TextField txtM;

    @FXML
    private VBox starPane;
    @FXML
    private TextField txtDensity;
    @FXML
    private ChoiceBox cmbColor;
    @FXML
    private TextField txtTemperature;

    @FXML
    private VBox cometPane;
    @FXML
    private TextField txtPeriodOfPassageThroughTheSolarSystem;
    @FXML
    private TextField txtSpeed;

    public SpaceObjectModel spaceObjectsModel;
    private Integer id = null; // добавили поле под идентификатор

    final String SPACE_OBJECT_PLANET = "Планета";
    final String SPACE_OBJECT_STAR = "Звезда";
    final String SPACE_OBJECT_COMET = "Комета";

    @Override
    public void initialize(URL location, ResourceBundle resources){
        cmbSpaceObjectType.setItems(FXCollections.observableArrayList(
                SPACE_OBJECT_PLANET,
                SPACE_OBJECT_STAR,
                SPACE_OBJECT_COMET
        ));

        cmbSpaceObjectType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updatePanes((String) newValue);
        });

        // добавляем все цвета звезд
        cmbColor.getItems().setAll(
                Stars.Color.red,
                Stars.Color.yellow,
                Stars.Color.blue,
                Stars.Color.white
        );

        // используем метод setConverter, чтобы типы объекты рендерились как строки
        cmbColor.setConverter(new StringConverter<Stars.Color>(){
            @Override
            public String toString(Stars.Color object) {
                // указываем как рендерить
                switch (object) {
                    case red:
                        return "красный";
                    case yellow:
                        return "желтый";
                    case blue:
                        return "синий";
                    case white:
                        return "белый";
                }
                return null;
            }

            @Override
            public Stars.Color fromString(String string) {
                // этот метод не трогаем так как наш комбобкос имеет фиксированный набор элементов
                return null;
            }
        });
        updatePanes("");
    }

    // метод чтобы отображать или прятать ту или иную панель в зависимости от выбранного объекта
    public void updatePanes(String value){
        this.planetPane.setVisible(value.equals(SPACE_OBJECT_PLANET));
        this.planetPane.setManaged(value.equals(SPACE_OBJECT_PLANET));
        this.starPane.setVisible(value.equals(SPACE_OBJECT_STAR));
        this.starPane.setManaged(value.equals(SPACE_OBJECT_STAR));
        this.cometPane.setVisible(value.equals(SPACE_OBJECT_COMET));
        this.cometPane.setManaged(value.equals(SPACE_OBJECT_COMET));
    }

    // метод, который будет возвращать новый объект на основании данных собранных с формы
    public SpaceObjects getSpaceObjects(){
        SpaceObjects result = null;

        String title = this.txtTitle.getText();
        int distanceFromTheEarth = Integer.parseInt(this.txtDistanceFromTheEarth.getText());

        switch ((String)this.cmbSpaceObjectType.getValue()){
            case SPACE_OBJECT_PLANET:
                int radius = Integer.parseInt(this.txtRadius.getText());
                int m = Integer.parseInt(this.txtM.getText());
                result = new Planets(title, distanceFromTheEarth, radius, this.chkPresenceOfAtmosphere.isSelected(), m);
                break;
            case SPACE_OBJECT_STAR:
                float density = Float.parseFloat(this.txtDensity.getText());
                int temperature = Integer.parseInt(this.txtTemperature.getText());
                result = new Stars(title, distanceFromTheEarth, density, (Stars.Color)this.cmbColor.getValue(), temperature);
                break;
            case SPACE_OBJECT_COMET:
                int periodOfPassageThroughTheSolarSystem = Integer.parseInt(this.txtPeriodOfPassageThroughTheSolarSystem.getText());
                int speed = Integer.parseInt(this.txtSpeed.getText());
                result = new Comets(title, distanceFromTheEarth, periodOfPassageThroughTheSolarSystem, speed);
                break;
        }
        return result;
    }

    // метод который позволит передать на форму объект для редактирования
    public void setSpaceObjects(SpaceObjects spaceObjects){
        // делаем так что если объект редактируется, то нельзя переключать тип
        this.cmbSpaceObjectType.setDisable(spaceObjects != null);

        // присвоим значение идентификатора,
        // если передали объект то есть spaceObjects != null, то используем spaceObjects.id
        // иначе запихиваем в this.id значение null
        this.id = spaceObjects != null ? spaceObjects.id : null;

        if (spaceObjects != null){
            // тут стандартное заполнение полей в соответствии с переданным объектом
            this.txtTitle.setText(spaceObjects.getTitle());
            this.txtDistanceFromTheEarth.setText(String.valueOf(spaceObjects.getDistanceFromTheEarth()));

            if (spaceObjects instanceof Planets){ // если планета
                this.cmbSpaceObjectType.setValue(SPACE_OBJECT_PLANET);
                this.txtRadius.setText(String.valueOf(((Planets) spaceObjects).radius));
                this.txtM.setText(String.valueOf(((Planets) spaceObjects).m));
                this.chkPresenceOfAtmosphere.setSelected(((Planets) spaceObjects).presenceOfAtmosphere);
            }
            else if (spaceObjects instanceof Stars){ // если звезда
                this.cmbSpaceObjectType.setValue(SPACE_OBJECT_STAR);
                this.txtDensity.setText(String.valueOf(((Stars) spaceObjects).density));
                this.cmbColor.setValue(((Stars) spaceObjects).color);
                this.txtTemperature.setText(String.valueOf(((Stars) spaceObjects).temperature));
            }
            else if (spaceObjects instanceof Comets){
                this.cmbSpaceObjectType.setValue(SPACE_OBJECT_COMET);
                this.txtPeriodOfPassageThroughTheSolarSystem.setText(String.valueOf(((Comets) spaceObjects).periodOfPassageThroughTheSolarSystem));
                this.txtSpeed.setText(String.valueOf(((Comets) spaceObjects).speed));
            }
        }
    }

    // обработчик нажатия на кнопку Сохранить
    public void onSaveClick(javafx.event.ActionEvent actionEvent) {
        // проверяем передали ли идентификатор
        if (this.id != null) {
            // если передавали значит у нас редактирование
            // собираем объект с формы
            SpaceObjects spaceObjects = getSpaceObjects();
            // подвязываем переданный идентификатор
            spaceObjects.id = this.id;
            // отправляем в модель на изменение
            this.spaceObjectsModel.edit(spaceObjects);
        } else {
            // если у нас добавление, просто добавляем объект
            this.spaceObjectsModel.add(getSpaceObjects());
        }

        // закрываем окно к которому привязана кнопка
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    // обработчик нажатия на кнопку Отмена
    public void onCancelClick(javafx.event.ActionEvent actionEvent) {
        // закрываем окно к которому привязана кнопка
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }
}
