package bestPackage;

import java.io.IOException;
import java.net.*;

public class Ping {

    public boolean isPinging(String ip) {
        try {
            InetAddress address;
            address = InetAddress.getByName(ip);
            if (address.isReachable(3000)) {
                System.out.println("ONLINE");
                return true;

            } else System.out.println("OFFLINE");
            return false;
        } catch (IOException e) {
            System.out.println("OFFLINE");
            return false;
        }
    }

    public boolean isPinging(String ip, int port) {
        SocketAddress sockaddr = new InetSocketAddress(ip, port);
        Socket socket = new Socket();
        boolean online = true;
        try {
            socket.connect(sockaddr, 3000);
        } catch (IOException IOException) {
            online = false;
        }
        if (!online) {
            System.out.println("OFFLINE");
            return false;
        }
        if (online) {
            System.out.println("ONLINE");
            return true;
        }
        return false;
    }

    public boolean isPingingFast(String ip, int port) {
        SocketAddress sockaddr = new InetSocketAddress(ip, port);
        Socket socket = new Socket();
        boolean online = true;
        try {
            socket.connect(sockaddr, 2100);
        } catch (IOException IOException) {
            online = false;
        }
        if (!online) {
            System.out.println("OFFLINE");
            return false;
        }
        if (online) {
            System.out.println("ONLINE");
            return true;
        }
        return false;
    }
}
