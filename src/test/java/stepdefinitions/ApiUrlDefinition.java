package stepdefinitions;

public enum ApiUrlDefinition {

    CATS("cats");

    private final String url;

    ApiUrlDefinition(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
