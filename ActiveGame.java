/*==================================================


File             :  ActiveGame.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  this class and CabinetGame counts up to 10 marks 

History          :  28/2/2025 v1.0 - added code
                                     4:19pm fixed edge case where the characters 
                                     where not checked only the length was


==================================================*/



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}



public class ActiveGame extends ArcadeGame{
    private final int AgeRequirement;  // only needs accessor for this field not setter
    public ActiveGame(String GameID, int PricePerPlay, String Name, int AgeRequirement) throws InvalidGameIdException{
        super(GameID,PricePerPlay,Name);
        this.AgeRequirement = AgeRequirement;

        //validating GameID
        if(!GameID.startsWith("A")){
            throw new InvalidGameIdException("GameID invalid, does not start is a 'C'.");
        }
        //TODO fix potental edge case where  the 10 characters are not all alphanumeric characters
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
        boolean canGetDiscounted = !isPeakHour;


        if(canGetDiscounted){
            return (int) (this.PricePerPlay * 0.8);  // 20% discount
        }

        return this.PricePerPlay;
    }

    public int getAgeRequirement() {
        return AgeRequirement;
    }
}
