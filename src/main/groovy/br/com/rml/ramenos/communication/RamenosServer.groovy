package br.com.rml.ramenos.communication

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

public interface RamenosServer {
	def accept(@ClosureParams(value=SimpleType.class,  options=["java.io.InputStream","java.io.OutputStream"
	]) Closure closure)
}
