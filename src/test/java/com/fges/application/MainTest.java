package com.fges.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void exec_shouldReturn1_whenNoArgumentsProvided() throws Exception {
        // Arrange
        String[] args = {};

        // Act
        int result = Main.exec(args);

        //Assert
        assertEquals(1, result, "No CLI arguments → expect error code 1");
    }

    @Test
    void exec_shouldReturn0_whenInfoCommandProvided() throws Exception {
        // Arrange
        String[] args = { "info" };

        //Act
        int result = Main.exec(args);

        //Assert
        assertEquals(0, result, "Info command should succeed and return 0");
    }
}
