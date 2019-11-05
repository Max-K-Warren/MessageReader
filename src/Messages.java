import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages {

    private ArrayList<Message> messages;

    public Messages(JsonArray memberJSON){
        messages = new ArrayList<>();
        for(int i =0; i<memberJSON.size(); i++){
            //System.out.println(((JsonObject) memberJSON.get(i)).get("sender_name").getAsString());
            String name = ((JsonObject) memberJSON.get(i)).get("sender_name").getAsString();
            long date = ((JsonObject) memberJSON.get(i)).get("timestamp_ms").getAsLong();
            String type = ((JsonObject) memberJSON.get(i)).get("type").getAsString();
            messages.add(new Message(name, date, type));
        }
    }

    public String findMemberCounts(Members members){
        String listWithSortedCounts = "Chat member | number of messages\n";
        HashMap<String, Integer> freqMap = new HashMap<String, Integer>(64);
        for(Message m: messages){
            String name = m.getSender();
            freqMap.putIfAbsent(name, 0);
            freqMap.put(name, freqMap.get(name)+1);
        }
        ArrayList<MessageCount> numberOfMessages = new ArrayList<MessageCount>();
        for(Map.Entry<String, Integer> e: freqMap.entrySet()){
            numberOfMessages.add(new MessageCount(e.getKey(), e.getValue()));
        }
        numberOfMessages.sort(new CountComparison());
        for(MessageCount c: numberOfMessages){
            listWithSortedCounts += (c.name + "  |  " + c.count);
            //System.out.println(c.name + "  |  " + c.count);
        }
        return listWithSortedCounts;
    }
}
