package clock;

import javafx.application.Platform;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Progress;
import weather.WeatherFromServer;
import weather.WeatherParameters;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Updater implements Runnable, javaFXUpdater {

    private Progress progress;
    private WeatherFromServer wfs = new WeatherFromServer();;
    private ArrayList<String> responseToFile = new ArrayList<>();
    private ArrayList<String> arrayPlaces = new ArrayList<>();
    private WeatherParameters wp = new WeatherParameters();

    private LineChart<?, ?> lineChartTemp;

    private TextField TextFieldMaxTemp;

    private TextField TextFieldMinTemp;

        private TextField TextFieldStandardDeviationTemp;


    private AreaChart<?, ?> areaChartPressure;

    private TextField TexTFieldMaxPress;

    private TextField TexTFieldMinPress;

    private TextField TextFieldStandardDeviationPress;

    private BarChart<?, ?> barChartHumidity;

    private TextField TexTFieldMaxHum;

    private TextField TextFieldMinHum;

    private TextField TextFieldStandardDeviationHum;

    private LineChart<?, ?> lineChartSpeed;

    private TextField TextFieldMaxSpeed;

    private TextField TextFieldMinSpeed;

    private TextField TextFieldStandardDeviationSpeed;

    private TextField textFieldPlace;

    private Label numberOfMeasurments;

    private TextField textFieldFileName;

    private TextField textFieldTemp;

    private TextField textFieldPressure;

    private TextField textFieldHumidity;

    private TextField textFieldTempMin;

    private TextField textFieldTempMax;

    private TextField textFieldWind;

    private Label labelTime;

    private TextField TextFieldFrequency;

    private Thread thread;

    private boolean isRunning=false;

    public void start() {
        thread = new Thread(this, "built-in timer");
        thread.start();
    }

    public void stop(){
        isRunning = false;
    }


    @Override
    public void run() {
        isRunning = true;

        while(isRunning){
            try{
                updateFxObjects();

                Thread.sleep(Integer.parseInt(TextFieldFrequency.getText())*60*1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setParameters(LineChart<?, ?> lineChartTemp, TextField textFieldMaxTemp, TextField textFieldMinTemp, TextField textFieldStandardDeviationTemp, AreaChart<?, ?> areaChartPressure, TextField texTFieldMaxPress, TextField texTFieldMinPress, TextField textFieldStandardDeviationPress, BarChart<?, ?> barChartHumidity, TextField texTFieldMaxHum, TextField textFieldMinHum, TextField textFieldStandardDeviationHum, LineChart<?, ?> lineChartSpeed, TextField textFieldMaxSpeed, TextField textFieldMinSpeed, TextField textFieldStandardDeviationSpeed, TextField textFieldPlace, Label numberOfMeasurments, TextField textFieldFileName, TextField textFieldTemp, TextField textFieldPressure, TextField textFieldHumidity, TextField textFieldTempMin, TextField textFieldTempMax, TextField textFieldWind, Label labelTime, TextField textFieldFrequency) {
        this.lineChartTemp = lineChartTemp;
        this.TextFieldMaxTemp = textFieldMaxTemp;
        this.TextFieldMinTemp = textFieldMinTemp;
        this.TextFieldStandardDeviationTemp = textFieldStandardDeviationTemp;
        this.areaChartPressure = areaChartPressure;
        this.TexTFieldMaxPress = texTFieldMaxPress;
        this.TexTFieldMinPress = texTFieldMinPress;
        this.TextFieldStandardDeviationPress = textFieldStandardDeviationPress;
        this.barChartHumidity = barChartHumidity;
        this.TexTFieldMaxHum = texTFieldMaxHum;
        this.TextFieldMinHum = textFieldMinHum;
        this.TextFieldStandardDeviationHum = textFieldStandardDeviationHum;
        this.lineChartSpeed = lineChartSpeed;
        this.TextFieldMaxSpeed = textFieldMaxSpeed;
        this.TextFieldMinSpeed = textFieldMinSpeed;
        this.TextFieldStandardDeviationSpeed = textFieldStandardDeviationSpeed;
        this.textFieldPlace = textFieldPlace;
        this.numberOfMeasurments = numberOfMeasurments;
        this.textFieldFileName = textFieldFileName;
        this.textFieldTemp = textFieldTemp;
        this.textFieldPressure = textFieldPressure;
        this.textFieldHumidity = textFieldHumidity;
        this.textFieldTempMin = textFieldTempMin;
        this.textFieldTempMax = textFieldTempMax;
        this.textFieldWind = textFieldWind;
        this.labelTime = labelTime;
        this.TextFieldFrequency = textFieldFrequency;
    }

    @Override
    public void initUpdate() {
        progress = new Progress(TextFieldFrequency.getText());
        progress.setInterval(TextFieldFrequency.getText());

        String response = wfs.serverResponse(textFieldPlace.getText());

        arrayPlaces.add(textFieldPlace.getText());

        if (arrayPlaces.size() > 1) {
            if (!arrayPlaces.get(arrayPlaces.size() - 2).equals(arrayPlaces.get(arrayPlaces.size() - 1))) {
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

            }

        }

        //arrayPlaces.add(textFieldPlace.getText());
        numberOfMeasurments.setText("Liczba pomiarów: " + arrayPlaces.size());
        responseToFile.add(response);


        textFieldTemp.setText(wp.getTemp(response) + " \u00b0C");
        textFieldPressure.setText(wp.getPressure(response) + " hPa");
        textFieldHumidity.setText(wp.getHumidity(response) + " %");
        textFieldTempMin.setText(wp.getTempMin(response) + " \u00b0C");
        textFieldTempMax.setText(wp.getTempMax(response) + " \u00b0C");
        textFieldWind.setText(wp.getWindSpeed(response) + " km/h");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());
        wp.addArrayTime(time.toString());

        lineChartTemp.getData().clear();
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


        labelTime.setText(date +"  "+time);
        progress.setInterval(TextFieldFrequency.getText());
}
}
