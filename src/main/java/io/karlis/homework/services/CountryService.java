package io.karlis.homework.services;

import com.sun.jdi.connect.Connector;
import io.karlis.homework.dto.CountryResult;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class CountryService {

    private static final String COUNTRIES_API_URL = "https://restcountries.com/v2/all?fields=name";
    private static final String CURRENT_LOCATION_API_URL = "https://ipapi.co/json/";

    @SneakyThrows
    public CountryResult getSortedCountries(){
        CountryResult countryResult = new CountryResult();

        countryResult.setCountries(sortedCountries());
        countryResult.setCurrentLocation(getCurrentLocation());
        return countryResult;
    }
    @SneakyThrows
    private List<String> sortedCountries() {
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(COUNTRIES_API_URL, String.class);

        JSONArray jsonArray = new JSONArray(response);
        List<String> countryList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            countryList.add(jsonObject.getString("name"));
        }
        return countryList;
    }
    @SneakyThrows
    private String getCurrentLocation() throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(CURRENT_LOCATION_API_URL, String.class);

        JSONObject jsonObject = new JSONObject(response);

        return jsonObject.getString("country_name");
    }
}
