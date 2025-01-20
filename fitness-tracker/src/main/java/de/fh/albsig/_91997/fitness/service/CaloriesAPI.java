package de.fh.albsig._91997.fitness.service;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.cdimascio.dotenv.Dotenv;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class CaloriesAPI {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String APP_ID = dotenv.get("APP_ID");
    private static final String API_KEY = dotenv.get("API_KEY");
    private static final String API_URL = "https://trackapi.nutritionix.com/v2/natural/exercise";
    
    // Create a Logger instance
    private static final Logger logger = LogManager.getLogger(CaloriesAPI.class);

    // Simple, synchronous method to get calories burned from the Nutritionix API
    public int getCaloriesBurned(String workoutType, int duration) {
        String query = workoutType + " for " + duration + " minutes";
        
        logger.info("Starting to fetch calories for workout: {}", query);
        
        OkHttpClient client = new OkHttpClient();
        
        String jsonBody = "{ \"query\": \"" + query + "\" }";
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("x-app-id", APP_ID)
                .addHeader("x-app-key", API_KEY)
                .build();

        try {
        	// Make the request synchronously
            Call call = client.newCall(request);
            Response response = call.execute(); // Blocks Thread until the response is received
            
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                logger.info("Calories fetched successfully: {}", responseBody);
                int caloriesBurned = parseCaloriesFromResponse(responseBody);
                return caloriesBurned;
            } else {
                logger.warn("Request failed with HTTP code: {}", response.code());
                return 0; // Return 0 if the request failed
            }
        } catch (IOException e) {
        	logger.error("Error in Request creation");
        	return 0;
        }
        
    }

    private int parseCaloriesFromResponse(String responseBody) {
    	try {
            // Initialize the Jackson ObjectMapper to parse the response
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Parse the response body to a JsonNode
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Extract the first exercise's calories burned
            JsonNode exercisesNode = rootNode.path("exercises");
            if (exercisesNode.isArray() && exercisesNode.size() > 0) {
                JsonNode exerciseNode = exercisesNode.get(0);
                int caloriesBurned = exerciseNode.path("nf_calories").asInt();
                return caloriesBurned; // Return the calories burned value
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing the response JSON: ", e);
            return 0;
        }
    	return 0;
    }
}
