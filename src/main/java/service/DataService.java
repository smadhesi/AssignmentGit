package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import model.Currency;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Constants;
import util.Properties;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataService {
    //private static final Logger logger = LoggerFactory.getLogger(DataService.class);
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private HttpEntity httpEntity;
    private List<City> cities;
    private List<Forecast> forecastList;
    private List<Forecast> forecast;
    private Resources resources;
    private ObjectMapper mapper;
    String forecastTime;
    public DataService(){
        this.resources = Properties.getResources();
        this.httpClient = HttpClientBuilder.create().build();
    }
    public List<City> fetchCitiesByCountryName(String countryName){
        httpClient = HttpClientBuilder.create().build();
        URI uri = buildfetchCityURI(countryName);
        HttpGet request = new HttpGet(uri);
        request.setHeader(resources.getCityConfig().getAuthorizationHeader(),resources.getCityConfig().getApikey());
        request.setHeader(HttpHeaders.ACCEPT,"application/json");
        try {
            response = httpClient.execute(request);
            httpEntity = response.getEntity();
            String responseBody = EntityUtils.toString(httpEntity, "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            cities = mapper.readValue(responseBody, new TypeReference<List<City>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
          return cities;
    }
    public ConcurrentHashMap<String, List<Forecast>> fetchWeatherOfCities(String cityName){
        ConcurrentHashMap<String,List<Forecast>> map = new ConcurrentHashMap<>();
        URI uri = buildfetchWeatherURI(cityName);
        HttpGet request = new HttpGet(uri);
        request.setHeader(HttpHeaders.ACCEPT,"application/json");
        try {
            response = httpClient.execute(request);
            httpEntity = response.getEntity();
            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            forecast = mapper.readValue((String.valueOf(jsonArray)), new TypeReference<List<Forecast>>(){});
            Collections.sort(forecast, Comparator.comparing(s -> s.getDt_txt()));
            synchronized (this){
                forecastTime = forecast.get(0).getDt_txt().split(" ")[1];
                forecastList = forecast.parallelStream().filter(filterCondition).collect(Collectors.toList());
            }

            } catch (IOException e) {
            e.printStackTrace();
        }
        map.put(cityName,forecastList);
       return(map);
    }


     public Currency fetchCurrencyByCountryName(String countryName){
        String uri = resources.getCurrencyConfig().getBaseUri()+countryName+"?fullText=true";
        HttpGet request = new HttpGet(uri);
        List<Currency> currency = null;
        try {
            response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            response.getStatusLine();
            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            JSONArray jsArray = new JSONArray(responseString);
            Object obj = jsArray.getJSONObject(0).get("currencies");
            currency =mapper.readValue((String.valueOf(obj)), new TypeReference<List<Currency>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currency.get(0);
    }

    public String fetchForexRatesForEUR(String currencyCode){
        String uri = resources.getForexConfig().getBaseUri()+"?base="+currencyCode;
        HttpGet request = new HttpGet(uri);
        String forex = null;
        try {
            response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            response.getStatusLine();
            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
            JSONObject jsonObject = new JSONObject(responseString);
            forex = jsonObject.getJSONObject("rates").get("EUR").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forex;
    }

    public URI buildfetchCityURI(String countryName){
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme(Constants.HTTPs)
                    .setHost(resources.getCityConfig().getHostname())
                    .setParameter(Constants.COUNTRY_PARAM,countryName)
                    .setParameter(Constants.SEARCH_BY,Constants.SEARCH_BY_COUNTRY)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;

    }

    public URI buildfetchWeatherURI(String cityName){
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme(Constants.HTTPs)
                    .setHost(resources.getWeatherConfig().getHostname())
                    .setPath(resources.getWeatherConfig().getPath())
                    .setParameter(Constants.WEATHER_SEARCH_BY_PARAM,cityName)
                    .setParameter(Constants.MODE,"json")
                    .setParameter(Constants.WEATHER_API_KEY_NAME,resources.getWeatherConfig().getApikey())
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;

    }
    private Predicate<Forecast> filterCondition= e->
            e.getDt_txt().equals(LocalDate.now()+" "+forecastTime)||e.getDt_txt().equals(LocalDate.now().plusDays(1)+" "+forecastTime)||e.getDt_txt().equals(LocalDate.now().plusDays(2)+" "+forecastTime);
}
