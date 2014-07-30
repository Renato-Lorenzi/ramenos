package rml.ramenos.communication.namedpipe

import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.LongByReference
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import rml.jnamedpipe.Advapi32
import rml.jnamedpipe.JNAKernel32
import rml.ramenos.communication.RamenosServer

import static rml.jnamedpipe.JNAKernel32.*

class NamedPipeServer implements RamenosServer {
    private JNAKernel32 k32lib = JNAKernel32.INSTANCE
    private Advapi32 advapi32 = Advapi32.INSTANCE
    private String pipeName

    public NamedPipeServer(String pipeName) {
        super()
        this.pipeName = pipeName
    }


    def accept(@ClosureParams(value = SimpleType.class, options = ["java.io.InputStream", "java.io.OutputStream"
    ]) Closure closure) {
        while (true) {
            def securityAttr = new IntByReference();
            advapi32.ConvertStringSecurityDescriptorToSecurityDescriptor("S:(ML;;NW;;;S-1-16-0)", 1, securityAttr, new LongByReference())
            SECURITY_ATTRIBUTES attr = new SECURITY_ATTRIBUTES()
            attr.lpSecurityDescriptor = securityAttr.value
            final int hPipe = k32lib.CreateNamedPipe(pipeName, // pipe
                    // name
                    PIPE_ACCESS_DUPLEX, // read/write access
                    PIPE_TYPE_MESSAGE | // message type pipe
                            PIPE_READMODE_MESSAGE | // message-read mode
                            PIPE_WAIT, // blocking mode
                    PIPE_UNLIMITED_INSTANCES | // max. instances
                            PIPE_ACCEPT_REMOTE_CLIENTS,//
                    512, // output buffer size
                    512, // input buffer size
                    0, // client time-out
                    attr) // default security attribute

            if (k32lib.ConnectNamedPipe(hPipe, 0)) {
                Thread.start {
                    NamedPipeServerStream stream = new NamedPipeServerStream(hPipe)
                    try {
                        closure(stream.inputStream, stream.outputStream)
                    } finally {
                        stream.close()
                    }
                }
            }
        }
    }
}
