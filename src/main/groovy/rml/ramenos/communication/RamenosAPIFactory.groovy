package rml.ramenos.communication

import rml.ramenos.communication.socket.RamenosClientSocket
import rml.ramenos.communication.socket.RamenosServerSocket

class RamenosAPIFactory {

    static RamenosClient newClient(String host) {
        return new RamenosClientSocket(host: host)
    }

    static RamenosServer newServer() {
        new RamenosServerSocket()
    }

}
