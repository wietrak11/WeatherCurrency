/**
 *
 *  @author Wietrak Mateusz S16686
 *
 */

package zad1;

import com.google.gson.*;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Service {
    private String country;

    public Service(){};
    public Service(String country) {
        this.country = country;
    }

    public String getWeather(String city) {

        OWM owm = new OWM("2d1fe855b30d5c9ff68a9aa0493ae19d");
        owm.setLanguage(OWM.Language.POLISH);
        owm.setUnit(OWM.Unit.METRIC);
        String JSON = null;

        try {
            CurrentWeather currentWeather = owm.currentWeatherByCityName(city);
            if(currentWeather.hasBaseStation()){
                JSON = CurrentWeather.toJson(currentWeather);
            }
        } catch (APIException e) {
            return null;
        }

        return JSON;
    }

    public double getRateFor(String currency) {

        Locale.setDefault(new Locale("en","GB"));
        String countryCode = getCountryCode();
        if(countryCode == null){
            return 0.0;
        }
        Locale loc = new Locale("", countryCode);
        Currency cur = Currency.getInstance(loc);

        String BASE_URL = "https://api.exchangeratesapi.io/";
        String ENDPOINT = "latest";
        String BASE = cur.getCurrencyCode();
        String SYMBOL = currency;
        double exchangeRate = 0.0;

        HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?base=" + BASE + "&symbols=" + SYMBOL);
        try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(get);){

            HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity);
            if(jsonString.startsWith("{\"error\"")){
                return 0.0;
            }
            else{
                JsonParser parser = new JsonParser();
                JsonElement rootNode = parser.parse(jsonString);

                if(rootNode.isJsonObject()){
                    JsonObject details = rootNode.getAsJsonObject();
                    JsonElement ratesNode = details.get("rates");

                    rootNode = parser.parse(ratesNode.toString());
                    if(rootNode.isJsonObject()){
                        details = rootNode.getAsJsonObject();
                        JsonElement rateNode = details.get(currency);
                        exchangeRate = rateNode.getAsDouble();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return exchangeRate;
    }

    public Double getNBPRate() {

        if(country.equals("Poland")){
            return 1.0;
        }

        Locale.setDefault(new Locale("en","GB"));
        String countryCode = getCountryCode();
        if(countryCode == null){
            return 0.0;
        }
        Locale loc = new Locale("", getCountryCode());
        Currency cur = Currency.getInstance(loc);

        String[] BASE_URL = {"http://www.nbp.pl/kursy/kursya.html","http://www.nbp.pl/kursy/kursyb.html"};
        double nbpRate = 0.0;

        for(int i = 0 ; i < BASE_URL.length ; i++){

            HttpGet get = new HttpGet(BASE_URL[i]);

            try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(get);){

                HttpEntity entity = response.getEntity();
                String exchangeRates = EntityUtils.toString(entity);
                int currencyIndex = exchangeRates.indexOf(cur.toString());

                if(currencyIndex != -1 && !country.equals("Poland")){

                    int currencyIndex2 = exchangeRates.indexOf(">", currencyIndex + 10) + 1;
                    nbpRate = Double.valueOf(exchangeRates.substring(currencyIndex2, currencyIndex2 + 6).replace(',', '.'));

                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return nbpRate;
    }

    private String getCountryCode(){
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
        if(countries.get(country)!=null){
            return countries.get(country);
        }
        else{
            return null;
        }
    }

}
