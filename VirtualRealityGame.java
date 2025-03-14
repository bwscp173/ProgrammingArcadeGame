/*==================================================


File             :  VirtualRealityGame.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  worth upto 5 marks, this class will handle all VR games the only unique
                    thing about this class is that ControlType is a enum of EnumControlTypes

History          :  28/2/2025 v1.0 - added all the code then did the testing as shown in the
                                     main when commented out
                                     6:05pm fixed the tostring saying cabinetgame obj.
                                     8:036pm fixed the constructor not asking for ageRequirement
                                     and just parsing in pricePerPlay twise
                                     9:11 fixed the isAllAphanumeic function and logic using that value
                                     10:58pm fixed the toString method by removing the format function.

                    1/3/2025 v1.0 - removed isAllAlphanumeric as it gets inherited from ArcadeGame
==================================================*/



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}



public class VirtualRealityGame extends ActiveGame{

    private EnumControlTypes ControlType;
    private enum EnumControlTypes {  HEADSETONLY,
                                    FULLBODYTRACKING,
                                    HEADSETANDCONTROLLER};
    

    public VirtualRealityGame(String gameId, int pricePerPlay, String Name, int ageRequirement, String ControlType) throws InvalidGameIdException{
        super(gameId, pricePerPlay, Name, ageRequirement);

        switch (ControlType) {
            case "headsetOnly" -> this.ControlType = EnumControlTypes.HEADSETONLY;
            case "fullBodyTracking" -> this.ControlType = EnumControlTypes.FULLBODYTRACKING;
            case "headsetAndController" -> this.ControlType = EnumControlTypes.HEADSETANDCONTROLLER;
        }

        //validating gameId
        if(!gameId.startsWith("AV")){
            throw new InvalidGameIdException("gameId invalid, does not start is a 'AV'.");
        }
        else if(!(isAllAlphanumeric(gameId) && (gameId.length() == 10))){
            throw new InvalidGameIdException("gameId invalid, does not contain exactly 10 alphanumeric characters.");
        }
    }

    public boolean isHeadsetOnly(){
        return this.ControlType == EnumControlTypes.HEADSETONLY;
    }

    public boolean isFullBodyTracking(){
        return this.ControlType == EnumControlTypes.FULLBODYTRACKING;
    }

    public boolean isHeadsetAndController(){
        return this.ControlType == EnumControlTypes.HEADSETANDCONTROLLER;
    }

    public EnumControlTypes getControlType(){
        return this.ControlType;
    }

    @Override
    protected int calculatePrice(boolean isPeakHour) {
        boolean canGetDiscounted = !isPeakHour;
        double totalDiscount = 1;
        if (canGetDiscounted){
            if(isHeadsetOnly()){
                totalDiscount -= 0.10;
            }
            else if(isHeadsetAndController()){
                totalDiscount -= 0.05;
            }
        }
        return (int) Math.floor(getPricePerPlay() * totalDiscount);
    }

    @Override
    public String toString(){
        return "This is a VirtualRealityGame obj, gameId: "+getGameId()+",pricePerPlay: "+getPricePerPlay()+", Name: "+getName()+",ControlType: " +getControlType();
    }

