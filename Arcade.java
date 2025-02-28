/*==================================================


File                     :  Arcade.java

date                     :  28/2/2025

Author                   :  Benedict Ward

Description              :  worth upto 25 marks, mainly linking up the types of arcade games
                            to customers

Possible Exceptions      :  InvalidCustomerException
                            InvalidgameIdException


History                  :  28/2/2025 v1.0 - 4:04 started, added the custom exception,
                                             made the constructors + getCustomer
                                             4:33 started testing getCustomer
                                             5:51 back on the grind :3
                                             11:34pm adding functionality for getting the median
==================================================*/

import java.util.ArrayList;
import java.util.Arrays;

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
    private double revenue;
    private final ArrayList<ArcadeGame> ArcadeGameCollection;
    private final ArrayList<Customer> customerCollection;

    public Arcade(String arcadeName){
        this.arcadeName = arcadeName;
        this.customerCollection = new ArrayList<>();
        this.ArcadeGameCollection = new ArrayList<>();
        this.revenue = 0;
    }

    public void addCustomer(Customer customer){
        this.customerCollection.add(customer);
    }

    public void addArcadeGame(ArcadeGame arcadeGame){
        this.ArcadeGameCollection.add(arcadeGame);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        for (Customer elem : this.customerCollection) {
            if (elem.getAccountId().equals(customerID)) {
                return elem;
            }
        }
        throw new InvalidCustomerException("No customer found with the ID of " + customerID);
    }
    
    public ArcadeGame getArcadeGame(String gameId) throws InvalidGameIdException{
        for (ArcadeGame elem : this.ArcadeGameCollection) {
            if (elem.getGameId().equals(gameId)) {
                return elem;
            }
        }
        throw new InvalidGameIdException("No game found with the ID of " + gameId);
    }

    public Customer findRichestCustomer(){
        int highestBalance = -501;  // not setting it to 0 as Students can have -500
        Customer richestCustomer = null;
        for (Customer customer : this.customerCollection) {
            if (customer.getAccountBalance() > highestBalance){
                highestBalance = customer.getAccountBalance();
                richestCustomer = customer;
            }
        }
        return richestCustomer;
    }

    public int getMedianGamePrice(){
        //TODO comeback to this one later when we are shown better data structures
        int[] allPrices = new int[this.ArcadeGameCollection.size()];
        int index = 0;
        for (ArcadeGame arcadeGame : this.ArcadeGameCollection) {
            allPrices[index] = (arcadeGame.getPricePerPlay());
            index += 1;
        }

        Arrays.sort(allPrices);

        if ((double) (this.ArcadeGameCollection.size() / 2) != (int) (this.ArcadeGameCollection.size() / 2)) {
            System.out.println("they aint the same");
            // TODO add functionality for when it is this edge case
        }
        else{
            return allPrices[this.ArcadeGameCollection.size() / 2];
            
        }
        return -1;
    }
    
    public int[] countArcadeGames(){
        int totalArcadeGames = this.ArcadeGameCollection.size();

        int totalActiveGames = 0;
        for (ArcadeGame arcadegame : ArcadeGameCollection) {
            if (arcadegame.toString().contains("ActiveGame")){
                totalActiveGames += 1;
            }
        }

        int totalVirtualgames = 0;
        for (ArcadeGame arcadegame : ArcadeGameCollection) {
            if (arcadegame.toString().contains("VirtualRealityGame")){
                totalVirtualgames += 1;
            }
        }

        //TODO maybe fix this so toReturn is set to 3 maybe it already is.
        //int[] toReturn = new int[3];
        int[] toReturn = {totalArcadeGames, totalActiveGames ,totalVirtualgames};
        return toReturn;
    }
    
    public static void printCorporateJargon(){
        System.out.println("GamesCo does not take responsibility for any accidents or fits of rage that occur on the premises");
    }

    public String getArcadeName() {
        return arcadeName;
    }

    public double getRevenue() {
        return this.revenue;
    }
    
    public boolean processTransaction(String customerId, String gameId, boolean peak){
        ArcadeGame arcadeGameObj;
        Customer customer;
        int amountCharged;
        try {
            arcadeGameObj = getArcadeGame(gameId);
        } catch (InvalidGameIdException e) {
            return false;
        }
        
        try {
            customer = getCustomer(customerId);
        } catch (InvalidCustomerException e) {
            return false;
        }

        try {
            amountCharged = customer.chargeAcconut(arcadeGameObj, peak);
        } catch (InsufficientBalanceException | AgeLimitException e) {
            return false;
        }

        this.revenue -= amountCharged;
    return true;
    }

    public static void main(String[] args) throws InvalidCustomerException {
        
        // a test for the addCustomer along with getCustomer
        Customer customer1 = new Customer("748A66", "name1", 18, "STUDENT",500);
        Customer customer2 = new Customer("1C6498", "name2", 18, "STUDENT",500);
        Customer customer3 = new Customer("305459", "name3", 18, "STUDENT",500);
        Customer customer4 = new Customer("203685", "name4", 18, "STUDENT",500);
        Arcade arcade = new Arcade("arcadeName");
        arcade.addCustomer(customer1);
        arcade.addCustomer(customer2);
        arcade.addCustomer(customer3);
        arcade.addCustomer(customer4);
        System.out.println(arcade.getCustomer("203685"));
        System.out.println(arcade.getCustomer("000000"));  // this line correctly throws an error
    }
}
