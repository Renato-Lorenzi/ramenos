package br.com.rml.ramenos.communication.namedpipe

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import br.com.rml.ramenos.communication.RamenosClient
import br.com.rml.ramenos.communication.helper.UnsupportedInputStream
import br.com.rml.ramenos.communication.helper.UnsupportedOutputStream


class NamedPipeClient implements RamenosClient {

	private RandomAccessFile pipe

	NamedPipeClient(String pipeName) {
		pipe = new RandomAccessFile(pipeName, "rw")
	}

	void accept(@ClosureParams(value=SimpleType.class,  options=["java.io.InputStream","java.io.OutputStream"
			]) Closure closure){
		closure(getInputStream(), getOutputStream())
	}

	InputStream getInputStream() {
		return new UnsupportedInputStream() {

			@Override
			int read(byte[] b) throws IOException {
				pipe.read(b)
			}

			@Override
			int read() throws IOException {
				pipe.read()
			}
		}
	}

	OutputStream getOutputStream() {
		return new UnsupportedOutputStream() {

			@Override
			void write(byte[] b, int off, int len) throws IOException {
				write(Arrays.copyOfRange(b, off, len))
			}

			@Override
			void write(byte[] b) throws IOException {
				pipe.write(b)
			}

			@Override
			void write(int b) throws IOException {
				pipe.writeByte(b)
			}
		}
	}

	void close() throws IOException {
		pipe.close()
	}
}