    public static void main(String[] args){
        //Testing took place on 28/02/25 around 12-1:30

        // expected restult: error, InvalidgameId as gameId does not start with a AV everything else should be valid though
        try {
            VirtualRealityGame gameIdTest1 = new VirtualRealityGame("gameId",200,"GAMENAME",0,"headsetOnly");    
        } catch (Exception e) {
            System.out.println(e);
        }
        // given result: i was correct, "InvalidGameIdException: gameId invalid, does not start is a 'C'."

        // expected restult: error InvalidgameId as gameId does not contain 10 alphanumeric characters.
        try {
            VirtualRealityGame gameIdTest2 = new VirtualRealityGame("CgameId",200,"GAMENAME",0,"headsetOnly");
        } catch (Exception e) {
            System.out.println(e);
        }
        // given result: i was incorrect, "InvalidGameIdException: gameId invalid, does not start is a 'C'.".
        // fix: simple spelling mistake and i will now input the correct gameId, 

        // expected result: will throw an error for invalid String length.
        try {
            VirtualRealityGame gameIdTest3 = new VirtualRealityGame("AVgameId",200,"GAMENAME", 0,"headsetOnly");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        // given result: i was correct, "InvalidGameIdException: gameId invalid, does not contain exactly 10 alphanumeric characters."

        // expected restult: incorrectly passes, as i am incorrectly checking for alphanumeric characters by just checking the length.
        try {
            VirtualRealityGame gameIdTest4 = new VirtualRealityGame("AV♀♂gameId",200,"GAMENAME", 0,"headsetOnly");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // given restuls: i was correct, no error message means it passes when it shouldnt of.
        // fix: i will rework/make the function to check the alphanumeric characters instead of just using .length()


        // expected result: pass as this is all valid
        VirtualRealityGame ControlTypeTest1;
        try {
            ControlTypeTest1 = new VirtualRealityGame("AVI1USPBNG", 0, "Virtual UEA Tour", 0, "headsetOnly");
            System.out.println(ControlTypeTest1.getControlType());
        } catch (InvalidGameIdException ex) {
            System.out.println(ex);
        }
        // given result: i was correct, output is HEADSETONLY

        // expected result: fail as headsetOnly is incorrectly capitalised so no value is set
        VirtualRealityGame ControlTypeTest2;
        try {
            ControlTypeTest2 = new VirtualRealityGame("AVI1USPBNG", 0, "Virtual UEA Tour", 0, "hEaDsEtOnly");
            System.out.println(ControlTypeTest2.getControlType());
        } catch (InvalidGameIdException ex) {
            System.out.println(ex);
        }
        // given result: i was correct, output is null


        //testing calculatePrice when given a valid VirtualRealityGame object
        VirtualRealityGame calculatePriceTest1 = null;
        VirtualRealityGame calculatePriceTest2 = null;
        VirtualRealityGame calculatePriceTest3 = null;
        try {
            calculatePriceTest1 = new VirtualRealityGame("AVI1USPBNG", 100, "Virtual UEA Tour",0, "headsetOnly");
            calculatePriceTest2 = new VirtualRealityGame("AVI1USPBNG", 100, "Virtual UEA Tour",0, "fullBodyTracking");
            calculatePriceTest3 = new VirtualRealityGame("AVI1USPBNG", 100, "Virtual UEA Tour",0, "headsetAndController");
        } catch (InvalidGameIdException e) {
            System.out.println(e);
        }

        // boolean isPeakHour = true;
        // System.out.println("expected price of  100, actual price of " + calculatePriceTest1.calculatePrice(isPeakHour));  //100
        // System.out.println("expected price of  90, actual price of " + calculatePriceTest1.calculatePrice(!isPeakHour));  // 90

        // System.out.println("expected price of  100, actual price of " + calculatePriceTest2.calculatePrice(isPeakHour));  // 100
        // System.out.println("expected price of  100, actual price of " + calculatePriceTest2.calculatePrice(!isPeakHour));  // 100

        // System.out.println("expected price of  100, actual price of " + calculatePriceTest3.calculatePrice(isPeakHour));  // 100
        // System.out.println("expected price of  95, actual price of " + calculatePriceTest3.calculatePrice(!isPeakHour));  // 95
        //fix: missing ! when setting canGetDiscounted + wrongful cast to int for totalDiscount. now casts to double
        // after these corrections i get the correct output of 100,90 and 100,100 and 100,95




        //error stats:
        // gameIdTest       expected pass rate : actual pass rate    (75%)
        //                                   4 : 3
        
        // ControlTypeTest  expected pass rate : actual pass rate    (100%)
        //                                   2 : 2   

        // where expected pass rate means i expect one result
        // and atual pass is when the result is the expected 
    }
}
