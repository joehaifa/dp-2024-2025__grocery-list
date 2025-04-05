package com.fges;

import java.io.IOException;
import java.util.List;

interface Command {
    int execute(List<String> args, GroceryListDAO dao) throws IOException;
}