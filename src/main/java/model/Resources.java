package model;


public class Resources {
    private CityConfig cityConfig;
    private WeatherConfig weatherConfig;
    private CurrencyConfig currencyConfig;
    private ForexConfig forexConfig;

    public CurrencyConfig getCurrencyConfig() {
        return currencyConfig;
    }
    public ForexConfig getForexConfig() {
        return forexConfig;
    }

    public void setForexConfig(ForexConfig forexConfig) {
        this.forexConfig = forexConfig;
    }
    public void setCurrencyConfig(CurrencyConfig currencyConfig) {
        this.currencyConfig = currencyConfig;
    }
    public CityConfig getCityConfig() {
        return cityConfig;
    }

    public void setCityConfig(CityConfig cityConfig) {
        this.cityConfig = cityConfig;
    }

    public WeatherConfig getWeatherConfig() {
        return weatherConfig;
    }

    public void setWeatherConfig(WeatherConfig weatherConfig) {
        this.weatherConfig = weatherConfig;
    }

    public class CityConfig{
        private String hostname;
        private String apikey;
        private String authorizationHeader;
        public String getAuthorizationHeader() {
            return authorizationHeader;
        }
        public void setAuthorizationHeader(String authorizationHeader) {
            this.authorizationHeader = authorizationHeader;
        }
        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }
    }

    public class WeatherConfig{
        private String hostname;
        private String path;
        private String apikey;
        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
        public String getApikey() {
            return apikey;
        }
        public void setApikey(String apikey) {
            this.apikey = apikey;
        }
        public String getHostname() {
            return hostname;
        }
        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
    }

    public class CurrencyConfig{
        public String getBaseUri() {
            return baseUri;
        }

        public void setBaseUri(String baseUri) {
            this.baseUri = baseUri;
        }

        private String baseUri;

    }

    public class ForexConfig{
        private String baseUri;
        public String getBaseUri() {
            return baseUri;
        }

        public void setBaseUri(String baseUri) {
            this.baseUri = baseUri;
        }

    }
}
