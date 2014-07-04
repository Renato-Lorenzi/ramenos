package br.com.rml.ramenos



class Main {

	String subject
	String recipient
	String view

	def sendMail(Closure<Main> closure) {
		closure(this)
		println subject
		// Code to send the mail now that all the
		// various properties have been set
	}


	static doSomething(String src, @DelegatesTo(String.class) Closure cl){
	}
	static main(String [] ags){
		ArrayList<String> list = new ArrayList<String>()
		new Main().sendMail{Main mail -> mail.subject ="test" }
	}
}
