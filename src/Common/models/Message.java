package common.models;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 6461513247981074530L;

	MessageType type;
	Object obj;
	
	public Message(MessageType type, Object obj) {
		this.type = type;
		this.obj = obj;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public Object getObject() {
		return obj;
	}
}
