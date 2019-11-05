import java.util.Date;

public class Message {

    private String sender;
    private Date timeStamp;
    private String type;

    public Message(String sender, long timeStamp, String type) {
        this.sender = sender;
        //TODO: Read this as a long, get a datetime
        this.timeStamp = new Date(timeStamp);
        //Who cares yet
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getType() {
        return type;
    }
}
