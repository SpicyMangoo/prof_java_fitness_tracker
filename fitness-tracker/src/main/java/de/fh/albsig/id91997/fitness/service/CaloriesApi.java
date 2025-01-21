package de.fh.albsig.id91997.fitness.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service class to interact with the Nutritionix API to calculate calories burned based on a
 * workout type and duration.
 */
public class CaloriesApi {

  private static final Dotenv DOTENV = Dotenv.load();
  private static final String APP_ID = DOTENV.get("APP_ID");
  private static final String API_KEY = DOTENV.get("API_KEY");
  private static final String API_URL = "https://trackapi.nutritionix.com/v2/natural/exercise";
  private static final Logger LOGGER = LogManager.getLogger(CaloriesApi.class);

  /**
   * Retrieves the number of calories burned for a specific workout type and duration by calling the
   * Nutritionix API.
   *
   * @param workoutType the type of workout (e.g., "running", "cycling")
   * @param duration the duration of the workout in minutes
   * @return the number of calories burned or 0 if there is an error
   */
  public int getCaloriesBurned(String workoutType, int duration) {
    String query = workoutType + " for " + duration + " minutes";

    LOGGER.info("Starting to fetch calories for workout: {}", query);

    OkHttpClient client = new OkHttpClient();

    String jsonBody = "{ \"query\": \"" + query + "\" }";
    RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

    Request request = new Request.Builder().url(API_URL).post(body).addHeader("x-app-id", APP_ID)
        .addHeader("x-app-key", API_KEY).build();

    try {
      // Make the request synchronously
      Call call = client.newCall(request);
      Response response = call.execute(); // Blocks Thread until the response is received
      ResponseBody resBody = response.body();
      String resBodyString = null;

      if (response.isSuccessful()) {
        if (resBody != null) {
          resBodyString = resBody.string();
        } else {
          LOGGER.error("Response Body is null");
          return 0;
        }
        LOGGER.info("Calories fetched successfully: {}", resBodyString);
        int caloriesBurned = parseCaloriesFromResponse(resBodyString);
        return caloriesBurned;
      } else {
        LOGGER.warn("Request failed with HTTP code: {}", response.code());
        return 0; // Return 0 if the request failed
      }
    } catch (IOException e) {
      LOGGER.error("Error in Request creation", e);
      return 0;
    }
  }

  /**
   * Parses the response body to extract the calories burned from the API response.
   *
   * @param responseBody the raw response body from the Nutritionix API
   * @return the number of calories burned or 0 if the response is invalid
   */
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
      LOGGER.error("Error parsing the response JSON: ", e);
      return 0;
    }
    return 0;
  }
}
