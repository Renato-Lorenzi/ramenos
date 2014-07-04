package br.com.rml.ramenos.communication

import br.com.rml.ramenos.communication.socket.RamenosClientSocket
import br.com.rml.ramenos.communication.socket.RamenosServerSocket

class RamenosAPIFactory {

    static RamenosClient newClient() {
        return new RamenosClientSocket()
    }

    static RamenosServer newServer() {
        new RamenosServerSocket("localhost")
    }

}
