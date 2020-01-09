package service;

import model.City;
import model.Currency;
import model.People;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomDataGenerator {
    DataService dataService;
    public RandomDataGenerator(){
        this.dataService = new DataService();
    }

   public List<People> generateRandomData(String country,String code){
       List<City> cities = dataService.fetchCitiesByCountryName(country);
       Currency currencyObj = dataService.fetchCurrencyByCountryName(code);
       String currency = currencyObj.getCode();
       List<People> peoples = cities.parallelStream().map(x->generateRandomPeopleData(x,country,currency)).collect(Collectors.toList());
       return peoples;
  }

  public People generateRandomPeopleData(City city,String country,String currency){
        Integer randomInt1 = ThreadLocalRandom.current().nextInt(1,5);
        Integer randomInt2 = ThreadLocalRandom.current().nextInt(8,10);
       People people = new People();
       people.setName(generateRandomName(randomInt1,randomInt2));
       people.setSurname(generateRandomSurName(randomInt1,randomInt2));
       people.setCountryOfBirth(country);
       people.setMartialStatus(getMartialStatus(randomInt1));
       people.setSex(getGender(randomInt2));
       people.setCurrency(currency);
       people.setCity(city.getCity());
       people.setDateOfBirth(getDateOfBirth());
       people.setGrossIncome(ThreadLocalRandom.current().nextInt(100000,200000));
       people.setEmail(getEmail());
       people.setAddress(getAddress());
       people.setNationality(country);
       people.setTelephone(getTelephoneNumber());
       return people;
  }
   public synchronized String generateRandomName(int randomInt1,int randomInt2){
       String SOURCES = "gdgasaabhdgasdnlcljqiuqeqkedjaanmccxalouew";
       String str= SOURCES.substring(randomInt1,randomInt2);
       return(str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase());
   }
    public synchronized String generateRandomSurName(int randomInt1,int randomInt2){
        String SOURCES = "bcnbcnshabdhduhklouiuqjanmncdjnfjvnfjfnvjf";
        String str= SOURCES.substring(randomInt1,randomInt2);
        return(str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase());
    }

   public String getGender(int intNum){
       String sex = (intNum%2==0)? "Female":"Male";
       return sex;
   }

   public String getMartialStatus(int intNum){
       String sex = (intNum%2==0)? "Married":"Unmarried";
       return sex;
   }
   public String getDateOfBirth(){
        String dataOfBirth = ThreadLocalRandom.current().nextInt(1,30)+"/"+ (ThreadLocalRandom.current().nextInt(1,12))+"/"+(ThreadLocalRandom.current().nextInt(1965,1990));
       return dataOfBirth;
   }

   public String getEmail(){
       String email = RandomStringUtils.randomAlphanumeric(5).toLowerCase()+"@gmail.com";
       return email;
   }

   public synchronized String getAddress(){
        String address = ThreadLocalRandom.current().nextInt(1,30)+",Street "+generateRandomName(6,12)+" "+ generateRandomName(8,17)+" "+generateRandomName(5,10);
        return address;
   }

   public int getTelephoneNumber(){
       int num = ThreadLocalRandom.current().nextInt(90000000,99999999);
       return num;
   }
}
