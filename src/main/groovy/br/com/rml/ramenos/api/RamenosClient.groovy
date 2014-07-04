
package br.com.rml.ramenos.api

import groovy.transform.stc.ClosureParams

import groovy.transform.stc.SimpleType

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

public interface RamenosClient {

	void accept(@ClosureParams(value=SimpleType.class,  options=["java.io.InputStream","java.io.OutputStream"
	]) Closure closure)
}