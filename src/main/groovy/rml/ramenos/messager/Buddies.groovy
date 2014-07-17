package rml.ramenos.messager

import com.google.gson.GsonBuilder
import groovy.json.JsonBuilder

/**
 * Created by renatol on 7/3/14.
 */
class Buddies {


    def static Map<String, Buddy> load() {
        HashMap<String, Buddy> buddies = new HashMap<String, Buddy>()
        def userName = System.getenv("USERNAME")
        buddies[userName] = new Buddy(user: userName, machine: InetAddress.getLocalHost().getHostName())
        internalLoad(buddies, Config.BUDDIES_DEFAULT_FILE)
        internalLoad(buddies, Config.BUDDIES_FILE)
        return buddies
    }


    def static save(Buddy[] buddies) {
        new FileWriter(Config.BUDDIES_DEFAULT_FILE).write(
                new GsonBuilder().create().toJson(buddies))

    }

    private static HashMap<String, Buddy> internalLoad(Map<String, Buddy> buddies, File buddiesFile) {
        if (!buddiesFile || !buddiesFile.exists()) {
            return buddies
        }
        def builder = new JsonBuilder()
        BuddyPOJO[] buddyPojos = new GsonBuilder().create().fromJson(new FileReader(buddiesFile), BuddyPOJO[].class)
        buddyPojos.each {
            buddies.put(it.userName, new Buddy(name: it.userName, userName: it.userName, machine: it.machine))
        }

        return buddies
    }

    private static class BuddyPOJO {
        String userName
        String machine
    }

}

