package rml.ramenos.communication.socket

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import rml.ramenos.communication.RamenosServer

class RamenosServerSocket implements RamenosServer {

    private String serverName


    RamenosServerSocket(String server) {
        this.serverName = server
    }

    def accept(@ClosureParams(value = SimpleType.class, options = ["java.io.InputStream", "java.io.OutputStream"
    ]) Closure closure) {
        def server = new ServerSocket(8888)
        server.soTimeout = Integer.MAX_VALUE
        while (true) {
            server.accept({ Socket socket ->
                socket.withStreams { InputStream input, OutputStream output ->
                    closure(input, output)
                }
            })
        }
    }
}
