package br.com.chat4e

import rml.ramenos.messager.Buddy;

/**
 * s
 *
 * @author renatol
 *
 */
interface ChatCallback {


    void receivedMessage(Buddy c, String s)

    void sendFailed(String s)
}
