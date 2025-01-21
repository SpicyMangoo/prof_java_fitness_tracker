package de.fh.albsig.id91997.fitness.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fh.albsig.id91997.fitness.model.Workout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;




/**
 * Unit test class for the FileManager class.
 * This class contains tests for saving workout data to a file using ObjectMapper.
 */
public class FileManagerTest {
  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private FileManager fileManager;
  @Mock
  private File mockFile;

  private static final String USERNAME = "TestUser";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveWorkoutsToFileShouldBeSuccess() throws IOException {
    // Arrange: Mock the behavior of ObjectMapper and File
    List<Workout> workouts = Arrays.asList(new Workout("Running", 30, 300));

    // Mock behavior for saving workouts to file
    doNothing().when(objectMapper).writeValue(any(File.class), eq(workouts));

    // Act: Call the method to save workouts to the file
    fileManager.saveWorkoutsToFile(USERNAME, workouts);

    // Assert: Verify writing of workouts to file
    verify(objectMapper).writeValue(any(File.class), eq(workouts));
  }
}
