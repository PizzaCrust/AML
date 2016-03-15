package net.pizzacrust.aml.versions;

import net.pizzacrust.aml.abstraction.AbstractModLoader;
import net.pizzacrust.aml.abstraction.MainMenuOptions;
import net.pizzacrust.aml.meta.VersionModLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A universal basic loader for the Minecraft 1.9.
 * There is no configurable item in this loader. It is the bare basics.
 */
@VersionModLoader("1.9")
public class BasicUniversalLoader implements AbstractModLoader {
    public static int amountOfLoadedMods = 0;
    public static Logger logger = LogManager.getLogger("UniversalML");

    public MainMenuOptions customizeMainMenu() {
        return new MainMenuOptions("Universal Mod Loader", amountOfLoadedMods);
    }

    public void load(File mod) throws Exception {
        logger.info("Loading file (" + mod.getName() + ") as a mod...");
        JarFile jarFile = new JarFile(mod);
        JarEntry uniManifest = jarFile.getJarEntry("universal.unimanifest");
        Properties properties = new Properties();
        properties.load(jarFile.getInputStream(uniManifest));
        String mainClassPath = properties.getProperty("main");
        String initMethodName = properties.getProperty("initname");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{ mod.toURI().toURL() });
        Class modClass = classLoader.loadClass(mainClassPath);
        Method initMethod = modClass.getDeclaredMethod(initMethodName, new Class[]{});
        initMethod.invoke(modClass.newInstance(), null);
        logger.info("File (" + mod.getName() + ") has loaded successfully.");
        amountOfLoadedMods++;
    }
}
