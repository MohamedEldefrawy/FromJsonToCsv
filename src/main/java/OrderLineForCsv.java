import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "Country name",
        "Number of cities",
        "City with max length"
})

public abstract class OrderLineForCsv {

    @JsonProperty("Country name")
    private String countryName;

    @JsonProperty("City with max length")
    private String cityWithMaxLength;

    @JsonProperty("Number of cities")
    private int numberOfCities;
}
