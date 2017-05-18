package tools;

public class DBConfig {

	private String connectionURL;
	private String dbUser;
	private String dbPassword;
	private String schema;
	private String driver;
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getConnectionURL() {
		return connectionURL;
	}
	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	@Override
	public String toString() {
		return "DBConfig [connectionURL=" + connectionURL + ", dbUser=" + dbUser + ", dbPassword=" + dbPassword
				+ ", schema=" + schema + "]";
	}
	
	
}
