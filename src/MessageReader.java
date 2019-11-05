import com.google.gson.*;
import com.google.gson.stream.JsonReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageReader {

    public static void main(String[] args){

        String rawInput;
        if(args.length != 1){
            System.out.println("use the path as the argument");
            return;
        }
        try {
            rawInput = getData(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        JsonObject fullTree = (JsonObject)JsonParser.parseString(rawInput);
        Members names = new Members((JsonArray) fullTree.get("participants"));
        Messages allMessages = new Messages((JsonArray) fullTree.get("messages"));
        allMessages.findMemberCounts(names);
    }



    private static String getData(String file) throws FileNotFoundException{
        String content;
        content = new Scanner(new File(file)).useDelimiter("\\Z").next();
        return content;
    }
}
