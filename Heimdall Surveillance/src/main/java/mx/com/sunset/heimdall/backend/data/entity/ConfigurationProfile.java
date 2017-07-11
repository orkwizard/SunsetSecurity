package mx.com.sunset.heimdall.backend.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class ConfigurationProfile extends AbstractEntity {

	@Size(max = 45)
	private String name;
	
	@Size(max = 45)
	private String database_server;
	
	@Min(0)
	private int port;
	
	@Size(max = 45)
	private String scheme;
	
	@Size(max = 45)
	private String driver_class;
	
	@Size(max = 200)
	private String email_server;
	
	@Size(max = 45)
	private String email_user;
	
	@Size(max = 45)
	private String email_password;
	
	@Min(0)
	private int email_port;
	
	@Size(max = 45)
	private String db_user;
	
	@Size(max = 45)
	private String db_password;
	
	@Size(max = 255)
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatabase_server() {
		return database_server;
	}

	public void setDatabase_server(String database_server) {
		this.database_server = database_server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getDriver_class() {
		return driver_class;
	}

	public void setDriver_class(String driver_class) {
		this.driver_class = driver_class;
	}

	public String getEmail_server() {
		return email_server;
	}

	public void setEmail_server(String email_server) {
		this.email_server = email_server;
	}

	public String getEmail_user() {
		return email_user;
	}

	public void setEmail_user(String email_user) {
		this.email_user = email_user;
	}

	public String getEmail_password() {
		return email_password;
	}

	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}

	public int getEmail_port() {
		return email_port;
	}

	public void setEmail_port(int email_port) {
		this.email_port = email_port;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_password() {
		return db_password;
	}

	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
}
