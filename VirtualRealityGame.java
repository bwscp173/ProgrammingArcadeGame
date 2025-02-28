/*==================================================


File             :  VirtualRealityGame.java

date             :  28/2/2025

Author           :  Benedict Ward

Description      :  worth upto 5 marks, this class will handle all VR games the only unique
                    thing about this class is that ControlType is a enum of EnumControlTypes

History          :  28/2/2025 v1.0 - added all the code then did the testing as shown in the
                                     main when commented out


==================================================*/



public class VirtualRealityGame extends ActiveGame{

    private EnumControlTypes ControlType;
    private enum EnumControlTypes {  HEADSETONLY,
                                FULLBODYTRACKING,
                                HEADSETANDCONTROLLER};
    

    public VirtualRealityGame(String GameID, int PricePerPlay, String Name, String ControlType) throws InvalidGameIdException{
        super(GameID, PricePerPlay, Name, PricePerPlay);

        switch (ControlType) {
            // TODO check if this is allowed on the pass server
            case "headsetOnly" -> this.ControlType = EnumControlTypes.HEADSETONLY;
            case "fullBodyTracking" -> this.ControlType = EnumControlTypes.FULLBODYTRACKING;
            case "headsetAndController" -> this.ControlType = EnumControlTypes.HEADSETANDCONTROLLER;
        }

        //validating GameID
        if(!GameID.startsWith("AV")){
            throw new InvalidGameIdException("GameID invalid, does not start is a 'AV'.");
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
        return (int) Math.floor(this.PricePerPlay * totalDiscount);
    }

    @Override
    public String toString(){
        String toReturn = "This is a CabinetGame obj, GameID: %,PricePerPlay: %, Name: %,ControlType: %";
        return String.format(toReturn, this.GameID, this.PricePerPlay, this.Name, this.ControlType);
    }

    public static void main(String[] args) throws InvalidGameIdException {
        //Testing took place on 28/02/25 around 12-1:30
        //String GameID, int PricePerPlay, String Name, String ControlType

        // expected restult: error, InvalidGameID as gameID does not start with a AV everything else should be valid though
        //VirtualRealityGame GameIDTest1 = new VirtualRealityGame("GAMEID",200,"GAMENAME","headsetOnly");
        // given result: i was correct, "InvalidGameIdException: GameID invalid, does not start is a 'C'."

        // expected restult: error InvalidGameID as gameID does not contain 10 alphanumeric characters.
        //VirtualRealityGame GameIDTest2 = new VirtualRealityGame("CGAMEID",200,"GAMENAME","headsetOnly");
        // given result: i was incorrect, "InvalidGameIdException: GameID invalid, does not start is a 'C'.".
        // fix: simple spelling mistake and i will now input the correct GameID, 

        // expected result: will throw an error for invalid String length.
        //VirtualRealityGame GameIDTest3 = new VirtualRealityGame("AVGAMEID",200,"GAMENAME","headsetOnly");
        // given result: i was correct, "InvalidGameIdException: GameID invalid, does not contain exactly 10 alphanumeric characters."

        // expected restult: incorrectly passes, as i am incorrectly checking for alphanumeric characters by just checking the length.
        //VirtualRealityGame GameIDTest4 = new VirtualRealityGame("AV♀♂GAMEID",200,"GAMENAME","headsetOnly");
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
        // GameIDTest       expected pass rate : actual pass rate    (75%)
        //                                   4 : 3
        
        // ControlTypeTest  expected pass rate : actual pass rate    (100%)
        //                                   2 : 2   

        // where expected pass rate means i expect one result
        // and atual pass is when the result is the expected 
    }
}
