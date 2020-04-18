package CDS280Project3.csvLib;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class CSVLib {
    //Imported text reader and CSV reader
    public static ArrayList<HashMap<String, Object>> txtReader(String filename) throws Exception{
        FileInputStream txt = new FileInputStream(filename);
        BufferedReader b = new BufferedReader(new InputStreamReader(txt));
        ArrayList<String> student = new ArrayList<>();
        ArrayList<ArrayList<String>> allStudents = new ArrayList<>();
        int row;
        String entry = "";
        b.readLine();

        while((row = b.read()) != -1){
            if(((char)row) == '\t') {
                student.add(entry);
                entry = "";
            }
            else if(((char)row) == '\n'){
                student.add(entry);
                allStudents.add(student);
                student = new ArrayList<>();
                entry = "";
            }
            else{
                entry += (char)row;
            }
        }
        //Converts to HashMap
        ArrayList<HashMap<String, Object>> hashArray = new ArrayList<>();
        for(ArrayList<String> line : allStudents){
            HashMap<String, Object> newLine = new HashMap<>();
            newLine.put("StudentID", Integer.parseInt(line.get(0)));
            newLine.put("Major", line.get(1));
            newLine.put("Credits", Integer.parseInt(line.get(2)));
            newLine.put("GPA", Double.parseDouble(line.get(3)));
            newLine.put("Address", new Address(line.get(4), line.get(5), line.get(6), line.get(7)));
            hashArray.add(newLine);
        }
        return hashArray;

    }

    public static ArrayList<HashMap<String, Object>> csvReader(String filename) throws Exception{
        FileInputStream csv = new FileInputStream(filename);
        BufferedReader b = new BufferedReader(new InputStreamReader(csv));
        ArrayList<String> student = new ArrayList<>();
        ArrayList<ArrayList<String>> allStudents = new ArrayList<>();
        int row;
        String entry = "";
        b.readLine();
        while((row = b.read()) != -1){
            if(((char)row) == ',' && student.size() < 8) {
                student.add(entry);
                entry = "";
            }
            else if(((char)row) == '\n'){
                student.add(entry);
                allStudents.add(student);
                student = new ArrayList<>();
                entry = "";
            }
            else{
                entry += (char)row;
            }
        }
        ArrayList<HashMap<String, Object>> hashArray = new ArrayList<>();
        for(ArrayList<String> line : allStudents){
            HashMap<String, Object> newLine = new HashMap<>();
            newLine.put("StudentID", Integer.parseInt(line.get(0)));
            newLine.put("Major", line.get(1));
            newLine.put("Credits", Integer.parseInt(line.get(2)));
            newLine.put("GPA", Double.parseDouble(line.get(3)));
            newLine.put("Address", new Address(line.get(4), line.get(5), line.get(6), line.get(7)));
            hashArray.add(newLine);
        }
        return hashArray;
    }

}
