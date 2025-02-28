/*==================================================


File             :  ArcadeGame.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  this class counts up to 10 marks 
                    the simple constuctor accessor and mutator methods.
                    with the class it's self being abstract along with calculatePrice
                    for "basis for the cabinet game and active game subclasses".

History          :  28/2/2025 v1.0 - added the constructor and calculatePrice
                                     plus accessor and mutator methods.


==================================================*/



public abstract class ArcadeGame{
    String GameID;  // also known as serial number
    int PricePerPlay;
    String Name;
    public ArcadeGame(String GameID, int PricePerPlay, String Name){
        this.GameID = GameID;
        this.PricePerPlay = PricePerPlay;
        this.Name = Name;   
    }

    protected abstract int calculatePrice(boolean peak);

    public String getGameID() {
        return GameID;
    }

    public void setGameID(String GameID) {
        this.GameID = GameID;
    }

    public int getPricePerPlay() {
        return PricePerPlay;
    }

    public void setPricePerPlay(int PricePerPlay) {
        this.PricePerPlay = PricePerPlay;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}