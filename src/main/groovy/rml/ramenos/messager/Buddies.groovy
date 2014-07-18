package rml.ramenos.messager

import com.google.gson.GsonBuilder
import groovy.json.JsonBuilder

/**
 * Created by renatol on 7/3/14.
 */
class Buddies {


    def static Map<String, Buddy> load() {
        HashMap<String, Buddy> buddies = new HashMap<String, Buddy>()
        //buddies[userName] = new Buddy(user: userName, machine: InetAddress.getLocalHost().getHostName())
        //internalLoad(buddies, Config.BUDDIES_DEFAULT_FILE)
        internalLoad(buddies, Config.BUDDIES_FILE)
        def userName = System.getenv("USERNAME")
        if (!buddies[userName]) {
            buddies[userName] = new Buddy(user: userName, machine: InetAddress.getLocalHost().getHostName())
            save(buddies.values() as Buddy[])
        }

        return buddies
    }


    def static save(Buddy[] buddies) {
        def file = Config.BUDDIES_FILE
        int tryCount
        if (file && file.exists()) {
            tryCount++
            try {
                def writer = new FileWriter(file)
                try {
                    writer.write(new GsonBuilder().create().toJson(buddies))
                } finally {
                    writer.close()
                }
            } catch (Exception e) {
                if (tryCount > 3) {
                    save(buddies)
                }
            }
        }
    }

    private static HashMap<String, Buddy> internalLoad(Map<String, Buddy> buddies, File buddiesFile) {
        if (!buddiesFile || !buddiesFile.exists()) {
            return buddies
        }
        def builder = new JsonBuilder()
        BuddyPOJO[] buddyPojos = new GsonBuilder().create().fromJson(new FileReader(buddiesFile), BuddyPOJO[].class)
        buddyPojos.each {
            buddies.put(it.user, new Buddy(user: it.user, machine: it.machine, email: it.email))
        }

        return buddies
    }

    private static class BuddyPOJO {
        String user
        String machine
        String email
    }

}

