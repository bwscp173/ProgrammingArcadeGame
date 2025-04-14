/*==================================================


File                     :  CabinetGame.java

date                     :  14/4/2025

Author                   :  Benedict Ward

Description              :  this class and ActiveGame counts up to 10 marks,

Possible Exceptions      :  InvalidGameIdException from CabinetGame

History                  :  28/2/2025 v1.0 - added code
                                             4:17pm fixed edge case where the characters 
                                             where not checked only the length was
                                             9:11pm fixed the isAllAphanumeic function and logic using that value
                                             10:58pm fixed the toString method by removing the format function.

                            3/1/2025 v1.01 - moved the helper function isAllAlphanumeric
                                             to ArcadeGame where it get inherritted from
                                             fixed calculatePrice, missing ! for boolean logic and wrongful cast to int not double.

                            21/3/2025 v1.02 - added final keyword to the class

                            11/04/2025 v1.03 - better toString
==================================================*/


// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON
class InvalidGameIdException extends Exception{// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON
    public InvalidGameIdException(){}// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON
    public InvalidGameIdException(String message){// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON
        super(message);// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON
    }// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON
}// TODO REMOVE THIS IT WORKS WITHOUT IT JUST SHOWS ALOT OF ERRORS FOR SOME REASON


public final class CabinetGame extends ArcadeGame{
    private final boolean givesReward;  // only needs a accessor method
    public CabinetGame(String gameId, int pricePerPlay, String Name, boolean givesReward) throws InvalidGameIdException{
        super(gameId,pricePerPlay,Name);
        
        this.givesReward = givesReward;

        //validating gameId
        if(!gameId.startsWith("C")){
            throw new InvalidGameIdException("gameId invalid, does not start is a 'C'.");
        }
        else if(!(isAllAlphanumeric(gameId) && (gameId.length() == 10))){
            throw new InvalidGameIdException("gameId invalid, does not contain exactly 10 alphanumeric characters.");
        }
    }

    @Override
    protected int calculatePrice(boolean isPeakHour) {
        boolean canGetDiscounted = !isPeakHour;
        double totalDiscount = 1;
        if (canGetDiscounted){
            
            // 20% discount if the game gives out rewards
            // else 50%
            if (getGivesReward()){
                totalDiscount -= 0.20;
            }
            else{
                totalDiscount -= 0.50;
            }
        }
        // to round down
        return (int) Math.floor(getPricePerPlay() * totalDiscount);
    };

    public boolean getGivesReward() {
        return this.givesReward;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+"{gameId: "+this.getGameId()+", pricePerPlay: "+this.getPricePerPlay()+", Name: "+this.getName()+", GiveReward: "+this.getGivesReward()+"}";

    }

    public static void main(String[] args){
        // expected result: pass, as this is all typical data
        CabinetGame gameIdTest1;
        try{
            gameIdTest1 = new CabinetGame("CBGCR27FQM",200,"GAMENAME", true);
            System.out.println(gameIdTest1.toString());
        }catch(InvalidGameIdException e){
            System.out.println(e);
        }
        // actual result: i was correct, toString executed without error.


        // expected result: fail as gameId does not start with C
        CabinetGame gameIdTest2;
        try{
            gameIdTest2 = new CabinetGame("BBGCR27FQM",200,"GAMENAME", true);
            System.out.println(gameIdTest2.toString());
        }catch(InvalidGameIdException e){
            System.out.println(e);
        }
        // actual result: i was correct, "gameId invalid, does not start is a 'C'."
        
        // epected result: fail as gameId is too long.
        CabinetGame gameIdTest3;
        try{
            gameIdTest3 = new CabinetGame("CBGCR27FQMMMM",200,"GAMENAME", true);
            System.out.println(gameIdTest3.toString());
        }catch(InvalidGameIdException e){
            System.out.println(e);
        }
        // actual result: i was correct, "gameId invalid, does not contain exactly 10 alphanumeric characters."


        CabinetGame calculatePriceTest1;
        CabinetGame calculatePriceTest2;
        try{
            calculatePriceTest1 = new CabinetGame("CBGCR27FQM",200,"GAMENAME", true);
            calculatePriceTest2 = new CabinetGame("CBGCR27FQM",200,"GAMENAME", false);
            boolean isPeakHour = true;
            System.out.println("expected price of  200, actual price of " + calculatePriceTest1.calculatePrice(isPeakHour));  // 200
            System.out.println("expected price of  160, actual price of " + calculatePriceTest1.calculatePrice(!isPeakHour));  // 160
    
            System.out.println("expected price of  200, actual price of " + calculatePriceTest2.calculatePrice(isPeakHour));  // 200
            System.out.println("expected price of  100, actual price of " + calculatePriceTest2.calculatePrice(!isPeakHour));  //100
        }catch(InvalidGameIdException e){
            System.out.println(e);
        }
        // actual output was, 0,200 and 0,200
        // fix: missing ! when setting canGetDiscounted and was casting totalDiscount to int not double
        // causing any discount to set totalDiscount to 0
        // after re-running i get the output 200,160 and 200,100 as expected.
    }

}
