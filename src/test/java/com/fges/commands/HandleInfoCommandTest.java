package com.fges.commands;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandleInfoCommandTest {

    @Test
    public void test_execute_should_return_0() {
        // Arrange
        HandleInfoCommand command = new HandleInfoCommand();
        List<String> args = List.of();

        // Act
        int result = command.execute(args);

        // Assert
        assertThat(result).isEqualTo(0);
    }
}
