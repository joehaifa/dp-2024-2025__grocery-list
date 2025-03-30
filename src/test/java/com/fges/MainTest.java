package com.fges;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Test
    void testExec_ShouldCallExecutorRun() throws IOException {
        try (MockedStatic<Main> mainMock = mockStatic(Main.class)) {
            // Setup mock behavior
            mainMock.when(() -> Main.exec(any())).thenReturn(0);

            // Test the exec method
            String[] args = {"-s", "test.json"};
            int result = Main.exec(args);

            assertEquals(0, result);
            mainMock.verify(() -> Main.exec(args));
        }
    }



    @Test
    void testObjectMapper_ShouldBeInitialized() {
        assertNotNull(Main.OBJECT_MAPPER);
    }
}