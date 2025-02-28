
import java.util.ArrayList;

/*==================================================


File             :  Arcade.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  worth upto 25 marks

History          :  28/2/2025 v1.0 - 4:04 started, added the custom exception,
                                     made the constructors + getCustomer
                                     4:33 started testing getCustomer

==================================================*/



class InvalidCustomerException extends Exception{
    public InvalidCustomerException(){}
    public InvalidCustomerException(String message){
        super(message);
    }
}



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}



public class Arcade {
    private final String arcadeName;
    private final double revenue;
    private final ArrayList<ArcadeGame> AracdeGameCollection;
    private final ArrayList<Customer> customerCollection;

    public Arcade(String arcadeName){
        this.arcadeName = arcadeName;
        this.customerCollection = new ArrayList<Customer>();
        this.AracdeGameCollection = new ArrayList<ArcadeGame>();
        this.revenue = 0;
        
    }

    public void addCustomer(Customer customer){
        this.customerCollection.add(customer);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        for (Customer elem : this.customerCollection) {
            if (elem.getAccountID().equals(customerID)) {
                return elem;
            }
        }
        throw new InvalidCustomerException("No customer found with the ID of " + customerID);
    }
    //public ArcadeGame getArcadeGame(String gameId){}


    public void findRichestCustomer(){}
    public void getMedianGamePrice(){}
    public void countArcadeGames(){}
    public void printCorporateJargon(){}

    public static void main(String[] args) throws InvalidCustomerException {
        
        // a test for the addCustomer along with getCustomer
        Customer customer1 = new Customer("748A66", "Name1", 18, "STUDENT",500);
        Customer customer2 = new Customer("1C6498", "Name2", 18, "STUDENT",500);
        Customer customer3 = new Customer("305459", "Name3", 18, "STUDENT",500);
        Customer customer4 = new Customer("203685", "Name4", 18, "STUDENT",500);
        Arcade arcade = new Arcade("ARCADENAME");
        arcade.addCustomer(customer1);
        arcade.addCustomer(customer2);
        arcade.addCustomer(customer3);
        arcade.addCustomer(customer4);
        System.out.println(arcade.getCustomer("203685"));
        System.out.println(arcade.getCustomer("000000"));  // this line correctly throws an error
    }
}
