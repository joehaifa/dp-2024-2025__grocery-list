package com.fges.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void test_exec_should_return_1_with_no_arguments() throws Exception {
        // Arrange
        String[] args = {};

        // Act
        int result = Main.exec(args);

        // Assert
        assertEquals(1, result);

    }
}
