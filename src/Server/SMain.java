package Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMain {

	public static void main(String[] args) throws Exception {
		ServerSocket serverS = null;
		Socket withClient = null;

		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.77", 9977));
		
		ServerCenter sc = new ServerCenter();

		while (true) {
			System.out.println("로그인 대기중~");
			withClient = serverS.accept();

			System.out.println(withClient.getInetAddress() + " 클라이언트 접속 됨");

			ServerChat s = new ServerChat(withClient);
			s.admin(sc);
			sc.addSCList(s);
			s.start();
		}

	}

}
