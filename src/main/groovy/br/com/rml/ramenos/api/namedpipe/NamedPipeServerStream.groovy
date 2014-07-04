package br.com.rml.ramenos.api.namedpipe

import br.com.rml.jnamedpipe.JNAKernel32
import br.com.rml.ramenos.api.helper.UnsupportedInputStream
import br.com.rml.ramenos.api.helper.UnsupportedOutputStream

import com.sun.jna.ptr.IntByReference

public class NamedPipeServerStream  {

	private int hPipe
	private static JNAKernel32 k32lib = JNAKernel32.INSTANCE

	public NamedPipeServerStream(int hPipe) {
		super()
		this.hPipe = hPipe
	}

	public InputStream getInputStream() {
		return new UnsupportedInputStream() {
			@Override
			public int read(byte[] b) throws IOException {
				IntByReference cbBytesRead = new IntByReference()
				if (k32lib.ReadFile(hPipe, // handle to pipe
				b, // buffer to receive data
				b.length, // size of buffer
				cbBytesRead, // number of bytes read
				0)) {// not overlapped I/O;
					return 0
				}

				return b.length
			}
		}
	}

	public OutputStream getOutputStream() {
		return new UnsupportedOutputStream() {

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				write(Arrays.copyOfRange(b, off, len))
			}

			@Override
			public void write(byte[] b) throws IOException {
				IntByReference cbBytesRead = new IntByReference()
				k32lib.WriteFile(hPipe, b, b.length, cbBytesRead, 0)
			}

			@Override
			public void write(int b) throws IOException {
				write([(byte) b])
			}
		}
	}

	public void close() throws IOException {
		if (hPipe != JNAKernel32.INVALID_HANDLE_VALUE) {
			k32lib.CloseHandle(hPipe)
			hPipe = JNAKernel32.INVALID_HANDLE_VALUE
		}
	}

}
