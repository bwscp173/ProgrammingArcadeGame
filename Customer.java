/*==================================================


File                     :  Customer.java

date                     :  28/2/2025

Author                   :  Benedict Ward

Description              :  worth up to 20 marks

Possible Exceptions      :  InsufficientBalanceException from chargeAccount
                            AgeLimitException from chargeAccount


History                  :  28/2/2025 v1.0 - finished coding at 3:30pm now doing testing
                                            3:50 found a 
                                            10:59pm fixed the toString method by removing the format function.

                            1/3/2025 v1.0 - chargeAccount now uses getAccountBalance() instead
                                            of this.accountBalance, same with age.

                            13/3/2025 v1.1 - now using .getClass().getSimpleName() to get
                                            the class name instead of checking the class's
                                            toString() result
                            
                            21/3/2025 v1.11 - added final keyword to the class
==================================================*/



class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(){}
    public InsufficientBalanceException(String message){
        super(message);
    }
}



class AgeLimitException extends Exception{
    public AgeLimitException(){}
    public AgeLimitException(String message){
        super(message);
    }
}



public final class Customer {
    private final String accountId;
    private final String Name;
    private final int Age;

    private final EnumPersonalDiscounts personalDiscount;
    private enum EnumPersonalDiscounts {NONE,STAFF,STUDENT}  //REMINDER students will be allowed a negative balance of upto -500
    private int accountBalance;  // 100 = £1
    
    public Customer(String accountId, String Name, int Age, String discountType){
        this.accountId = accountId;
        this.Name = Name;
        this.Age = Age;
        this.accountBalance = 0;

        //checking what discountType was given
        switch (discountType) {
            case "STUDENT" -> this.personalDiscount = EnumPersonalDiscounts.STUDENT;
            case "STAFF" -> this.personalDiscount = EnumPersonalDiscounts.STAFF;
            default -> this.personalDiscount = EnumPersonalDiscounts.NONE;
        }
    }

    public Customer(String accountId, String Name, int Age, String discountType, int initalBalance){
        this.accountId = accountId;
        this.Name = Name;
        this.Age = Age;
        // this math.max function is used so the value can not be smaller then 0 but can go anywhere higher.
        this.accountBalance = Math.max(0,initalBalance);

        //checking what discountType was given
        switch (discountType) {
            case "STUDENT" -> this.personalDiscount = EnumPersonalDiscounts.STUDENT;
            case "STAFF" -> this.personalDiscount = EnumPersonalDiscounts.STAFF;
            default -> this.personalDiscount = EnumPersonalDiscounts.NONE;
        }
    }

    public void AddFunds(int amount){
        //checking if positive
        if (0 < amount){
            this.accountBalance += amount;
        }
    }

    public int chargeAccount(ArcadeGame arcadeGameObj, boolean peakTime) throws InsufficientBalanceException, AgeLimitException{        
        double discountFactor = 1;
        boolean canGoNegative = false;

        // staff get 10% off, students get 5% off.
        if (isDiscountStaff()){
            discountFactor -= 0.10;
        }
        if (isDiscountStudent()){
            discountFactor -= 0.05;
            canGoNegative = true;
        }

        int fullPrice = arcadeGameObj.calculatePrice(peakTime);

        int price = (int) (Math.floor(fullPrice * discountFactor));
    
        if (0 < (getAccountBalance() - price) || (-500 < (getAccountBalance() - price) && canGoNegative)) {
            // the user has enough funds to pay

            // now checking if the arcadegameObj is either activegame,cabinetgame or virtualrealitygame then type casting it to a new variable
            //.getClass().getSimpleName() returns the class name
            if (arcadeGameObj.getClass().getSimpleName().equals("ActiveGame")){
                ActiveGame activeGameObj = (ActiveGame) arcadeGameObj;
                int ageRequirement = activeGameObj.getAgeRequirement();

                if (ageRequirement > getAge()){
                    throw new AgeLimitException("you must be at least " + ageRequirement + ", to play this game, you are only " + this.Age);
                }
            }

            this.accountBalance -= price;
            return price;
        }
        else{
            throw new InsufficientBalanceException("the price is," + price + ". and you only have, " + getAccountBalance());
        }
    }

    public boolean isDiscountNone(){
        return this.personalDiscount == EnumPersonalDiscounts.NONE;
    }

    public boolean isDiscountStaff(){
        return this.personalDiscount == EnumPersonalDiscounts.STAFF;
    }

    public boolean isDiscountStudent(){
        return this.personalDiscount == EnumPersonalDiscounts.STUDENT;
    }

    public String getAccountId(){
        return this.accountId;
    }
    public String getName(){
        return this.Name;
    }
    public int getAge(){
        return this.Age;
    }
    public int getAccountBalance(){
        return this.accountBalance;
    }
    private EnumPersonalDiscounts getPersonalDiscount(){
        return this.personalDiscount;
    }

    @Override
    public String toString(){
        return "This is a Customer object. accountID, "+getAccountId()+", name "+getName()+", age "+getAge()+", discounttype "+getPersonalDiscount()+", balance "+getAccountBalance();
    }

    public static void main(String[] args) throws InvalidGameIdException,InsufficientBalanceException, AgeLimitException {

        // this is a test for when given a valid arcadegame does charging the customer work correctly
        // expected result: it will loop 2 times like normal, on the 3rd it will throw a InsufficientBalanceException
        ArcadeGame ag = new ActiveGame("AL2ETWHG0Q", 200, "Name",18);
        Customer customer = new Customer("accountID", "Name", 18,"NONE",500);

        for (int i = 0; i < 4; i++) {
            try {
                customer.chargeAccount(ag, true);
                System.out.println(i +" : "+ customer.toString());    
            } catch (Exception e) {
                System.out.println("[Error]when charging account");
            }
        }
        // actual result: looped 3 times then gave an error because the pricePerPlay was discounted and i forgot to account for that 
        // correction: well it shouldnt of been discounted as its peakTime, 
        // fix: added a not to canGetDiscounted in ActiveGame
        // after re running it gave the expected result of looping 2 times, error on the 3rd.
        
        // same testing but the Customers discount type is now Student
        // expected result: it will manage to loop all 4 times with the balance going negative
        ArcadeGame ag2 = new ActiveGame("AL2ETWHG0Q", 200, "Name",18);
        Customer customer2 = new Customer("accountID", "Name", 18,"STUDENT",500);

        for (int i = 0; i < 4; i++) {
           customer2.chargeAccount(ag2, true);
           System.out.println(i +" : "+ customer2.toString());
        }
        // given result: i was correct, looped 4 times with balance going from 500->310->120->-70->-260
        // other notes: swapped out the 4 for a 7 and i only reached to 4 before the balance hit -450
        // meaning it couldnt go lower and error was thrown "InsufficientBalanceException: the price is,190. and you only have, -450"
    }

}