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

            // TODO check if this is allowed on the pass server
            case "headsetOnly" -> this.ControlType = EnumControlTypes.HEADSETONLY;
            case "fullBodyTracking" -> this.ControlType = EnumControlTypes.FULLBODYTRACKING;
            case "headsetAndController" -> this.ControlType = EnumControlTypes.HEADSETANDCONTROLLER;
        }

        //validating gameId
        if(!gameId.startsWith("AV")){
            throw new InvalidGameIdException("gameId invalid, does not start is a 'AV'.");
        }
        //TODO fix potental edge case where  the 10 characters are not all alphanumeric characters
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
        boolean canGetDiscounted = isPeakHour;
        int totalDiscount = 1;
        if (canGetDiscounted){
            if(this.isHeadsetOnly()){
                totalDiscount -= 0.10;
            }
            else if(this.isHeadsetAndController()){
                totalDiscount -= 0.05;
            }

        }
        return (int) Math.floor(this.pricePerPlay * totalDiscount);
    }

    @Override
    public String toString(){
        String toReturn = "This is a VirtualRealityGame obj, gameId: %,pricePerPlay: %, Name: %,ControlType: %";
        return String.format(toReturn, getGameId(), this.pricePerPlay, this.name, this.ControlType);
    }

    public static void main(String[] args) throws InvalidGameIdException {
        //Testing took place on 28/02/25 around 12-1:30
        //String gameId, int pricePerPlay, String Name, String ControlType

        // expected restult: error, InvalidgameId as gameId does not start with a AV everything else should be valid though
        //VirtualRealityGame gameIdTest1 = new VirtualRealityGame("gameId",200,"GAMENAME","headsetOnly");
        // given result: i was correct, "InvalidGameIdException: gameId invalid, does not start is a 'C'."

        // expected restult: error InvalidgameId as gameId does not contain 10 alphanumeric characters.
        //VirtualRealityGame gameIdTest2 = new VirtualRealityGame("CgameId",200,"GAMENAME","headsetOnly");
        // given result: i was incorrect, "InvalidGameIdException: gameId invalid, does not start is a 'C'.".
        // fix: simple spelling mistake and i will now input the correct gameId, 

        // expected result: will throw an error for invalid String length.
        //VirtualRealityGame gameIdTest3 = new VirtualRealityGame("AVgameId",200,"GAMENAME","headsetOnly");
        // given result: i was correct, "InvalidGameIdException: gameId invalid, does not contain exactly 10 alphanumeric characters."

        // expected restult: incorrectly passes, as i am incorrectly checking for alphanumeric characters by just checking the length.
        //VirtualRealityGame gameIdTest4 = new VirtualRealityGame("AV♀♂gameId",200,"GAMENAME","headsetOnly");
        // given restuls: i was correct, no error message means it passes when it shouldnt of.
        // fix: i will rework/make the function to check the alphanumeric characters instead of just using .length()


        // expected result: pass as this is all valid
        //VirtualRealityGame ControlTypeTest1 = new VirtualRealityGame("AVI1USPBNG", 0, "Virtual UEA Tour", "headsetOnly");
        //System.out.println(ControlTypeTest1.getControlType());
        // given result: i was correct, output is HEADSETONLY

        // expected result: fail as headsetOnly is incorrectly capitalised so no value is set
        //VirtualRealityGame ControlTypeTest2 = new VirtualRealityGame("AVI1USPBNG", 0, "Virtual UEA Tour", "hEaDsEtOnly");
        //System.out.println(ControlTypeTest2.getControlType());
        // given result: i was correct, output is null

        //error stats:
        // gameIdTest       expected pass rate : actual pass rate    (75%)
        //                                   4 : 3
        
        // ControlTypeTest  expected pass rate : actual pass rate    (100%)
        //                                   2 : 2   

        // where expected pass rate means i expect one result
        // and atual pass is when the result is the expected 
    }
}
