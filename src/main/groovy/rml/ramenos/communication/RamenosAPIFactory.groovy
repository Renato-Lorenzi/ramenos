package rml.ramenos.communication

import rml.ramenos.communication.socket.RamenosClientSocket
import rml.ramenos.communication.socket.RamenosServerSocket

class RamenosAPIFactory {

    static RamenosClient newClient(String host) {
        return new RamenosClientSocket(host: host)
        //return new NamedPipeClient("\\\\${host}\\pipe\\ramenos_pipe")
    }

    static RamenosServer newServer() {
        //return new NamedPipeServer("\\\\.\\pipe\\ramenos_pipe")
        new RamenosServerSocket()
    }

}
