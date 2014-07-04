package br.com.rml.ramenos.api

import br.com.rml.ramenos.api.socket.RamenosClientSocket
import br.com.rml.ramenos.api.socket.RamenosServerSocket

class RamenosAPIFactory {

    static RamenosClient newClient() {
        return new RamenosClientSocket()
    }

    static RamenosServer newServer() {
        new RamenosServerSocket("localhost")
    }

}
