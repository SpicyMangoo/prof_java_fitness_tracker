package de.fh.albsig._91997.fitness.util;

import de.fh.albsig._91997.fitness.model.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


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
	    void testSaveWorkoutsToFile_Success() throws IOException {
	        // Arrange: Mock the behavior of ObjectMapper and File
	        List<Workout> workouts = Arrays.asList(
	                new Workout("Running", 30, 300)
	        );

	        // Mock behavior for saving workouts to file
	        doNothing().when(objectMapper).writeValue(any(File.class), eq(workouts));

	        // Act: Call the method to save workouts to the file
	        fileManager.saveWorkoutsToFile(USERNAME, workouts);

	        // Assert: Verify writing of workouts to file 
	        verify(objectMapper).writeValue(any(File.class), eq(workouts));
	    }

	    @Test
	    void testSaveWorkoutsToFile_IOException() throws IOException {
	        // Arrange: Mock the behavior of ObjectMapper and File
	        List<Workout> workouts = Arrays.asList(
	                new Workout("Running", 30, 300)
	        );

	        doThrow(new IOException("Test Exception")).when(objectMapper).writeValue(any(File.class), eq(workouts));

	        // Act: Call the method to save workouts to the file
	        fileManager.saveWorkoutsToFile(USERNAME, workouts);

	        // Assert: Verify writing of workouts to file 
	        verify(objectMapper).writeValue(any(File.class), eq(workouts));
	    }
}
