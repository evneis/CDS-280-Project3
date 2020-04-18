import CDS280Project3.csvLib.Address;
import CDS280Project3.csvLib.CSVLib;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    //Takes an Address object and converts it to JSON
    public static JSONObject addressToJSON(Address address){
        JSONObject out = new JSONObject();
        //Adding all the aspects of the address to the JSONObject
        out.put("street", address.street);
        out.put("city", address.city);
        out.put("state", address.state);
        out.put("zip", address.zip);

        return out;
    }

    //Converts a JSONObject back to Address
    public static Address JSONToAddress(JSONObject in) {
        //Getting all aspects of the Address object that was stored in the JSONObject
        String street = (String)in.get("street");
        String city = (String)in.get("city");
        String state = (String)in.get("state");
        String zip = (String)in.get("zip");
        //Returning these values in an Address
        return new Address(street, city, state, zip);
    }

    //Converting a single record to JSON
    public static JSONObject recordToJSON(HashMap<String, Object> record){
        JSONObject out = new JSONObject();
        //Adding all the entries of the record to the JSONObject
        out.put("StudentID", record.get("StudentID"));
        out.put("Major", record.get("Major"));
        out.put("Credits", record.get("Credits"));
        out.put("GPA", record.get("GPA"));
        //Since the address has its own class, we must call the addressToJSON method
        out.put("Address", addressToJSON((Address) record.get("Address")));

        return out;
    }

    //Converting the JSONObject back to a record
    public static HashMap<String, Object> JSONToRecord(JSONObject in){
        HashMap<String, Object> out = new HashMap<>();

        out.put("StudentID", in.get("StudentID"));
        out.put("Major", in.get("Major"));
        out.put("Credits", in.get("Credits"));
        out.put("GPA", in.get("GPA"));
        out.put("Address", JSONToAddress((JSONObject) in.get("Address")));

        return out;
    }

    //Converting the ArrayList of records to a JSONArray
    public static JSONArray allToJSON(ArrayList<HashMap<String, Object>> allStudents){
        JSONArray out = new JSONArray();

        //This will use our previous methods for each line in the ArrayList to add them to the JSONArray
        for(HashMap<String, Object> line : allStudents){
            out.add(recordToJSON(line));
        }
        return out;
    }

    //Converting JSONArray back to ArrayList
    public static ArrayList<HashMap<String, Object>> JSONToArray(JSONArray in){
        ArrayList<HashMap<String, Object>> out = new ArrayList<>();

        //Very similar code to add it back, just using the previous conversion methods
        for(Object line : in){
            out.add(JSONToRecord((JSONObject)line));
        }
        return out;
    }

    //Converting a record to JSONObject then writing to text file
    public static void recordToTxt(HashMap<String, Object> in)throws IOException {
        FileWriter out = new FileWriter("record1_json.txt");
        //using a recordToJSON to convert then writing it as String to file
        out.write(recordToJSON(in).toJSONString());
        out.close();
    }

    //Converting the whole ArrayList to a JSONArray then writing to a file
    public static void allToText(ArrayList<HashMap<String, Object>> in)throws IOException{
        FileWriter out = new FileWriter("student_json.txt");
        //Again, very similar to writing a single record
        out.write(allToJSON(in).toJSONString());
        out.close();
    }

    //Reading a single record from a text file
    public static HashMap<String,Object> readRecord(String filename)throws Exception{
        JSONParser parser = new JSONParser();
        FileReader read = new FileReader(filename);
        //This takes the record from the file as a JSONObject
        JSONObject json = (JSONObject) parser.parse(read);
        read.close();
        //We then convert the JSONObject to a HashMap
        HashMap<String, Object> out = JSONToRecord(json);
        return out;
    }

    //Reading in the whole JSONArray from a text file
    public static ArrayList<HashMap<String, Object>> readAll(String filename)throws Exception{
        JSONParser parser = new JSONParser();
        FileReader read = new FileReader(filename);
        //Reading in the text file as a JSONArray
        JSONArray json = (JSONArray)parser.parse(read);
        read.close();
        //Then converting that to an ArrayList to return
        ArrayList<HashMap<String, Object>> out = JSONToArray(json);
        return out;
    }

    //This method just utilizes the allToText abd readAll methods in a single method
    public static ArrayList<HashMap<String, Object>> writeAndRead(ArrayList<HashMap<String, Object>> in)throws Exception{
        allToText(in);
        return readAll("student_json.txt");
    }


    //Testing my code
    public static void main(String[] args)throws Exception{
        System.out.println(CSVLib.csvReader("Student.csv"));
        System.out.println(CSVLib.txtReader("Student.txt"));

        Address adTest = new Address("12 st", "Annville", "PA", "17003");
        JSONObject adJSONTest = addressToJSON(adTest);
        System.out.println(adJSONTest);
        Address back = JSONToAddress(adJSONTest);
        System.out.println(back);

        HashMap<String, Object> hashTest = new HashMap<>();
        hashTest.put("StudentID", 1);
        hashTest.put("Major", "CDS");
        hashTest.put("Credits", 45);
        hashTest.put("GPA", 3.40);
        hashTest.put("Address", adTest);

        JSONObject hashJSON = recordToJSON(hashTest);
        System.out.println(hashJSON);
        HashMap<String, Object> back2 = JSONToRecord(hashJSON);
        System.out.println(back2);

        JSONArray allJSON = allToJSON(CSVLib.csvReader("Student.csv"));
        System.out.println(allJSON);
        ArrayList<HashMap<String, Object>> allBack = JSONToArray(allJSON);
        System.out.println(allBack);

        recordToTxt(hashTest);
        allToText(CSVLib.txtReader("Student.txt"));
        System.out.println(readRecord("record1_json.txt"));
        System.out.println(readAll("student_json.txt"));

        allToText(CSVLib.csvReader("Student.csv"));
        System.out.println(readAll("student_json.txt"));
        System.out.println(writeAndRead(CSVLib.txtReader("Student.txt")));
    }
}
