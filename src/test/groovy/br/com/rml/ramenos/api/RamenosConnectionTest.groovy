package br.com.rml.ramenos.api

import org.junit.Test

class RamenosConnectionTest {

    @Test
    public void test() {
        def server = RamenosAPIFactory.newServer()

        def thread = Thread.start {
            server.accept { input, output ->
                byte[] bytes = new byte[10]
                input.read(bytes)
                println new String(bytes)
            }
        }

        sleep(1000)
        RamenosAPIFactory.newClient().accept { input, output ->
            output.write("1234567890".bytes)
        }

        thread.wait()


    }
}
