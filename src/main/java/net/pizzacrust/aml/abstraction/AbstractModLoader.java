package net.pizzacrust.aml.abstraction;

import java.io.File;

/**
 * An abstract mod loader class that defines what a mod loader class requires.
 * This should be version-specific on how the mods are loaded.
 */
public interface AbstractModLoader {
    MainMenuOptions customizeMainMenu();

    void load(File mod) throws Exception;
}
