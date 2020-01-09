package Scripts;

import model.Forecast;
import model.People;
import model.Weather;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.DataService;
import service.RandomDataGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Scripts {
    DataService dataService;
    RandomDataGenerator randomDataGenerator;
    private List<People> people;
    private List<People> peopleWithForeacast;
    private List<People> peopleWithCitiesOfBadWeather;
    private List<People> peopleWithCitiesOfClearWeather;
    private ConcurrentHashMap<String,List<Forecast>> weathermap;
    private List<String> citiesWithGoodWeather;
    @BeforeClass
    public void testDataGenerator(){
        dataService =new DataService();
        randomDataGenerator = new RandomDataGenerator();
        people = randomDataGenerator.generateRandomData("United States","US");
    }
    @Test(priority = 1)
    public void getWeatherForecastByCityName(){
        peopleWithForeacast = people.parallelStream().limit(20).map(x->getWeatherDesc(x)).collect(Collectors.toList());
        Assertions.assertThat(peopleWithForeacast.size()).isEqualTo(20);
    }
    @Test(priority=2)
    public void getWeeklyIncomeInEuro(){
        String euroRate = dataService.fetchForexRatesForEUR(people.get(0).getCurrency());
        people.parallelStream().map(x->getWeeklyIncomeInEuro(x,euroRate)).collect(Collectors.toList());
    }

    @Test(priority = 3)
    public void filterCityWithBadWeather(){
        peopleWithCitiesOfBadWeather = peopleWithForeacast.stream().filter(x->isWeatherBad(x)).collect(Collectors.toList());
    }
    @Test(priority = 4)
    public void filterCityWithClearWeather(){
        peopleWithCitiesOfClearWeather = peopleWithForeacast.stream().filter(isWeatherGood).collect(Collectors.toList());
        citiesWithGoodWeather = peopleWithCitiesOfClearWeather.stream().map(x->x.getCity()).collect(Collectors.toList());
    }

    @Test(priority=5)
    public void suggestPeopleToVisitCityWithGoodWeather(){
        peopleWithCitiesOfBadWeather.parallelStream().map(x->suggestPeopleCitiesOfGoodWeather(x)).collect(Collectors.toList());
    }

    private People getWeatherDesc(People people){
        String city = people.getCity();
        weathermap = dataService.fetchWeatherOfCities(city);
        people.setForecasts(weathermap.get(city));
        people.setWeather(people.getForecasts().get(0).getWeather().get(0).getMain());
        return people;
    }

    private People suggestPeopleCitiesOfGoodWeather(People people){
        people.setRecommendedCity(citiesWithGoodWeather);
        return people;
    }

    private Boolean isWeatherBad(People people){
        boolean isWeatherBad = false;
        List<Forecast> forecast = people.getForecasts();
        if(!people.getWeather().equalsIgnoreCase("clear")){
            isWeatherBad = isWeatherBadForNextTwoDays.test(forecast)?true:false;
        }
        return isWeatherBad;
    }

    private People getWeeklyIncomeInEuro(People people,String rates){
        int annualIncome = people.getGrossIncome();
        double incomeInEuro = (Double.parseDouble(rates)*annualIncome)/52;
        people.setWeeklyIncomeInEuro(incomeInEuro);
        return people;
    }

    Predicate<List<Forecast>> isWeatherBadForNextTwoDays = e-> (!(e.get(1).getWeather().get(0).getMain().equalsIgnoreCase("clear"))&&!(e.get(2).getWeather().get(0).getMain().equalsIgnoreCase("clear")));
    Predicate<People> isWeatherGood = e->(e.getWeather().equalsIgnoreCase("clear"));


}
