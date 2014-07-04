package br.com.rml.ramenos.messager

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

/**
 * Created by renatol on 7/3/14.
 */
class Messeger {


    Map<String,Buddy> buddies

    Messeger() {
        buddies = Buddies.load()
    }

    def start(@ClosureParams(value = SimpleType.class, options = ["br.com.rml.ramenos.messager.Buddy",
            "java.lang.String"
    ]) Closure closure) {


    }

    def sendMessage(Buddy buddy, String message) {}

}
