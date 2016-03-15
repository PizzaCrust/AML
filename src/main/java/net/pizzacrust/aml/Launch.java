package net.pizzacrust.aml;

import net.minecraft.server.MinecraftServer;
import net.pizzacrust.aml.abstraction.AbstractModLoader;
import net.pizzacrust.aml.meta.VersionModLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

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
        logger.info("Detected Minecraft Version: " + minecraftVersion);

        logger.info("Finding correct mod loader for version specified...");
        Reflections reflections = new Reflections("net.pizzacrust");
        Class<?> modLoader = null;
        Set<Class<?>> versionModLoaders = reflections.getTypesAnnotatedWith(VersionModLoader.class);
        for (Class<?> modLoaderClass : versionModLoaders) {
            VersionModLoader annotation = (VersionModLoader) modLoaderClass.getAnnotation(VersionModLoader.class);
            if (annotation.value().equals(minecraftVersion)) {
                logger.info("Mod loader has been found for version specified!");
                modLoader = modLoaderClass;
            }
        }
        if (modLoader == null) {
            logger.error("Mod loader for version specified wasn't found. This version of AML isn't compatible with this version of Minecraft.");
            return;
        }
        logger.info("Passing instance -> mod loader...");
        try {
            AbstractModLoader loader = (AbstractModLoader) modLoader.newInstance();
        } catch (Exception e) {
            logger.error("Failed to pass instance to mod loader.");
            e.printStackTrace();
            return;
        }
    }
}
