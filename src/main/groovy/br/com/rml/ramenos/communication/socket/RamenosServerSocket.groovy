package br.com.rml.ramenos.communication.socket

import br.com.rml.ramenos.communication.RamenosServer
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

class RamenosServerSocket implements RamenosServer {

    private String serverName


    RamenosServerSocket(String server) {
        this.serverName = server
    }

    def accept(@ClosureParams(value = SimpleType.class, options = ["java.io.InputStream", "java.io.OutputStream"
    ]) Closure closure) {
        def server = new ServerSocket(8888)
        while (true) {
            server.accept({ Socket socket ->
                socket.withStreams { InputStream input, OutputStream output ->
                    closure(input, output)
                }
            })
        }
    }
}
