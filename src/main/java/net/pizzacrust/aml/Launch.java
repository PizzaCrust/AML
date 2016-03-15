package net.pizzacrust.aml;

import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Launches the abstract mod loader accordingly to the client's version.
 */
public class Launch {
    public static void launch() {
        System.out.println("Retrieving AML's logger...");

        Logger logger = LogManager.getLogger("AML");

        logger.info("Connected to Log4j!");
        logger.info("Checking for a MinecraftServer class...");

        try {
            Class.forName("net.minecraft.server.MinecraftServer");
        } catch (ClassNotFoundException e) {
            logger.error("Could not find the default Minecraft server class!");
            logger.error("This error should not be called, ever. All clients/server versions supported by AML should have a server class!");
            logger.error("Please report this error to a person that may help you.");
            e.printStackTrace();
            return;
        }

        logger.info("Detecting Minecraft version...");
        String minecraftVersion = MinecraftServer.getMinecraftVersion();
        if (minecraftVersion == null) {
            logger.error("Minecraft version is invalid? Minecraft version is null.");
            logger.error("Incompatible client with AML.");
            return;
        }
    }
}
