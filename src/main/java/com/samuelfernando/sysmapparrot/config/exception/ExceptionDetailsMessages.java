package com.samuelfernando.sysmapparrot.config.exception;

import java.util.List;

public class ExceptionDetailsMessages {
	private int status;
	private List<String> messages;
	
	public ExceptionDetailsMessages() {
	}

	public ExceptionDetailsMessages(int status, List<String> messages) {
		this.status = status;
		this.messages = messages;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
