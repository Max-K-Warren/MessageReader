import com.google.gson.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MessageReader {

    public static void main(String[] args){

        String rawInput;
        if(args.length != 1){
            System.out.println("use the path as the argument");
            return;
        }
        try {
            rawInput = new Scanner(new File(args[0])).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        JsonObject fullTree = (JsonObject)JsonParser.parseString(rawInput);
        Members names = new Members((JsonArray) fullTree.get("participants"));
        Messages allMessages = new Messages((JsonArray) fullTree.get("messages"));

        System.out.println(allMessages.mostReacts());
        allMessages.findMemberCounts(names);

    }

}
