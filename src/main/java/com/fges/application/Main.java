package com.fges.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

// Point d'entrée du programme : lance l'exécution avec les arguments de la ligne de commande.
public class Main {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        CommandExecutor executor = new CommandExecutor(OBJECT_MAPPER);
        return executor.run(args);
    }
}