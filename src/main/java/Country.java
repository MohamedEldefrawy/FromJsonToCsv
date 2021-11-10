
public class Country {

    private String cityWithMaxLength;

    private int numberOfCities;
    private String countryName;


    public Country(String countryName, String cityWithMaxLength, int numberOfCities) {
        this.countryName = countryName;
        this.cityWithMaxLength = cityWithMaxLength;
        this.numberOfCities = numberOfCities;
    }

    public Country() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityWithMaxLength() {
        return cityWithMaxLength;
    }

    public void setCityWithMaxLength(String cityWithMaxLength) {
        this.cityWithMaxLength = cityWithMaxLength;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public void setNumberOfCities(int numberOfCities) {
        this.numberOfCities = numberOfCities;
    }

    @Override
    public String toString() {
        return "Country{" +
                "CountryName='" + countryName + '\'' +
                ", CityWithMaxLength='" + cityWithMaxLength + '\'' +
                ", NumberOfCities=" + numberOfCities +
                '}';
    }
}
