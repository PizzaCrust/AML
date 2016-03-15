package org.example;

import net.minecraft.server.MinecraftServer;

/**
 * A example mod for AML.
 */
public class ExampleMod {
    public void init() {
        System.out.println("Mod loading is working.");
        System.out.println("Current mod MC version is: " + MinecraftServer.getMinecraftVersion());
    }
}
