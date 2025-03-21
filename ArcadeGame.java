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

                            13/3/2025 v1.02 - gameId,pricePerPlay,name attributes are all final
                                              removed the setter methods for those attributes
                            
                            19/3/2025 v1.03 - all attributes are now privated not protected
                                              and getter methods are all protected
==================================================*/



public abstract class ArcadeGame{
    private final String gameId;
    private final int pricePerPlay;
    private final String name;
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

    protected String getGameId() {
        return this.gameId;
    }

    protected int getPricePerPlay() {
        return this.pricePerPlay;
    }

    protected String getName() {
        return this.name;
    }
}