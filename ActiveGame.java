/*==================================================


File                     :  ActiveGame.java

date                     :  11/4/2025

Author                   :  Benedict Ward

Description              :  this class and CabinetGame counts up to 10 marks 


Possible Exceptions      :  InvalidGameIdException from ActiveGame()


History                  :  28/2/2025 v1.0 - added code
                                            4:19pm fixed edge case where the characters 
                                            where not checked only the length was.

                            28/2/2025 v1.01 - added possible exceptions in the header
                                              added a toString method
                                              9:08 fixed the isAllAphanumeic function and logic using that value.

                            1/3/2025 v1.02 - added final keyword to ageRequirement
                                             finished testing in the main function.
                            
                            1/3/2025 v1.02 - moved isAllalphanumeric to ArcadeGame where
                                             it will be inherited from.
                                             calculatePrice now uses getPricePerPlay() not this.pricePerPlay.

                            13/3/2025 v1.03 - more checking in the constructor method
                                              for the gameId to not start with 'AV' as that is
                                              a different class. then removed this as virtualRealityGame
                                              extended from this class.
                            
                            19/3/2025 v1.04 - made ageRequirement private

                            11/04/2025 v1.05 - better toString
==================================================*/



public class ActiveGame extends ArcadeGame{
    private final int ageRequirement;  // only needs accessor for this field not setter
    public ActiveGame(String gameId, int pricePerPlay, String name, int ageRequirement) throws InvalidGameIdException{
        super(gameId,pricePerPlay,name);
        this.ageRequirement = ageRequirement;

        if(!gameId.startsWith("A")){
            throw new InvalidGameIdException("gameId invalid, does not start is a 'A'.");
        }
        else if(!(isAllAlphanumeric(gameId) && (gameId.length() == 10))){
            throw new InvalidGameIdException("gameId invalid, does not contain exactly 10 alphanumeric characters.");
        }
    }

    protected int getAgeRequirement() {
        return this.ageRequirement;
    }

    @Override
    protected int calculatePrice(boolean isPeakHour) {
        boolean canGetDiscounted = !isPeakHour;

        if(canGetDiscounted){
            return (int) (getPricePerPlay() * 0.8);  // 20% discount
        }

        return getPricePerPlay();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+"{gameId: "+this.getGameId()+", pricePerPlay: "+this.getPricePerPlay()+", Name: "+this.getName()+", ageRequirement: "+this.getAgeRequirement()+"}";
    }

    public static void main(String[] args) {
        
        // testing
        // expected result: pass, as it is given the data from the file
        try {
           ActiveGame gameIdTest1 = new ActiveGame("AHW0HK1F03",80,"Foosball",3);
           System.out.println(gameIdTest1.getAgeRequirement());
        } catch (InvalidGameIdException ex) {
           System.out.println("invalid gameid");
        }
        // actual result: i was correct, no error was raised
        
        // expected result: InvalidGameIdException will get raised
        try {
           ActiveGame gameIdTest2 = new ActiveGame("BHW0HK1F03",80,"Foosball",3);
           gameIdTest2.getName();
        } catch (InvalidGameIdException ex) {
           System.out.println("error" + ex);
        }
        // actual result: i was correct, an error was raised as gameId started with a B

        // expected result: InvalidGameIdException will get raised due to length.
        try {
           ActiveGame gameIdTest3 = new ActiveGame("AHW0HK1F033",80,"Foosball",3);
           gameIdTest3.getName();
        } catch (InvalidGameIdException ex) {
           System.out.println("error"+ex);
        }
        // actual result: i was correct an error was raised "gameId invalid, does not contain exactly 10 alphanumeric characters."


        // testing for calculatePrice with a valid ActiveGame
        ActiveGame validgame = null;
        try {
           validgame = new ActiveGame("AHW0HK1F03",80,"Foosball",3);
        } catch (InvalidGameIdException ex) {
           System.out.println("invalid gameid");
        }

        boolean isPeakHour=true;
        System.out.println("expected price 80, actual price :"+validgame.calculatePrice(isPeakHour));  // 80
        System.out.println("expected price 64, actual price :"+validgame.calculatePrice(!isPeakHour));  // 64
        // actual result: i was correct calculatePrice gave the expected price
    }
}
