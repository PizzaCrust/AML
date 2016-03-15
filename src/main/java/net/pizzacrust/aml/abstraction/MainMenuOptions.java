package net.pizzacrust.aml.abstraction;

/**
 * Options to customize the main menu for mod loaders.
 */
public class MainMenuOptions {
    private String modLoaderName;
    private int loadedModsAmount;

    public MainMenuOptions(String modLoaderName, int loadedModsAmount) {
        this.modLoaderName = modLoaderName;
        this.loadedModsAmount = loadedModsAmount;
    }

    public int getLoadedModsAmount() {
        return loadedModsAmount;
    }

    public String getModLoaderName() {
        return modLoaderName;
    }
}
