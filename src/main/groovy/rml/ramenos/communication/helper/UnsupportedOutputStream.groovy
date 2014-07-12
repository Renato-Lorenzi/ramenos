package rml.ramenos.communication.helper


class UnsupportedOutputStream extends OutputStream {

    @Override
    public void write(int arg0) throws IOException {
        throw new UnsupportedOperationException()
    }

    @Override
    public void write(byte[] b) throws IOException {
        throw new UnsupportedOperationException()
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        throw new UnsupportedOperationException()
    }

    @Override
    public void flush() throws IOException {
        throw new UnsupportedOperationException()
    }
}
