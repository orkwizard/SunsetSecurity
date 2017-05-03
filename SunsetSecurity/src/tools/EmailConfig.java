package tools;

public class EmailConfig {
	
	private String email_to_send;
	private String email_usr;
	private String email_pwd;
	private String domain;
	private int port;
	
	
	public String getEmail_to_send() {
		return email_to_send;
	}
	public void setEmail_to_send(String email_to_send) {
		this.email_to_send = email_to_send;
	}
	public String getEmail_usr() {
		return email_usr;
	}
	public void setEmail_usr(String email_usr) {
		this.email_usr = email_usr;
	}
	public String getEmail_pwd() {
		return email_pwd;
	}
	public void setEmail_pwd(String email_pwd) {
		this.email_pwd = email_pwd;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "EmailConfig [email_to_send=" + email_to_send + ", email_usr=" + email_usr
				+ ", email_pwd=" + email_pwd + ", domain=" + domain + ", port=" + port + "]";
	}

}
