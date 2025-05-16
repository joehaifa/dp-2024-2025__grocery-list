package com.fges.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.commands.HandleAddCommand;
import com.fges.storage.CsvStorage;
import com.fges.storage.JsonStorage;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandExecutorTest {

    //Helper to create the system-under-test
    private static CommandExecutor newExecutor() {
        return new CommandExecutor(new ObjectMapper());
    }

    @Test
    void run_shouldReturn1_whenCommandIsUnknown() throws IOException {
        //Arrange
        CommandExecutor executor = newExecutor();

        //Act
        int status = executor.run(new String[]{"foobar"});

        //Assert
        assertEquals(1, status);
    }

    @Test
    void run_shouldInvokeAddCommand_andUseJsonStorageByDefault() throws Exception {
        // Arrange
        try (MockedConstruction<JsonStorage> jsonCtor =
                     Mockito.mockConstruction(JsonStorage.class);
             MockedConstruction<HandleAddCommand> addCtor =
                     Mockito.mockConstruction(HandleAddCommand.class,
                             (mock, context) ->
                                     when(mock.execute(any())).thenReturn(0))) {

            String[] args = {
                    "add", "apple", "5",
                    "-s", "groceries.json"
            };

            //Act
            int status = newExecutor().run(args);

            //Assert
            assertEquals(0, status);                           // add executed OK
            assertEquals(1, jsonCtor.constructed().size(),     // JsonStorage instantiated
                    "JsonStorage should be used when no -f flag is given");
            assertEquals(1, addCtor.constructed().size(),      // HandleAddCommand built
                    "Add command should be selected for 'add'");
            verify(addCtor.constructed().get(0))               // executed exactly once
                    .execute(any());
        }
    }

    @Test
    void run_shouldUseCsvStorage_whenCsvFormatFlagProvided() throws Exception {
        //Arrange
        try (MockedConstruction<CsvStorage> csvCtor =
                     Mockito.mockConstruction(CsvStorage.class);
             MockedConstruction<HandleAddCommand> addCtor =
                     Mockito.mockConstruction(HandleAddCommand.class,
                             (mock, context) ->
                                     when(mock.execute(any())).thenReturn(0))) {

            String[] args = {
                    "add", "orange", "2",
                    "-s", "groceries.csv",
                    "-f", "csv"
            };

            //Act
            int status = newExecutor().run(args);

            //Assert
            assertEquals(0, status);
            assertEquals(1, csvCtor.constructed().size(),
                    "CsvStorage should be chosen when -f csv is given");
            verify(addCtor.constructed().get(0)).execute(any());
        }
    }
}
