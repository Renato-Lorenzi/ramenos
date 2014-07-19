package rml.ramenos.messager

/**
 * Created by renatol on 7/16/14.
 */

class Config {

    static File RAMENOS_FOLDER = new File(new File(System.properties["user.home"]), ".ramenos")

    static File BUDDIES_DEFAULT_FILE = new File(RAMENOS_FOLDER, "/.buddies ")

    static File BUDDIES_FILE = config["buddies.file"] ? new File(config["buddies.file"]) : null


    static internalConfig

    private static Properties getConfig() {
        if (internalConfig) {
            return internalConfig
        }
        internalConfig = new Properties()
        def configFile = new File(RAMENOS_FOLDER, "config.properties")
        if (configFile.exists()) {
            configFile.withInputStream {
                stream -> internalConfig.load(stream)
            }
        }

        return internalConfig
    }

}
