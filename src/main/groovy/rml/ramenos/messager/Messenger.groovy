package rml.ramenos.messager

import br.com.chat4e.ChatCallback
import groovy.util.logging.Log
import rml.ramenos.communication.RamenosAPIFactory

import java.nio.charset.Charset

import static java.util.logging.Level.SEVERE
import static rml.ramenos.messager.Messenger.ErrorKind.NOT_SHOW_MESSAGE
import static rml.ramenos.messager.Messenger.ErrorKind.PACKAGE_NOT_SUPPORTED

/**
 * Created by renatol on 7/3/14.
 */
@Log
class Messenger {


    Map<String, Buddy> buddies
    Thread thread

    private ChatCallback callback
    private self

    private enum PackageKind {
        MESSAGE, RECEIVED, ERROR
    }

    private enum ErrorKind {
        PACKAGE_NOT_SUPPORTED{

            def String toString() {
                "package kind not supported"
            }
        },

        NOT_SHOW_MESSAGE{
            def String toString() {
                "Was not possible to show the message"
            }
        }
    }

    Messenger(ChatCallback callback) {
        this.callback = callback
        buddies = new HashMap<String, Buddy>()
        buddies = Buddies.load()
        self = buddies[System.properties["user.name"]]
        start()
    }

    private def start() {
        def server = RamenosAPIFactory.newServer()
        thread = Thread.start {
            server.accept { input, output ->

                try {
                    def reader = new DataInputStream(input)
                    def kindByte = reader.readByte()
                    def values = PackageKind.values()

                    if (kindByte > values.size()) {
                        sendError(PACKAGE_NOT_SUPPORTED, output)
                        return
                    }

                    def size = reader.readInt()
                    byte[] bytes = new byte[size]
                    def readedSize = reader.read(bytes)

                    if (readedSize < size) {
                        log.severe "Problems to read package. Expected ${size} but readed ${readedSize}"
                        return
                    }

                    def message = Message.fromBytes(bytes)
                    try {
                        callback.receivedMessage(message.sender, message.message)
                    } catch (Exception e) {
                        log.log(SEVERE, e.message, e)
                        sendError(NOT_SHOW_MESSAGE, output)
                    }

                } catch (Exception e) {
                    log.log(SEVERE, e.message, e)
                }
            }
        }
    }

    def stop() {
        thread.stop()
    }

    def sendMessage(Buddy buddy, String message) {
        def messageDef = new Message(sender: self, message: message)
        def client = RamenosAPIFactory.newClient(buddy.machine)
        client.accept { input, output ->

            try {
                def out = new DataOutputStream(output)
                out.writeByte(PackageKind.MESSAGE.ordinal())
                def bytes = messageDef.toBytes()
                out.writeInt(bytes.length)
                out.write(bytes)

//                def reader = new DataInputStream(input)
//                def packageKind = reader.readByte()
//                if (packageKind != PackageKind.RECEIVED.ordinal()) {
//                    callback.sendFailed(packageKind.toString())
//                }
            } catch (Exception e) {
                log.log(SEVERE, e.message, e)
            }
        }
    }


    private def sendError(ErrorKind errorKind, OutputStream out) {
        def error = errorKind.toString()
        log.severe(error)
        DataOutputStream output = new DataOutputStream(out)
        output.writeByte(PackageKind.ERROR.ordinal())
        def bytes = error.getBytes(Charset.forName("ISO-8859-1"))
        output.writeInt(bytes.length)
        out.write(bytes)
    }

}
