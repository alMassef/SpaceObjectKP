package sample.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class SpaceObjectModel {
    ArrayList<SpaceObjects> spaceObjectsList = new ArrayList<>();
    private int counter = 1; // добавили счетчик

    // поле фильтр, по умолчанию используем базовый класс
    Class<? extends SpaceObjects> spaceObjectsFilter = SpaceObjects.class;

    // создаем функциональный интерфейс
    // с помощью него мы организуем общение между моделью и контроллером
    public interface DataChangedListener {
        void dataChanged(ArrayList<SpaceObjects> spaceObjectsList);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();

    // добавляем метод который позволяет привязать слушателя
    public void addDataChangedListener(DataChangedListener listener) {
        // добавляем его в список
        this.dataChangedListeners.add(listener);
    }

    // метод, который будет имитировать загрузку данных
    public void load(){
        spaceObjectsList.clear();

        String img1 = "https://look.com.ua/pic/201209/240x320/look.com.ua-31701.jpg";
        String img2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEXG6V6ROWzx5ElgFU8o4S_sTwhshhlaNDxQ&usqp=CAU";
        String img3 = "https://avatars.mds.yandex.net/get-zen_doc/108057/pub_5c137d7a86fd9e00aa0a8a2a_5c138418dab7be00aa983682/scale_1200";

        this.add(new Planets(img1,"Земля", 0, 6371, true, 597200000), false);
        this.add(new Stars(img2,"Солнце", 149500000, 1.41f, Stars.Color.yellow, 15700000), false);
        this.add(new Comets(img3,"Галлея", 859750570, 75, 252000), false);

        this.emitDataChanged();
    }

    // метод добавления с двумя параметрами
    public void add(SpaceObjects spaceObjects, boolean emit) {
        spaceObjects.id = counter;
        counter += 1;
        this.spaceObjectsList.add(spaceObjects);
        if (emit) {
            this.emitDataChanged();
        }
    }

    // метод добавления с одним параметром который вызывает add с двумя параметрами
    public void add(SpaceObjects spaceObjects) {
        add(spaceObjects, true);
    }

    // метод для редактирования
    public void edit(SpaceObjects spaceObjects) {
        // ищем объект в списке
        for (int i = 0; i < this.spaceObjectsList.size(); ++i) {
            // чтобы id совпадал с id переданным форме
            if (this.spaceObjectsList.get(i).id == spaceObjects.id) {
                // если нашли, то подменяем объект
                this.spaceObjectsList.set(i, spaceObjects);
                break;
            }
        }
        this.emitDataChanged();
    }

    // метод для удаления
    public void delete(int id)
    {
        for (int i = 0; i < this.spaceObjectsList.size(); ++i) {
            if (this.spaceObjectsList.get(i).id == id) {
                this.spaceObjectsList.remove(i);
                break;
            }
        }
        this.emitDataChanged();
    }

    // метод для смены фильтра
    public void setSpaceObjectFilter(Class<? extends SpaceObjects> spaceObjectsFilter) {
        this.spaceObjectsFilter = spaceObjectsFilter;
        this.emitDataChanged();
    }

    // метод который оповещает всех слушателей что данные изменились
    private void emitDataChanged() {
        for (DataChangedListener listener: dataChangedListeners) {
            ArrayList<SpaceObjects> filteredList = new ArrayList<>(
                    spaceObjectsList.stream() // запускаем стрим
                            .filter(spaceObject -> spaceObjectsFilter.isInstance(spaceObject)) // фильтруем по типу
                            .collect(Collectors.toList()) // превращаем в список
            );
            listener.dataChanged(filteredList);
        }
    }

    // метод который сохраняет данные в файл
    public void saveToFile(String path) {
        // открываем файл для чтения
        try (Writer writer =  new FileWriter(path)) {
            // создаем сериализатор
            ObjectMapper mapper = new ObjectMapper();

            mapper.writerFor(new TypeReference<ArrayList<SpaceObjects>>() { }) // указали какой тип ставим
                    .withDefaultPrettyPrinter() // чтобы в файле все красиво печаталось
                    .writeValue(writer, spaceObjectsList); // записываем данные списка в файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод в модели для чтения из файла
    public void loadFromFile(String path){
        try (Reader reader =  new FileReader(path)) {
            // создаем сериализатор
            ObjectMapper mapper = new ObjectMapper();

            // читаем из файла
            spaceObjectsList = mapper.readerFor(new TypeReference<ArrayList<SpaceObjects>>() { })
                    .readValue(reader);

            this.counter = spaceObjectsList.stream()
                    .map(spaceObject -> spaceObject.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // оповещаем что данные загрузились
        this.emitDataChanged();
    }
}
