package rml.ramenos.messager

import groovy.transform.EqualsAndHashCode

/**
 * Created by renatol on 7/4/14.
 */

@EqualsAndHashCode
class Buddy {
    String user
    String machine
    String email


    def String toString() {
        user
    }

}
