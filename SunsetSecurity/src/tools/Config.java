package tools;

public class Config {

	private int interval;
	private EmailConfig email_config;
	private DBConfig db_config;
	
	
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public EmailConfig getEmail_config() {
		return email_config;
	}
	public void setEmail_config(EmailConfig email_config) {
		this.email_config = email_config;
	}
	
	@Override
	public String toString() {
		return "Config [interval=" + interval + ", email_config=" + email_config + ", db_config=" + db_config + "]";
	}
	
	public DBConfig getDb_config() {
		return db_config;
	}
	public void setDb_config(DBConfig db_config) {
		this.db_config = db_config;
	}
	
}
