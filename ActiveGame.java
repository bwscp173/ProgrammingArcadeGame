/*==================================================


File                     :  ActiveGame.java

date                     :  28/2/2025

Author                   :  Benedict Ward

Description              :  this class and CabinetGame counts up to 10 marks 


Possible Exceptions      :  InvalidGameIdException


History                  :  28/2/2025 v1.0 - added code
                                            4:19pm fixed edge case where the characters 
                                            where not checked only the length was

                            28/2/2025 v1.01 - added possible exceptions in the header
                                              added a toString method
                                              9:08 fixed the isAllAphanumeic function and logic using that value


==================================================*/



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}



public class ActiveGame extends ArcadeGame{
    protected int ageRequirement;  // only needs accessor for this field not setter
    public ActiveGame(String gameId, int pricePerPlay, String name, int ageRequirement) throws InvalidGameIdException{
        super(gameId,pricePerPlay,name);
        this.ageRequirement = ageRequirement;

        //validating gameId
        if(!gameId.startsWith("A")){
            throw new InvalidGameIdException("gameId invalid, does not start is a 'C'.");
        }
        //TODO fix potental edge case where  the 10 characters are not all alphanumeric characters
        else if(!(isAllAlphanumeric(gameId) && (gameId.length() == 10))){
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

    public int getAgeRequirement() {
        return ageRequirement;
    }

    @Override
    protected int calculatePrice(boolean isPeakHour) {
        boolean canGetDiscounted = !isPeakHour;


        if(canGetDiscounted){
            return (int) (this.pricePerPlay * 0.8);  // 20% discount
        }

        return this.pricePerPlay;
    }

    @Override
    public String toString(){
        return "This is a ActiveGame obj. gameId "+getGameId()+", pricePerPlay "+getPricePerPlay()+", name "+getName()+", ageRequirement "+getAgeRequirement();
    }
}
