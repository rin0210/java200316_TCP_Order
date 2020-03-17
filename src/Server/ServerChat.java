package Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerChat extends Thread {
	private Socket withClient = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private String pwd = null;
	private ServerCenter sc = null;
	private ArrayList<String> orderList = new ArrayList<>();

	public ServerChat(Socket withClient) {
		this.withClient = withClient;
	}

	public void admin(ServerCenter sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		streamSet();
		receive();
	}

	private void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {

						reMsg = withClient.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();

						if (msg.equals("/bye")) {
							System.out.println("[" + id + "] 연결이 종료되었습니다.");
							reMsg.close();
							sendMsg.close();
							withClient.close();
							break;

						} else {
							System.out.println("[" + id + "] " + msg);
							sc.receiveMsg(id, msg);
						}
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}

	public void send(String m) {
		try {
			sendMsg = withClient.getOutputStream();
			sendMsg.write(m.getBytes());
		} catch (Exception e) {
		}
	}

	private void streamSet() {
		try {
			while (true) {

				reMsg = withClient.getInputStream();
				byte[] idBuffer = new byte[100];
				reMsg.read(idBuffer);
				id = new String(idBuffer);
				id = id.trim();

				if (sc.idChk(id)) {

					while (true) {

						String pwdMsg = "PASSWORD를 입력하세요 >>";
						sendMsg = withClient.getOutputStream();
						sendMsg.write(pwdMsg.getBytes());

						reMsg = withClient.getInputStream();
						byte[] pwdBuffer = new byte[100];
						reMsg.read(pwdBuffer);
						pwd = new String(pwdBuffer);
						pwd = pwd.trim();

						if (sc.pwdChk(id, pwd)) {
							InetAddress c_ip = withClient.getInetAddress();
							String ip = c_ip.getHostAddress();
							System.out.println(id + "님 로그인 (" + ip + ")");
							String reMsg = "정상적으로 로그인되었습니다.";
							sendMsg = withClient.getOutputStream();
							sendMsg.write(reMsg.getBytes());
							sc.sendMenu(id);
							break;

						} else {
							String reMsg = "PASSWORD가 일치하지 않습니다.";
							sendMsg = withClient.getOutputStream();
							sendMsg.write(reMsg.getBytes());
						}
					}
					break;
				} else {
					String reMsg = "해당 ID가 존재하지 않습니다.";
					sendMsg = withClient.getOutputStream();
					sendMsg.write(reMsg.getBytes());
					String reIdMsg = "ID를 입력하세요 >>";
					sendMsg = withClient.getOutputStream();
					sendMsg.write(reIdMsg.getBytes());
				}
			}

		} catch (Exception e) {
		}
	}

	public String getMyId() {
		return id;
	}
	
	public void addOrder(String o) {
		this.orderList.add(o);
	}

	public ArrayList<String> getOrder(){
		return orderList;
	}
}
