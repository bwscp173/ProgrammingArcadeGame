/*==================================================


File             :  CabinetGame.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  this class and ActiveGame counts up to 10 marks,

History          :  28/2/2025 v1.0 - added code
                                     4:17pm fixed edge case where the characters 
                                     where not checked only the length was
                                     9:11pm fixed the isAllAphanumeic function and logic using that value

==================================================*/



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}


public class CabinetGame extends ArcadeGame{
    private final boolean givesReward;  // only needs a accessor method
    public CabinetGame(String gameId, int pricePerPlay, String Name, boolean givesReward) throws InvalidGameIdException{
        super(gameId,pricePerPlay,Name);
        
        this.givesReward = givesReward;

        //validating gameId
        if(!gameId.startsWith("C")){
            throw new InvalidGameIdException("gameId invalid, does not start is a 'C'.");
        }
        else if(!(isAllAlphanumeric(gameId) && (gameId.length() == 10))){
            System.out.println(isAllAlphanumeric(gameId) +":"+ (gameId.length() != 10) + gameId.length());
            throw new InvalidGameIdException("gameId invalid, does not contain exactly 10 alphanumeric characters.");
        }
    }

    private boolean isAllAlphanumeric(String str){
        // gets each character of a given String and
        for (int i = 0; i < str.length(); i++) {
            if (!(Character.isDigit(str.charAt(i)) || Character.isLetter(str.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    @Override
    protected int calculatePrice(boolean isPeakHour) {
        boolean canGetDiscounted = isPeakHour;
        int totalDiscount = 1;
        if (canGetDiscounted){
            
            // 20% discount if the game gives out rewards
            // else 50%
            if (this.givesReward){
                totalDiscount -= 0.20;
            }
            else{
                totalDiscount -= 0.50;
            }
        }
        // to round down
        return (int) Math.floor(this.pricePerPlay * totalDiscount);
    };

    public boolean isGivesReward() {
        return this.givesReward;
    }

    @Override
    public String toString(){
        String toReturn = "This is a CabinetGame obj, gameId: %,pricePerPlay: %, Name: %,GiveReward: %";
        return String.format(toReturn, this.gameId, this.pricePerPlay, this.name, this.givesReward);
    }

    public static void main(String[] args) throws InvalidGameIdException {
        //TODO testing here
        //CabinetGame cabinetGame = new CabinetGame("ID",200,"GAMENAME", true);
    }

}
