package rml.ramenos.communication.helper


abstract class UnsupportedInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException()
    }

    @Override
    public int read(byte[] b) throws IOException {
        throw new UnsupportedOperationException()
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        throw new UnsupportedOperationException()
    }

    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException()
    }

    @Override
    public synchronized void mark(int readlimit) {
        throw new UnsupportedOperationException()
    }

    @Override
    public boolean markSupported() {
        throw new UnsupportedOperationException()
    }
}
