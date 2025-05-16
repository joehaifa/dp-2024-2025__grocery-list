package com.fges.application;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandContextTest {

    @Test
    void gettersReturnExpectedValues_whenSourceFileProvided() {
        //Arrange
        List<String> args = Arrays.asList("add", "pomme", "2");
        CommandContext ctx = new CommandContext(args, "fruit", "list.json");

        //Act
        List<String> positional = ctx.getPositionalArgs();
        String category        = ctx.getCategory();
        boolean hasSource      = ctx.getSourceFile().isPresent();
        String sourcePath      = ctx.getSourceFile().orElse(null);

        //Assert
        assertEquals(args, positional);
        assertEquals("fruit", category);
        assertTrue(hasSource);
        assertEquals("list.json", sourcePath);
    }

    @Test
    void gettersBehaveCorrectly_whenSourceFileIsNull() {
        //Arrange
        List<String> args = Arrays.asList("list");
        CommandContext ctx = new CommandContext(args, null, null);

        //Act
        boolean hasSource = ctx.getSourceFile().isPresent();

        //Assert
        assertFalse(hasSource);
        assertNull(ctx.getCategory());
        assertEquals(args, ctx.getPositionalArgs());
    }
}
