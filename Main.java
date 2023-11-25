import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.*;
public class Main {
    //For Testing Purposes
    public static void main(String[] args) {
        try {
            DatabaseConnector dc = new DatabaseConnector();
            HashMap<Integer, User> animalList = dc.readUser();
            Map.Entry<Integer,User> entry = animalList.entrySet().iterator().next();
            Integer key = entry.getKey();
            User value = entry.getValue();
           System.out.println(value.getName()); 
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
    }
}
