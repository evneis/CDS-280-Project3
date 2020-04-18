package CDS280Project3.csvLib;

//Address class takes street, city, state, and zip codes
public class Address {
    public String street;
    public String city;
    public String state;
    //I originally had the zip as an integer but when it was set as that
    //the program gave me trouble with reading from text files so I changed it to String
    public String zip;

    public Address(String s, String c, String st, String z){
        street = s;
        city = c;
        state = st;
        zip = z;
    }



    public String toString(){
        return street + ", " + city + ", " + state + " " + zip;
    }

}
