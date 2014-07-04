package br.com.rml.ramenos.api.namedpipe

import static br.com.rml.jnamedpipe.JNAKernel32.PIPE_ACCESS_DUPLEX
import static br.com.rml.jnamedpipe.JNAKernel32.PIPE_READMODE_MESSAGE
import static br.com.rml.jnamedpipe.JNAKernel32.PIPE_TYPE_MESSAGE
import static br.com.rml.jnamedpipe.JNAKernel32.PIPE_UNLIMITED_INSTANCES
import static br.com.rml.jnamedpipe.JNAKernel32.PIPE_WAIT
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import br.com.rml.jnamedpipe.JNAKernel32
import br.com.rml.ramenos.api.RamenosServer

class NamedPipeServer implements RamenosServer{
	private JNAKernel32 k32lib = JNAKernel32.INSTANCE
	private String pipeName
	private Closure serverListener

	public NamedPipeServer(String pipeName) {
		super()
		this.pipeName = pipeName
		this.serverListener = serverListener
	}


	def accept(@ClosureParams(value=SimpleType.class,  options=["java.io.InputStream","java.io.OutputStream"
			]) Closure closure){
		while (true) {
			final int hPipe = k32lib.CreateNamedPipe(pipeName, // pipe
					// name
					PIPE_ACCESS_DUPLEX, // read/write access
					PIPE_TYPE_MESSAGE | // message type pipe
					PIPE_READMODE_MESSAGE | // message-read mode
					PIPE_WAIT, // blocking mode
					PIPE_UNLIMITED_INSTANCES, // max. instances
					512, // output buffer size
					512, // input buffer size
					0, // client time-out
					0) // default security attribute

			if (k32lib.ConnectNamedPipe(hPipe, 0)) {
				Thread.start {
					NamedPipeServerStream stream = new NamedPipeServerStream(hPipe)
					try {
						serverListener(stream)
					} finally {
						stream.close()
					}
				}
			}
		}
	}
}
