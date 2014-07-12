package rml.ramenos.messager

import com.google.gson.GsonBuilder
import groovy.json.JsonBuilder

/**
 * Created by renatol on 7/3/14.
 */
class Buddies {


    public static final String BUDDIES = "./.ramenos/.buddies"

    def static Map<String, Buddy> load() {
        def builder = new JsonBuilder()
        BuddyPOJO[] buddyPojos = new GsonBuilder().create().fromJson(new FileReader(BUDDIES), BuddyPOJO[].class)
        HashMap<String, Buddy> buddies = new HashMap<String, Buddy>()
        buddyPojos.each {
            buddies.put(it.userName, new Buddy(name: it.userName, userName: it.userName, machine: it.machine))
        }

        return buddies
    }

    def static save(Buddy[] buddies) {
        new FileWriter(BUDDIES).write(
                new GsonBuilder().create().toJson(buddies))

    }

    private static class BuddyPOJO {
        String userName
        String machine
    }
}

