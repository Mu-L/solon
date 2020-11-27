package org.noear.solon.boot.jdksocket;

import org.noear.solon.Utils;
import org.noear.solon.core.message.Message;
import org.noear.solon.core.message.Session;
import org.noear.solon.extend.xsocket.ListenerProxy;

import java.net.Socket;
import java.net.SocketException;

class BioClient {
    String host;
    int port;

    public BioClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Socket start() {
        try {
            Socket socket = new Socket(host, port);

            return socket;
        } catch (Exception ex) {
            throw Utils.throwableWrap(ex);
        }
    }

    public void startReceive(Session session, Socket socket) {
        Utils.pools.submit(() -> {
            while (true) {
                Message message = BioReceiver.receive(socket);

                if (message != null) {
                    ListenerProxy.getGlobal().onMessage(session, message, false);
                } else {
                    break;
                }
            }
        });
    }
}
