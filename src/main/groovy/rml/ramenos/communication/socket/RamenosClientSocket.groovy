package rml.ramenos.communication.socket

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import rml.ramenos.communication.RamenosClient

class RamenosClientSocket implements RamenosClient {

    public void accept(@ClosureParams(value = SimpleType.class, options = ["java.io.InputStream", "java.io.OutputStream"
    ]) Closure closure) {
        def socket = new Socket("localhost", 8888)
        socket.soTimeout = 50000
        socket.withStreams { input, output ->
            closure(input, output)
        }
    }
}
