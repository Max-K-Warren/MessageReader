import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages {

    private ArrayList<Message> messages;
    private HashMap<String, Reacts> reactions;

    public Messages(JsonArray memberJSON){
        messages = new ArrayList<>();
        this.setUpReactionMapping();
        for(int i =0; i<memberJSON.size(); i++){
            //System.out.println(((JsonObject) memberJSON.get(i)).get("sender_name").getAsString());
            String name = ((JsonObject) memberJSON.get(i)).get("sender_name").getAsString();
            long date = ((JsonObject) memberJSON.get(i)).get("timestamp_ms").getAsLong();
            String type = ((JsonObject) memberJSON.get(i)).get("type").getAsString();
            ArrayList<Reaction> messageReactions = new ArrayList<>();
            if(((JsonObject) memberJSON.get(i)).has("reactions")){
                for(JsonElement e :((JsonObject) memberJSON.get(i)).getAsJsonArray("reactions")) {
                    String reactionSender = ((JsonObject) e).get("actor").getAsString();
                    System.out.println(((JsonObject) e).get("reaction").getAsString());
                    Reacts emojiUsed = reactions.get( ((JsonObject) e).get("reaction").getAsString());
                    if(emojiUsed != null) {
                        System.out.println(emojiUsed.name());
                        messageReactions.add(new Reaction(reactionSender, emojiUsed));
                    }
                }
            }
            messages.add(new Message(name, date, type, messageReactions));
        }
    }

    public String mostReacts(){
        Message best = null;
        int most = 0;
        for(Message m: messages){
            if(m.getReacts().size() > most){
                best = m;
            }
        }
        if(best == null){
            return "no reacted messages";
        }

        String formatted = "Most Reacted message\n";
        formatted += ("Name: " + best.getSender() + "\n" );
        formatted += ("Message: " + "\n");
        formatted += ("Reacts: " + best.getReacts().size());
        return formatted;
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

    private void setUpReactionMapping(){
        reactions = new HashMap<>();
        reactions.put("\\u00e2\\u009d\\u00a4", Reacts.heart);
        reactions.put("\\u00f0\\u009f\\u0098\\u008d", Reacts.love);
        reactions.put("\\u00f0\\u009f\\u0091\\u008d", Reacts.thumbsUp);
        reactions.put("\\u00f0\\u009f\\u0091\\u008e", Reacts.thumbsDown);
        reactions.put("\\u00f0\\u009f\\u0098\\u00a2",Reacts.sad);
        reactions.put("\\u00f0\\u009f\\u0098\\u00a0", Reacts.angry);
        reactions.put("\\u00f0\\u009f\\u0098\\u00ae", Reacts.shocked);
        reactions.put("\\u00f0\\u009f\\u0098\\u0086", Reacts.laugh);


    }

}
