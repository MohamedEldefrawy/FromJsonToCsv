import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        List<JSONObject> processedData = new ArrayList<>();
        JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/countries.json"));
        var fieldNames = jsonTree.fieldNames();
        List<String> countryNames = new ArrayList<>();
        List<JsonNode> cities = new ArrayList<>();


        while (fieldNames.hasNext()) {
            countryNames.add(fieldNames.next());
        }

        for (int i = 0; i < countryNames.size(); i++) {
            cities.add(jsonTree.get(countryNames.get(i)));
        }


        // write new json with desired format
        for (int i = 0; i < countryNames.size(); i++) {

            JSONObject jsonObject = new JSONObject();
            List<Integer> cityLengths = new ArrayList<>();


            var citiesInCountry = cities.get(i);

            for (int j = 0; j < citiesInCountry.size(); j++) {
                cityLengths.add(citiesInCountry.get(j)
                        .toString().replaceAll("^\"|\"$", "").length());
            }

            var index = cityLengths.indexOf(getMaxNumber(cityLengths));


            jsonObject.put("Number of cities", cities.get(i).size());
            jsonObject.put("Country name", countryNames.get(i));
            jsonObject.put("City with max length", cities.get(i).get(index));

            processedData.add(jsonObject);


            try {
                FileWriter file = new FileWriter("src/main/resources/processedData.json");
                file.write(String.valueOf(processedData));
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // read json and write csv
//        JsonNode processedJsonTree = new ObjectMapper().readTree(new File("src/main/resources/processedData.json"));


        try {
            CsvMapper csvMapper = new CsvMapper();

            CsvSchema csvSchema = csvMapper
                    .schemaFor(OrderLineForCsv.class)
                    .withHeader();

            csvMapper.addMixIn(Country.class, OrderLineForCsv.class);
            Country[] countries = new ObjectMapper()
                    .readValue(new File("src/main/resources/processedData.json"), Country[].class);
            csvMapper.writerFor(Country[].class)
                    .with(csvSchema)
                    .writeValue(new File("src/main/resources/result.csv"), countries);
        } catch (Exception e) {
            JsonNode processedJsonTree = new ObjectMapper().readTree(new File("src/main/resources/processedData.json"));
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode header = processedJsonTree.elements().next();
            header.fieldNames().forEachRemaining(fieldName -> csvSchemaBuilder.addColumn(fieldName));
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File("src/main/resources/result.csv"), processedJsonTree);
        }

    }

    public static int getMaxNumber(List<Integer> nums) {
        int maximum = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            if (maximum < nums.get(i))
                maximum = nums.get(i);
        }
        return maximum;
    }

}