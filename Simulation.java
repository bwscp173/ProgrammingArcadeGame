import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {
    public static void main(String[] args) throws FileNotFoundException, InvalidGameIdException {
        
        File customersFile = new File("customers.txt");
        File arcadeGamesFile = new File("games.txt");
        File trasnactionsFile = new File("transactions.txt"); 
        initialiseArcade("arcadeName", arcadeGamesFile, customersFile);
    }

    public static Arcade initialiseArcade(String arcadeName, File gamesFile, File customerFile) throws FileNotFoundException, InvalidGameIdException{
        Arcade newArcadeObj = new Arcade(arcadeName);

        // file reading code originally from https://www.tutorialspoint.com/how-to-read-contents-of-a-file-using-scanner-class


        
        ArrayList<String> contents = new ArrayList<>();

        // code copied and then modified from https://www.w3schools.com/java/java_files_read.asp
        try {
            File myObj = gamesFile;  // changed the filename
            try(Scanner myReader = new Scanner(myObj)){
                while (myReader.hasNextLine()) {
                    String current_line = myReader.nextLine();
                    
                    contents.add(current_line);  // added this line

                }
                myReader.close();
                }
            }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        for (String elem : contents) {
            ArcadeGame arcadeGameToAdd = null;
            // each line goes Id, name,typeofgame, cost, age restriction
            // if vr then there is a extra tracking type
            // if cabinet then there is no age restriction
            String[] lineData = elem.split("@");
            String gameId = lineData[0];
            String gameName = lineData[1];
            String gameType = lineData[2];
            int pricePerPlay = Integer.parseInt(lineData[3]);
            
            System.out.println("gameId:"+ gameId);
            if (gameType.equals("virtualReality")){
                int ageRequirement = Integer.parseInt(lineData[4]);
                String trackingType = lineData[5];
                arcadeGameToAdd = new VirtualRealityGame(gameId, pricePerPlay, gameName, ageRequirement, trackingType);
                // try {
                //     arcadeGameToAdd = new VirtualRealityGame(gameId, pricePerPlay, gameName, ageRequirement, trackingType);
                // } catch (InvalidGameIdException e) {
                //     System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Warning Incorrect GameId given skipping1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //     continue;
                // }
                
            }
            else if(gameType.equals("cabinet")){
                boolean givesReward = lineData[4].equals("yes");
                try {
                    arcadeGameToAdd = new CabinetGame(gameId, pricePerPlay, gameName, givesReward);
                } catch (InvalidGameIdException e) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Warning Incorrect GameId given skipping2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    continue;
                }
            }
            else if(gameType.equals("active")){
                int ageRequirement = Integer.parseInt(lineData[4]);
                try {
                    arcadeGameToAdd = new ActiveGame(gameId, pricePerPlay, gameId, ageRequirement);
                } catch (InvalidGameIdException ex) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Warning Incorrect GameId given skipping3!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    continue;
                }
            }
            newArcadeObj.addArcadeGame(arcadeGameToAdd);

            System.out.println(gameId);
// rotected String gameId;  // also known as serial number
//     protected int pricePerPlay;
//     protected String name;
        }

        return newArcadeObj;
    }

    public static void simulateFun(Arcade arcade, File transactionFile){}
   
}
