package models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SuccessMessage")
public class SuccessMessage {
	private boolean success;

    public SuccessMessage() {}

    public SuccessMessage(boolean success) {
        this.success = success;
    }

    @XmlElement(name = "success")
    public boolean getSuccessMessage() {
        return success;
    }

    public void setSuccessMessage(boolean success) {
        this.success = success;
    }
}
