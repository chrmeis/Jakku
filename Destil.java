import org.json.JSONArray;

public class Destil {
    public static void main(String args[]){
        Weather weather = new Weather();

        JSONArray jsonArray = weather.requestShortWeather();
    }
}
