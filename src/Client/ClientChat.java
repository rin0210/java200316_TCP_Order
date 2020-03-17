package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
	private Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private Scanner in = new Scanner(System.in);
	private String id = null;

	public ClientChat(Socket withServer) {
		this.withServer = withServer;

		streamSet();
		receive();
		send();

	}

	private void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						reMsg = withServer.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						
						System.out.println(msg);

					} catch (Exception e) {
					}
				}
			}
		}).start();
	}

	private void send() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						String sMsg = in.nextLine();
						sendMsg = withServer.getOutputStream();
						sendMsg.write(sMsg.getBytes());

						if (sMsg.equals("/bye")) {
							System.out.println("연결이 종료됩니다.");
							sendMsg.close();
							reMsg.close();
							withServer.close();
							break;
						}
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}

	private void streamSet() {
		try {
			System.out.println("ID를 입력하세요 >>");
			id = in.nextLine();
			sendMsg = withServer.getOutputStream();
			sendMsg.write(id.getBytes());

			reMsg = withServer.getInputStream();
			byte[] reBuffer = new byte[100];
			reMsg.read(reBuffer);
			String msg = new String(reBuffer);
			msg = msg.trim();
			System.out.println(msg); 

		} catch (Exception e) {
		}
	}
}
