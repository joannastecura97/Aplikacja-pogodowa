package weather;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherFromServer {
    private String place;

    public String serverResponse(String place) {

        this.place = place;

        StringBuffer response = new StringBuffer();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + place + "&units=metric&APPID=cbd9d8b94222c9d1eb207c553efa344b";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();

        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            System.out.println("Such a place does not exist!!");

        }
        String responseString = response.toString();
        return responseString;
    }

    public void toFile (String name, ArrayList<String> jsonString,WeatherParameters wp){
        File file = new File(name+".txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for (int i =0; i < jsonString.size();i++) {
                printWriter.print(jsonString.get(i)+"\n"+wp.getArrayTime().get(i)+"\n");
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> fromFile (String name) throws FileNotFoundException {
        ArrayList<String> jsonString= new ArrayList<>();
        File file = new File(name+".txt");
        Scanner in = new Scanner(file);
        while(in.hasNextLine()){
            String zdanie = in.nextLine();
            jsonString.add(zdanie);
            System.out.println(zdanie);
        }
        return jsonString;
    }

    public int readFromFile (ArrayList<String> jsonString, WeatherParameters wp){
        int k=0;
        int l=1;
        for(int i=0;i<jsonString.size();i++){
            if(i==k) {
                wp.getTemp(jsonString.get(i));
                wp.getHumidity(jsonString.get(i));
                wp.getPressure(jsonString.get(i));
                wp.getWindSpeed(jsonString.get(i));
                k++;
                k++;
            }
            if(i==l){
                wp.addArrayTime(jsonString.get(i));
                l++;
                l++;
            }
        }
        return wp.getArrayTemp().size();
    }

}
