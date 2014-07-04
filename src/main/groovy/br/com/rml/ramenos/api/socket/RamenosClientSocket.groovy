package br.com.rml.ramenos.api.socket

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import br.com.rml.ramenos.api.RamenosClient

class RamenosClientSocket implements RamenosClient{

	public void accept(@ClosureParams(value=SimpleType.class,  options=["java.io.InputStream","java.io.OutputStream"
			])Closure closure) {
		new Socket("localhost", 8888).withStreams { input, output ->
			closure(input, output)
		}
	}
}
