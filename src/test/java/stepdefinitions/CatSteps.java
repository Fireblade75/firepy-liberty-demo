package stepdefinitions;

import com.google.gson.reflect.TypeToken;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.firepy.examples.liberty.CatDto;
import nl.firepy.examples.liberty.FurColor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.google.gson.Gson;

public class CatSteps {

    private static final String BASE_URL = "http://localhost:9080/firepy-liberty-app/";
    private HashMap<String, String> data = new HashMap<>();

    @When("the url {string} is called")
    public void theUrlIsCalled(String url) {
        ApiUrlDefinition apiUrl = ApiUrlDefinition.valueOf(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + apiUrl.getUrl()))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            data.put("RESPONSE", response.body());
        } catch (IOException | InterruptedException e) {
            data.put("RESPONSE", null);
            throw new RuntimeException(e);
        }
    }

    @When("the url {string} is called with the name {string}")
    public void theUrlIsCalled(String url, String name) {
        ApiUrlDefinition apiUrl = ApiUrlDefinition.valueOf(url);
        URI uri = URI.create(BASE_URL + apiUrl.getUrl() + "?name=" + name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        System.err.println(uri.toString());
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());

            data.put("RESPONSE", response.body());
        } catch (IOException | InterruptedException e) {
            data.put("RESPONSE", null);
            throw new RuntimeException(e);
        }
    }

    @Then("an empty list of Cats is returned")
    public void anEmptyListOfCatsIsReturned() {
        String actual = data.get("RESPONSE");
        if (actual == null || !actual.equals("[]")) {
            throw new RuntimeException("Actual was " + actual + ", expected value was []");
        }
    }

    @Then("the following list of Cats ir returned:")
    public void theFollowingListOfCatsIrReturned(DataTable dataTable) {
        String actual = data.get("RESPONSE");
        List<CatDto> expectedCats = new ArrayList<>();
        for (int i = 1; i < dataTable.width(); i++) {
            List<String> column = dataTable.column(i);
            expectedCats.add(convertColumnToCat(dataTable.column(0), column));
        }
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CatDto>>() {}.getType();
        List<CatDto> actualCats = gson.fromJson(actual, listType);

        if (actualCats.size() != expectedCats.size()) {
            throw new RuntimeException("Actual size was " + actualCats.size() + ", expected size was " + expectedCats.size());
        }

        for (CatDto expectedCat : expectedCats) {
            CatDto actualCat = actualCats.stream()
                    .filter(cat -> cat.getName().equals(expectedCat.getName())).findAny()
                    .orElseThrow(() -> new RuntimeException("No actual cat found for name " + expectedCat.getName()));

            if ((expectedCat.getAge() != actualCat.getAge()) || (!expectedCat.getColor().equals(actualCat.getColor()))) {
                throw new RuntimeException("Expected cat " + expectedCat.getName() + " did not match the actual cat");
            }
        }
    }

    private CatDto convertColumnToCat(List<String> indexColumn, List<String> dataColumn) {
        int nameIndex = indexColumn.indexOf("Name");
        int ageIndex = indexColumn.indexOf("Age");
        int colorIndex = indexColumn.indexOf("Color");

        String name = dataColumn.get(nameIndex);
        int age = Integer.parseInt(dataColumn.get(ageIndex));
        String color = dataColumn.get(colorIndex);

        return new CatDto(name, FurColor.valueOf(color), age);
    }

}
