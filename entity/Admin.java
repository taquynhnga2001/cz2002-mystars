package entity;

import java.io.*;
import java.util.ArrayList;

import text_manager.*;

public class Admin extends User {

	private String name;
	private String mail;
	private String phoneNum;

	private final int nameIdx = 0;
	private final int mailIdx = 1;
	private final int phoneNumIdx = 2;


	public Admin(String username, String password) throws IOException {
		super(username, password);
		try {
			ArrayList<String> attributes = AdminTextMng.readAdmin(username, password);
			this.name = attributes.get(nameIdx);
			this.mail = attributes.get(mailIdx);
			this.phoneNum = attributes.get(phoneNumIdx);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}
	public String getMail() {
		return mail;
	}
	public String getPhoneNume() {
		return phoneNum;
	}

}
