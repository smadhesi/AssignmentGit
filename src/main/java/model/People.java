package model;

import java.util.List;

public class People {
    private String name;
    private String surname;
    private String address;
    private String city;
    private Integer telephone;
    private String email;
    private String dateOfBirth;
    private String sex;
    private String martialStatus;
    private String nationality;
    private String countryOfBirth;
    private Integer grossIncome;
    private String currency;
    private List<String> recommendedCity;
    private String weather;
    private List<Forecast> forecasts;
    private Double weeklyIncomeInEuro;
    public Double getWeeklyIncomeInEuro() {
        return weeklyIncomeInEuro;
    }

    public void setWeeklyIncomeInEuro(Double weeklyIncomeInEuro) {
        this.weeklyIncomeInEuro = weeklyIncomeInEuro;
    }
    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public List<String> getRecommendedCity() {
        return recommendedCity;
    }

    public void setRecommendedCity(List<String> recommendedCity) {
        this.recommendedCity = recommendedCity;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public int getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(int grossIncome) {
        this.grossIncome = grossIncome;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
