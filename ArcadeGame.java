/*==================================================


File                     :  ArcadeGame.java

date                     :  28/2/2025

Author                   :  Benedict Ward

Description              :  this class counts up to 10 marks 
                            the simple constuctor accessor and mutator methods.
                            with the class it's self being abstract along with calculatePrice
                            for "basis for the cabinet game and active game subclasses".


                            
History                  :  28/2/2025 v1.0 - added the constructor and calculatePrice
                                             plus accessor and mutator methods.
                                             8:53pm added the this keyword in the accessor methods

                            1/3/2025 v1.01 - moved isAllAlphanumeric here as a protected function
                                             as all subclasses have the function
==================================================*/



public abstract class ArcadeGame{
    //TODO look into making these final and removing the mutator methods?
    protected String gameId;  // also known as serial number
    protected int pricePerPlay;
    protected String name;
    public ArcadeGame(String gameId, int pricePerPlay, String name){
        this.gameId = gameId;
        this.pricePerPlay = pricePerPlay;
        this.name = name;   
    }

    protected abstract int calculatePrice(boolean peak);

    protected boolean isAllAlphanumeric(String str){
        // gets each character of a given String and checks if its a digit or a letter this stops unique characters
        for (int i = 0; i < str.length(); i++) {
            if (!(Character.isDigit(str.charAt(i)) || Character.isLetter(str.charAt(i)))){
                return false;
            }
        }
        return true;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getPricePerPlay() {
        return this.pricePerPlay;
    }

    public void setPricePerPlay(int pricePerPlay) {
        this.pricePerPlay = pricePerPlay;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}