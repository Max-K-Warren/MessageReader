import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Members {

    ArrayList<String> members;

    public Members(JsonArray memberJSON){
        members = new ArrayList<String>();
        for(int i =0; i<memberJSON.size(); i++){
            members.add(((JsonObject) memberJSON.get(i)).get("name").getAsString());
            //System.out.println(((JsonObject) memberJSON.get(i)).get("name").getAsString());
        }
    }
}
