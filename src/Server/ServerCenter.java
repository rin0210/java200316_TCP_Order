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
		menuList.add("아메리카노 3000");
		menuList.add("카페라떼 4000");
		menuList.add("카푸치노 5000");
		menuList.add("바닐라라떼 5000");
		menuList.add("녹차라떼 5000");

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
			String sendMsg = "잘못된 입력입니다.";
			for (int i = 0; i < sCList.size(); i++) {
				if (id.equals(sCList.get(i).getMyId())) {
					sCList.get(i).send(sendMsg);
				}
			}
		}
	}

	private void menuOrder(String id, String msg) {
		System.out.println("메뉴 주문");

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
		System.out.println("주문 확인");

		String notice = "주문하신 메뉴입니다 >>";
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
		System.out.println("메뉴 보기");

		String notice = "[주문 방법] ex) /order 아메리카노";
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
		String menu = "1.메뉴 보기  2.주문 확인";

		for (int i = 0; i < sCList.size(); i++) {
			if (id.equals(sCList.get(i).getMyId())) {
				sCList.get(i).send(menu);
			}
		}
	}

	// 로그인 아이디 체크
	public boolean idChk(String id) {
		for (Member m : memberList) {
			if (m.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	// 로그인 패스워드 체크
	public boolean pwdChk(String id, String pwd) {
		for (Member m : memberList) {
			if (m.getId().equals(id) && m.getPwd().equals(pwd)) {
				return true;
			}
		}
		return false;
	}
}
