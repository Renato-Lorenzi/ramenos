package rml.ramenos.communication

import rml.ramenos.communication.socket.RamenosClientSocket
import rml.ramenos.communication.socket.RamenosServerSocket

class RamenosAPIFactory {

    static RamenosClient newClient() {
        return new RamenosClientSocket()
    }

    static RamenosServer newServer() {
        new RamenosServerSocket("localhost")
    }

}
