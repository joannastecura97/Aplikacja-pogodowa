package sample;

import clock.Updater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import weather.WeatherFromServer;
import weather.WeatherParameters;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeatherController {

    @FXML
    private Tab weather;

    @FXML
    private TextField textFieldTemp;

    @FXML
    private TextField textFieldPressure;

    @FXML
    private TextField textFieldHumidity;

    @FXML
    private TextField textFieldTempMin;

    @FXML
    private TextField textFieldTempMax;

    @FXML
    private TextField textFieldWind;

    @FXML
    private Label labelTime;

    @FXML
    private TextField TextFieldFrequency;

    @FXML
    private Button ButtonSetFrequency;

    @FXML
    private ProgressBar CountDownProgressBar;

    @FXML
    private Label numberOfMeasurments;

    @FXML
    private TextField textFieldFileName;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonLoad;

    @FXML
    private Tab temp;

    @FXML
    private LineChart<?, ?> lineChartTemp;

    @FXML
    private TextField TextFieldMaxTemp;

    @FXML
    private TextField TextFieldMinTemp;

    @FXML
    private TextField TextFieldStandardDeviationTemp;

    @FXML
    private AreaChart<?, ?> areaChartPressure;

    @FXML
    private TextField TexTFieldMaxPress;

    @FXML
    private TextField TexTFieldMinPress;

    @FXML
    private TextField TextFieldStandardDeviationPress;

    @FXML
    private BarChart<?, ?> barChartHumidity;

    @FXML
    private TextField TexTFieldMaxHum;

    @FXML
    private TextField TextFieldMinHum;

    @FXML
    private TextField TextFieldStandardDeviationHum;

    @FXML
    private LineChart<?, ?> lineChartSpeed;

    @FXML
    private TextField TextFieldMaxSpeed;

    @FXML
    private TextField TextFieldMinSpeed;

    @FXML
    private TextField TextFieldStandardDeviationSpeed;

    @FXML
    private TextField textFieldPlace;

    @FXML
    private Button ButtonCheck;

    @FXML
    private Button ButtonStop;

    @FXML
    private Button ButtonStart;

    private ArrayList<String> responseToFile = new ArrayList<>();
    private ArrayList<String> arrayPlaces = new ArrayList<>();
    private WeatherFromServer wfs = new WeatherFromServer();
    private WeatherParameters wp = new WeatherParameters();
    private Progress progress;
    Updater updater = new Updater();

    @FXML
    void ButtonCheckClicked(ActionEvent event) throws InterruptedException {

        updater.setParameters(lineChartTemp,TextFieldMaxTemp,TextFieldMinTemp,TextFieldStandardDeviationTemp,areaChartPressure,TexTFieldMaxPress,TexTFieldMinPress,TextFieldStandardDeviationPress,barChartHumidity,TexTFieldMaxHum,TextFieldMinHum,TextFieldStandardDeviationHum,lineChartSpeed,TextFieldMaxSpeed,TextFieldMinSpeed,TextFieldStandardDeviationSpeed,textFieldPlace,numberOfMeasurments,textFieldFileName,textFieldTemp,textFieldPressure,textFieldHumidity,textFieldTempMin,textFieldTempMax,textFieldWind,labelTime,TextFieldFrequency);
        updater.start();
    }

    @FXML
    void ButtonSaveClicked(ActionEvent event) {


        WeatherFromServer wfs = new WeatherFromServer();
        wfs.toFile(textFieldFileName.getText(), responseToFile,wp);

    }

    @FXML
    void ButtonSetFrequencyClicked(ActionEvent event) throws InterruptedException {

        Progress progress = new Progress();
        progress.setInterval(TextFieldFrequency.getText());
        ButtonCheckClicked(new ActionEvent());

    }

    @FXML
    void ButtonStopClicked(ActionEvent event) throws InterruptedException {
        updater.stop();
    }

    @FXML
    void ButtonStartClicked(ActionEvent event) {
        updater.start();

    }

    @FXML
    void ButtonLoadClicked(ActionEvent event) throws FileNotFoundException {
        progress.thread.cancel();
        textFieldPlace.setText("");
        textFieldTemp.setText("");
        textFieldPressure.setText("");
        textFieldHumidity.setText("");
        textFieldTempMin.setText("");
        textFieldTempMax.setText("");
        textFieldWind.setText("");

        responseToFile.clear();
        lineChartTemp.getData().clear();
        lineChartSpeed.getData().clear();
        areaChartPressure.getData().clear();
        barChartHumidity.getData().clear();
        wp.getArrayTemp().clear();
        wp.getArrayHumidity().clear();
        wp.getArrayWindSpeed().clear();
        wp.getArrayPressure().clear();
        wp.getArrayTime().clear();
        arrayPlaces.clear();
        arrayPlaces.add(textFieldPlace.getText());
        WeatherFromServer wfs = new WeatherFromServer();
        ArrayList<String> jsonResponse=wfs.fromFile(textFieldFileName.getText());
        wfs.readFromFile(jsonResponse,wp);

        lineChartTemp.setLegendVisible(false);
        XYChart.Series series = wp.getValuesTemp();
        lineChartTemp.getData().addAll(series);
        TextFieldMaxTemp.setText(wp.maxValue(wp.getArrayTemp()));
        TextFieldMinTemp.setText(wp.minValue(wp.getArrayTemp()));
        TextFieldStandardDeviationTemp.setText(wp.standardDeviation(wp.getArrayTemp()));

        areaChartPressure.getData().clear();
        areaChartPressure.setLegendVisible(false);
        XYChart.Series series1 = wp.getValuesPress();
        areaChartPressure.getData().addAll(series1);
        TexTFieldMaxPress.setText(wp.maxValue(wp.getArrayPressure()));
        TexTFieldMinPress.setText(wp.minValue(wp.getArrayPressure()));
        TextFieldStandardDeviationPress.setText(wp.standardDeviation(wp.getArrayPressure()));

        barChartHumidity.getData().clear();
        barChartHumidity.setLegendVisible(false);
        XYChart.Series series2 = wp.getValuesHum();
        barChartHumidity.getData().addAll(series2);
        TexTFieldMaxHum.setText(wp.maxValue(wp.getArrayHumidity()));
        TextFieldMinHum.setText(wp.minValue(wp.getArrayHumidity()));
        TextFieldStandardDeviationHum.setText(wp.standardDeviation(wp.getArrayHumidity()));

        lineChartSpeed.getData().clear();
        lineChartSpeed.setLegendVisible(false);
        XYChart.Series series3 = wp.getValuesSpeed();
        lineChartSpeed.getData().addAll(series3);
        TextFieldMaxSpeed.setText(wp.maxValue(wp.getArrayWindSpeed()));
        TextFieldMinSpeed.setText(wp.minValue(wp.getArrayWindSpeed()));
        TextFieldStandardDeviationSpeed.setText(wp.standardDeviation(wp.getArrayWindSpeed()));


    }
}






