package weather;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class WeatherParameters {

    private ArrayList<Double> arrayTemp = new ArrayList<>();
    private ArrayList<Double> arrayPressure = new ArrayList<>();
    private ArrayList<Double> arrayHumidity = new ArrayList<>();
    private ArrayList<Double> arrayWindSpeed = new ArrayList<>();
    private ArrayList<String> arrayTime=new ArrayList<>();

    public void addArrayTime(String time){
        arrayTime.add(time);
    }

    public ArrayList<String> getArrayTime() {

        for (int i = 0 ; i<arrayTime.size();i++){
        }

        return arrayTime;
    }

    public ArrayList<Double> getArrayPressure() {
        return arrayPressure;
    }

    public ArrayList<Double> getArrayHumidity() {
        return arrayHumidity;
    }

    public ArrayList<Double> getArrayWindSpeed() {
        return arrayWindSpeed;
    }

    public ArrayList<Double> getArrayTemp() {
        return arrayTemp;
    }

    public Double getTemp(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        arrayTemp.add(temp);
        return temp;
    }

    public double getPressure(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double pressure = jsonObject.getAsJsonObject("main").get("pressure").getAsDouble();
        arrayPressure.add(pressure);
        return pressure;
    }

    public double getHumidity(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsDouble();
        arrayHumidity.add(humidity);
        return humidity;
    }

    public double getTempMin(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double tempMin = jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble();
        return tempMin;
    }

    public double getTempMax(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double tempMax = jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble();
        return tempMax;
    }

    public double getWindSpeed(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
        arrayWindSpeed.add(windSpeed);
        return windSpeed;
    }

    public double getWindDirection(String jsonString){
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        Double windDirection = jsonObject.getAsJsonObject("wind").get("deg").getAsDouble();
        return windDirection;
    }

    public XYChart.Series  getValuesTemp() {

        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < arrayTime.size(); i++) {
            series1.getData().add(new XYChart.Data(arrayTime.get(i).toString(), arrayTemp.get(i)));
        }
        return series1;
    }

    public XYChart.Series  getValuesPress() {

        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < arrayTime.size(); i++) {
            series1.getData().add(new XYChart.Data(arrayTime.get(i).toString(), arrayPressure.get(i)));
        }
        return series1;
    }

    public XYChart.Series  getValuesHum() {

        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < arrayTime.size(); i++) {
            series1.getData().add(new XYChart.Data(arrayTime.get(i).toString(), arrayHumidity.get(i)));
        }
        return series1;
    }
    public XYChart.Series  getValuesSpeed() {

        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < arrayTime.size(); i++) {
            series1.getData().add(new XYChart.Data(arrayTime.get(i).toString(), arrayWindSpeed.get(i)));
        }
        return series1;
    }

    public String standardDeviation(ArrayList <Double> array){
        double sum=0;
        for (int i = 0; i < array.size(); i++) {
            sum+=array.get(i);
        }
        double average=sum/array.size();

        double sum2=0;

        for (int i = 0; i < array.size(); i++) {
            sum2+=Math.pow((array.get(i)-average),2);
        }
        double sd=sum2/array.size();
        String sdS=""+sd;

        return sdS;
    }

    public String maxValue(ArrayList <Double> array){
        double max=0;
        for (int i =0; i<array.size();i++){
            if(array.get(i)>max){
                max=array.get(i);
            }
        }
        String maxS=""+max;
        return maxS;
    }

    public String minValue(ArrayList <Double> array){
        double min=array.get(0);
        for (int i =0; i<array.size();i++){
            if(array.get(i)<min){
                min=array.get(i);
            }
        }
        String minS=""+min;
        return minS;
    }

}
