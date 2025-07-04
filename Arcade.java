/*==================================================


File                     :  Arcade.java

date                     :  4/5/2025

Author                   :  Benedict Ward

Description              :  worth upto 25 marks, mainly linking up the types of arcade games
                            to customers

Possible Exceptions      :  InvalidCustomerException from getCustomer
                            InvalidGameIdException from getArcadeGame


History                  :  28/2/2025 v1.0 - 4:04 started, added the custom exception,
                                             made the constructors + getCustomer
                                             4:33 started testing getCustomer
                                             11:34pm adding functionality for getting the median
                            1/3/2025 v1.01 - created private acessor method getMedianGamePrice

                            13/3/2025 v1.02 - fixed getMedianPrice()
                                              the error was casting to a double after dividing the int
                                              when it should be casting to a double then dividing by 2.
                                              
                                              fixed countArcadeGames()
                                              now using .getClass().getSimpleName().equals instead of checking
                                              the toString output
                            
                            14/3/2025 v1.03 - added a toString method()

                            21/3/2025 v1.02 - added final keyword to the class

                            4/5/2025 v.1.1 - added hashmap for ArcadeGameCollection and customerCollection
==================================================*/
import java.util.Arrays;
import java.util.HashMap;


public final class Arcade {
    private final String arcadeName;
    private final HashMap<String, ArcadeGame> ArcadeGameCollection;
    private final HashMap<String, Customer> customerCollection;
    private int revenue;  // cant be final as revenue will change 

    public Arcade(String arcadeName){
        this.arcadeName = arcadeName;
        this.customerCollection = new HashMap<>();
        this.ArcadeGameCollection = new HashMap<>();
        this.revenue = 0;
    }

    public void addCustomer(Customer customer){
        this.customerCollection.put(customer.getName(),customer);
    }

    public void addArcadeGame(ArcadeGame arcadeGame){
        this.ArcadeGameCollection.put(arcadeGame.getGameId(),arcadeGame);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException{
        for (Customer elem : getCustomerCollection().values()) {
            if (elem.getAccountId().equals(customerID)) {
                return elem;
            }
        }
        throw new InvalidCustomerException("No customer found with the ID of " + customerID);
    }
    
    public ArcadeGame getArcadeGame(String gameId) throws InvalidGameIdException{
        if (this.ArcadeGameCollection.get(gameId) == null){
            throw new InvalidGameIdException("No game found with the ID of " + gameId);
        }
        return this.ArcadeGameCollection.get(gameId);
    }

    public Customer findRichestCustomer(){
        int highestBalance = -501;  // not setting it to 0 as Students can have -500
        Customer richestCustomer = null;
        for (Customer customer : getCustomerCollection().values()) {
            if (customer.getAccountBalance() > highestBalance){
                highestBalance = customer.getAccountBalance();
                richestCustomer = customer;
            }
        }
        return richestCustomer;
    }

    private HashMap<String,ArcadeGame> getArcadeGameCollection(){
        return this.ArcadeGameCollection;
    }

    public int getMedianGamePrice(){
        int[] allPrices = new int[getArcadeGameCollection().size()];
        int index = 0;
        for (ArcadeGame arcadeGameKey : getArcadeGameCollection().values()) {
            allPrices[index] = (arcadeGameKey.getPricePerPlay());
            index += 1;
        }

        Arrays.sort(allPrices);

        if (((double) (getArcadeGameCollection().size())) / 2 == getArcadeGameCollection().size() / 2) {
            // when there is an even amount of ArcadeGame machines
            return (allPrices[getArcadeGameCollection().size() / 2] + allPrices[(getArcadeGameCollection().size() + 1) / 2]) / 2;
        }
        else{
            // when there is an odd amount of ArcadeGame machines
            return allPrices[getArcadeGameCollection().size() / 2];
            
        }
    }
    
    public int[] countArcadeGames(){
        int totalCabinetGames = 0;
        int totalActiveGames = 0;
        int totalVirtualgames = 0;

        for (ArcadeGame arcadegame : getArcadeGameCollection().values()) {
            if (arcadegame.getClass().getSimpleName().equals("CabinetGame")){
                totalCabinetGames += 1;
            }

            if (arcadegame.getClass().getSimpleName().equals("ActiveGame")){
                totalActiveGames += 1;
            }

            if (arcadegame.getClass().getSimpleName().equals("VirtualRealityGame")){
                totalVirtualgames += 1;
            }
        }

        int[] toReturn = {totalCabinetGames, totalActiveGames ,totalVirtualgames};
        return toReturn;
    }
    
    public static void printCorporateJargon(){
        System.out.println("GamesCo does not take responsibility for any accidents or fits of rage that occur on the premises");
    }

    public String getArcadeName() {
        return this.arcadeName;
    }

    public int getRevenue() {
        return this.revenue;
    }
    
    public boolean processTransaction(String customerId, String gameId, boolean peak){
        ArcadeGame arcadeGameObj;
        Customer customer;
        int amountCharged;
        try {
            arcadeGameObj = getArcadeGame(gameId);
        } catch (InvalidGameIdException e) {
            System.out.println("[error]invalidGameIdException for id:"+gameId);
            return false;
        }
        
        try {
            customer = getCustomer(customerId);
        } catch (InvalidCustomerException e) {
            System.out.println("[error]InvalidCustomerException for customer id: "+ customerId);
            return false;
        }

        try {
            amountCharged = customer.chargeAccount(arcadeGameObj, peak);
        } catch (AgeLimitException exception) {
            System.out.println("[error pt1]AgeLimitException for {arcadegameobj:"+arcadeGameObj+", peak: "+peak+"}");
            System.out.println("[error pt2]the customers info is "+customer);
            return false;
        } catch (InsufficientBalanceException exception){
            System.out.println("[error pt1]InvalidCustomerException for {arcadegameobj:"+arcadeGameObj+", peak: "+peak+"}");
            System.out.println("[error pt2]the customers info is "+customer);
            return false;
        }

        this.revenue += amountCharged;
    return true;
    }

    public HashMap<String,Customer> getCustomerCollection(){
        return this.customerCollection;
    }

    @Override
    public String toString(){
        return "this is a Arcade object, arcadeName " + getArcadeName() + "ArcadeGameCollection size: " + getArcadeGameCollection().size() + ", customerCollection size: " + getCustomerCollection().size() + ", revenue: "+ getRevenue();
    }
    public static void main(String[] args){
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
        try {
            System.out.println(arcade.getCustomer("203685"));
            System.out.println(arcade.getCustomer("000000"));  // this line correctly throws an error
        } catch (InvalidCustomerException e) {
            System.out.println("caught an error: "+e);
        }

        try {
            ArcadeGame activeGame1 = new ActiveGame("AHW0HK1F01",100,"Foosball",3);
            ArcadeGame activeGame2 = new ActiveGame("AHW0HK1F02",90,"Foosball",3);
            ArcadeGame activeGame3 = new ActiveGame("AHW0HK1F03",80,"Foosball",3);
            ArcadeGame activeGame4 = new ActiveGame("AHW0HK1F04",70,"Foosball",3);
    
            
    
            arcade.addArcadeGame(activeGame1);
            arcade.addArcadeGame(activeGame2);
            arcade.addArcadeGame(activeGame3);
    

            System.out.println(arcade.getArcadeGame("AHW0HK1F01"));

            System.out.println("median:" + arcade.getMedianGamePrice());
    
            arcade.addArcadeGame(activeGame4);
    
            System.out.println("median:" + arcade.getMedianGamePrice());
        } catch (InvalidGameIdException e) {
            System.out.println(e);
        }
        System.out.println(arcade);
    }
}
