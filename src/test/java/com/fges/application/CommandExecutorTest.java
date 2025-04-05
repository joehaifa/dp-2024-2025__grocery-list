package com.fges.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandExecutorTest {

    @Test
    public void test_run_should_return_1_when_arguments_invalid() throws Exception {
        // Arrange
        ObjectMapper mapper = new ObjectMapper();
        CommandExecutor executor = new CommandExecutor(mapper);

        String[] args = {};

        // Act
        int result = executor.run(args);

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void test_run_should_call_add_command_when_add_argument_is_passed() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("grocery", ".json");
        tempFile.deleteOnExit();
        FileWriter fw = new FileWriter(tempFile);
        fw.write("[]");
        fw.close();

        String[] args = {
                "--source", tempFile.getAbsolutePath(),
                "--format", "json",
                "add", "banane", "5", "fruit"
        };

        CommandExecutor executor = new CommandExecutor(new ObjectMapper());

        // Act
        int result = executor.run(args);

        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void test_run_should_return_1_when_command_is_unknown() throws Exception {
        String[] args = {
            "--source", "dummy.json",
            "unknownCommand"
        };

        CommandExecutor executor = new CommandExecutor(new ObjectMapper());
        int result = executor.run(args);

        assertThat(result).isEqualTo(1);
    }

}
