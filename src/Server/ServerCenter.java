package Server;

import java.util.ArrayList;

public class ServerCenter {
	private ArrayList<ServerChat> sCList = new ArrayList<>();
	private ArrayList<String> menuList = new ArrayList<>();
	private ArrayList<Member> memberList = new ArrayList<>();

	ServerCenter() {
		admin();
	}

	private void admin() {
		menuList.add("�Ƹ޸�ī�� 3000");
		menuList.add("ī��� 4000");
		menuList.add("īǪġ�� 5000");
		menuList.add("�ٴҶ�� 5000");
		menuList.add("������ 5000");

		Member m1 = new Member("aaa", "111");
		Member m2 = new Member("bbb", "222");
		Member m3 = new Member("ccc", "333");
		memberList.add(m1);
		memberList.add(m2);
		memberList.add(m3);
	}

	public void addSCList(ServerChat s) {
		sCList.add(s);
	}

	public void receiveMsg(String id, String msg) {
		if (msg.indexOf("1") == 0) {
			menuView(id);
		} else if (msg.indexOf("2") == 0) {
			shoppingBag(id);
		} else if (msg.indexOf("/order") == 0) {
			menuOrder(id, msg);
		} else {
			String sendMsg = "�߸��� �Է��Դϴ�.";
			for (int i = 0; i < sCList.size(); i++) {
				if (id.equals(sCList.get(i).getMyId())) {
					sCList.get(i).send(sendMsg);
				}
			}
		}
	}

	private void menuOrder(String id, String msg) {
		System.out.println("�޴� �ֹ�");

		int ed = msg.indexOf(" ", 0);
		String orderName = msg.substring(ed + 1);

		for (String m : menuList) {
			String menuListName = m.substring(0, ed);
			if (menuListName.indexOf(orderName) == 0) {
				for (int j = 0; j < sCList.size(); j++) {
					if (id.equals(sCList.get(j).getMyId())) {
						sCList.get(j).addOrder(m);
					}
				}
			}
		} 
		System.out.println(orderName);
	}

	private void shoppingBag(String id) {
		System.out.println("�ֹ� Ȯ��");

		String notice = "�ֹ��Ͻ� �޴��Դϴ� >>";
		for (int j = 0; j < sCList.size(); j++) {
			if (id.equals(sCList.get(j).getMyId())) {
				sCList.get(j).send(notice);
			}
		}
		
		for (int j = 0; j < sCList.size(); j++) {
			if (id.equals(sCList.get(j).getMyId())) {
				
				for (int i = 0; i < sCList.get(j).getOrder().size(); i++) {
					String orderMsg = (i + 1) + ". " + sCList.get(j).getOrder().get(i) + "\n";

					for (int k = 0; k < sCList.size(); k++) {
						if (id.equals(sCList.get(k).getMyId())) {
							sCList.get(k).send(orderMsg);
						}
					}
				}
			}
		}
	}

	private void menuView(String id) {
		System.out.println("�޴� ����");

		String notice = "[�ֹ� ���] ex) /order �Ƹ޸�ī��";
		for (int j = 0; j < sCList.size(); j++) {
			if (id.equals(sCList.get(j).getMyId())) {
				sCList.get(j).send(notice);
			}
		}

		for (int i = 0; i < menuList.size(); i++) {
			String menuMsg = (i + 1) + ". " + menuList.get(i) + "\n";

			for (int j = 0; j < sCList.size(); j++) {
				if (id.equals(sCList.get(j).getMyId())) {
					sCList.get(j).send(menuMsg);
				}
			}
		}
	}

	public void sendMenu(String id) {
		String menu = "1.�޴� ����  2.�ֹ� Ȯ��";

		for (int i = 0; i < sCList.size(); i++) {
			if (id.equals(sCList.get(i).getMyId())) {
				sCList.get(i).send(menu);
			}
		}
	}

	// �α��� ���̵� üũ
	public boolean idChk(String id) {
		for (Member m : memberList) {
			if (m.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	// �α��� �н����� üũ
	public boolean pwdChk(String id, String pwd) {
		for (Member m : memberList) {
			if (m.getId().equals(id) && m.getPwd().equals(pwd)) {
				return true;
			}
		}
		return false;
	}
}
