package model;

import java.util.List;
import java.util.Map;

public class Forecast {
   private String dt_txt;
   private List<Weather> weather;
    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }


}
