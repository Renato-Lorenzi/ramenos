package rml.ramenos.messager

import com.google.gson.GsonBuilder

import java.nio.charset.Charset

/**
 * Created by renatol on 7/4/14.
 */
class Message {

    Buddy sender
    String message

    def byte[] toBytes() {
        new GsonBuilder().create().toJson(this).getBytes(Charset.forName("ISO-8859-1"))
    }

    def static Message fromBytes(byte[] bytes) {
        new GsonBuilder().create().fromJson(new String(bytes, Charset.forName("ISO-8859-1")), Message.class)
    }


}
