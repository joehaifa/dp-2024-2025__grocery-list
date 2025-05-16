package com.fges.commands;

import com.fges.application.CommandContext;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HandleInfoCommandTest {

    @Test
    void execute_shouldReturn0_andPrintSystemInfo() {
        // Arrange
        CommandContext ctx = new CommandContext(
                Arrays.asList("info"),
                null,
                null
        );
        HandleInfoCommand cmd = new HandleInfoCommand();

        // Redirect System.out to capture the output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        try {
            // Act
            int status = cmd.execute(ctx);
            String output = buffer.toString();

            // Assert
            assertEquals(0, status, "Return code should be 0");
            assertTrue(output.contains("Today's date:"),       "Date line must be present");
            assertTrue(output.contains("Operating System:"),   "OS line must be present");
            assertTrue(output.contains("Java version:"),       "Java version line must be present");
        } finally {
            // # Restore original System.out (avoid side effects on other tests)
            System.setOut(originalOut);
        }
    }
}
