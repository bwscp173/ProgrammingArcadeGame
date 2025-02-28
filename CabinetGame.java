/*==================================================


File             :  CabinetGame.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  this class and ActiveGame counts up to 10 marks,

History          :  28/2/2025 v1.0 - added code
                                     4:17pm fixed edge case where the characters 
                                     where not checked only the length was


==================================================*/



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}


public class CabinetGame extends ArcadeGame{
    private final boolean givesReward;  // only needs a accessor method
    public CabinetGame(String GameID, int PricePerPlay, String Name, boolean givesReward) throws InvalidGameIdException{
        super(GameID,PricePerPlay,Name);
        
        this.givesReward = givesReward;

        //validating GameID
        if(!GameID.startsWith("C")){
            throw new InvalidGameIdException("GameID invalid, does not start is a 'C'.");
        }
        else if(!(isAllAlphanumeric(GameID) && (GameID.length() != 10))){
            throw new InvalidGameIdException("GameID invalid, does not contain exactly 10 alphanumeric characters.");
        }
    }

    private boolean isAllAlphanumeric(String str){
        // gets each character of a given String and
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (Character.isDigit(str.charAt(i))){
                return false;
            }
            if(Character.isLetter(str.charAt(i))){
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
        return (int) Math.floor(this.PricePerPlay * totalDiscount);
    };

    public boolean isGivesReward() {
        return this.givesReward;
    }

    @Override
    public String toString(){
        String toReturn = "This is a CabinetGame obj, GameID: %,PricePerPlay: %, Name: %,GiveReward: %";
        return String.format(toReturn, this.GameID, this.PricePerPlay, this.Name, this.givesReward);
    }

    public static void main(String[] args) throws InvalidGameIdException {
        //TODO testing here
        //CabinetGame cabinetGame = new CabinetGame("ID",200,"GAMENAME", true);
    }

}
