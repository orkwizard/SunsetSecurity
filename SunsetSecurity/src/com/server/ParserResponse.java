package com.server;

public class ParserResponse {

	private boolean result;
	private String sresult;
	private String comments;
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getSresult() {
		return sresult;
	}
	public void setSresult(String sresult) {
		this.sresult = sresult;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "ParserResponse [result=" + result + ", sresult=" + sresult + ", comments=" + comments + "]";
	}
	
	
}
