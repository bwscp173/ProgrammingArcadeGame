/*==================================================


File                     :  Arcade.java

date                     :  14/4/2025

Author                   :  Benedict Ward

Description              :  worth upto 25 marks, mainly linking up the types of arcade games
                            to customers

Possible Exceptions      :  

History                  :  28/2/2025 v1.0 - made the static helper function readFromFile
                                             initialiseArcade now reads from both given files
                                             11:09pm initialiseArcade now doesnt throw any errors
                                             and when prompted gave mantis toboggan as the richest customer
                                             11:18pm moved checking the gameType to a switch case statement
                            1/3/2025 v1.01 - fully account for possible Exception

                            14/4/2025 v1.02 - better FileNotFoundException handling
==================================================*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public final class Simulation {
    public static void main(String[] args){
        
        File customersFile = new File("customers.txt");
        File arcadeGamesFile = new File("games.txt");
        File transactionsFile = new File("transactions.txt"); 

        Arcade arcade = null;
        
        try {
            arcade = initialiseArcade("arcadeName", arcadeGamesFile, customersFile);
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]initialiseArcade cannot find file '"+arcadeGamesFile+"' and or '" +customersFile+"'.");
            System.exit(-1);
        }

        
        try {
            simulateFun(arcade, transactionsFile);
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]simulateFun cannot find file '"+transactionsFile+"'.");
            System.exit(-1);
        }


        System.out.println("\n\n==================================================");
        Arcade.printCorporateJargon();
        
        int[] stats = arcade.countArcadeGames();
        System.out.println("total number of cabinet games in this arcade: " + stats[0]);
        System.out.println("number of active games in this arcade (not including vr):" + stats[1]);
        System.out.println("number of virtual reality games in this arcade:" + stats[2]);
        
        System.out.println("the richest customer is: " + arcade.findRichestCustomer());
        
        //using printf as to always keep 2 decimal places 
        System.out.printf("the median price is: £%.2f\n", ((double)arcade.getMedianGamePrice()) / 100);

        System.out.println("the total revenue is: £" + ((double) arcade.getRevenue()) / 100);
        System.out.println("==================================================\n\n");
        
    }

    public static ArrayList<String> readFromFile(File fileObj) throws FileNotFoundException{
        ArrayList<String> contents = new ArrayList<>();
        // code copied and then modified from https://www.w3schools.com/java/java_files_read.asp

        try(Scanner myReader = new Scanner(fileObj)){
            while (myReader.hasNextLine()) {
                String current_line = myReader.nextLine();
                contents.add(current_line);  // added this line
            }
            myReader.close();
        }

        return contents;
    }

    public static Arcade initialiseArcade(String arcadeName, File gamesFile, File customerFile) throws FileNotFoundException{
        Arcade newArcadeObj = new Arcade(arcadeName);
        
        ArrayList<String> gamesFileContens = readFromFile(gamesFile);
        // for dealing with a
        for (String line : gamesFileContens) {
            ArcadeGame arcadeGameToAdd = null;
            // each line goes Id, name,typeofgame, cost, age restriction
            // if vr then there is a extra tracking type
            // if cabinet then there is no age restriction but a yes or no for giving out rewards.
            String[] lineData = line.split("@");
            String gameId = lineData[0];
            String gameName = lineData[1];
            String gameType = lineData[2];
            int pricePerPlay = Integer.parseInt(lineData[3]);
            int ageRequirement;

            // creating the virtualRealityGame/CabinetGame/ActiveGame objects to the expected ArcadeGame object, arcadeGameToAdd.
            switch (gameType) {
                case "virtualReality" -> {
                    ageRequirement = Integer.parseInt(lineData[4]);
                    String trackingType = lineData[5];
                    try {
                        arcadeGameToAdd = new VirtualRealityGame(gameId, pricePerPlay, gameName, ageRequirement, trackingType);
                    } catch (InvalidGameIdException e) {
                        System.out.println("[Error]VirtualRealityGame constructor" + e);
                        continue;
                    }
                }

                case "cabinet" -> {
                    boolean givesReward = lineData[4].equals("yes");
                    try {
                        arcadeGameToAdd = new CabinetGame(gameId, pricePerPlay, gameName, givesReward);
                    } catch (InvalidGameIdException e) {
                        System.out.println("[Error]CabinetGame constructor" + e);
                        continue;
                    }
                }

                case "active" -> {
                    ageRequirement = Integer.parseInt(lineData[4]);
                    try {
                        arcadeGameToAdd = new ActiveGame(gameId, pricePerPlay, gameId, ageRequirement);
                    } catch (InvalidGameIdException e) {
                        System.out.println("[Error]ActiveGame constructor" + e);
                        continue;
                    }
                }
            }

            if (arcadeGameToAdd != null){
                // will stay null if it couldnt initilise
                System.out.println("adding arcadegame: " + arcadeGameToAdd);
            }
            
            newArcadeObj.addArcadeGame(arcadeGameToAdd);
        }


        ArrayList<String> customerFileContens = readFromFile(customerFile);
        for (String elem : customerFileContens) {
            String[] lineData = elem.split("#");
            String accountId = lineData[0];
            String name = lineData[1];
            int initalBalance = Integer.parseInt(lineData[2]);
            int age = Integer.parseInt(lineData[3]);
            String discountType = "NONE";
            Customer customerToAdd;
            if (lineData.length == 5){
                discountType = lineData[4];
                customerToAdd = new Customer(accountId, name, age, discountType,initalBalance);
            }
            else{
                customerToAdd = new Customer(accountId, name, age, discountType, initalBalance);
            }
            newArcadeObj.addCustomer(customerToAdd);
        }
        
        return newArcadeObj;
    }

    public static void simulateFun(Arcade arcade, File transactionFile) throws FileNotFoundException{
        ArrayList<String> transactionFileContense = readFromFile(transactionFile);
        
        for (String line : transactionFileContense) {
            String[] lineData = line.split(",");
            String command = lineData[0];
            String customerId = lineData[1];
            switch (command) {
                case "PLAY" -> {
                    String gameId = lineData[2];
                    boolean peakTime = lineData[3].equals("PEAK");

                    arcade.processTransaction(customerId, gameId, peakTime);
                    System.out.println("processed transaction : {customerId: "+customerId+", gameId: "+gameId+", peakTime: "+peakTime+"}");
                }
                case "NEW_CUSTOMER" -> {
                    String name = lineData[2];
                    int age;
                    String discountType;
                    int initalBalance;

                    if (lineData.length == 5){
                        discountType = "NONE";
                        initalBalance = Integer.parseInt(lineData[3]);
                        age = Integer.parseInt(lineData[4]);
                    }
                    else{
                        discountType = lineData[3];
                        initalBalance = Integer.parseInt(lineData[4]);
                        age = Integer.parseInt(lineData[5]);
                    }

                    Customer newCustomer = new Customer(customerId, name, age, discountType, initalBalance);
                
                    arcade.addCustomer(newCustomer);
                    System.out.println("added new customer: "+newCustomer.toString());
                }
                case "ADD_FUNDS" -> {
                    int moneyToAdd = Integer.parseInt(lineData[2]);
                    
                    try {
                        arcade.getCustomer(customerId).AddFunds(moneyToAdd);
                        System.out.println("successfully added funds: {customerId: "+customerId+", moneyToAdd: "+moneyToAdd+"}");
                    } catch (InvalidCustomerException ex) {
                        System.out.println("[Error]from getCustomer: " + ex);

                        System.out.println("could not add £"+(double)moneyToAdd /100+" as we could not find that Id.");
                    }
                }

            }
        }
    }
   
}
