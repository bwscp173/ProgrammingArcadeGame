/*==================================================


File                     :  exceptions.java

date                     :  14/4/2025

Author                   :  Benedict Ward

Description              :  worth 0 marks, just moving all the exceptions here so it
                            doesnt throw errors when uploaded to pass

Possible Exceptions      :  InvalidGameIdException
                            InsufficientBalanceException
                            AgeLimitException
                            InvalidCustomerException

History                  :  14/4/14 - moved all the exceptions here
==================================================*/



class InvalidGameIdException extends Exception{
    public InvalidGameIdException(String message){
        super(message);
    }
}

class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String message){
        super(message);
    }
}

class AgeLimitException extends Exception{
    public AgeLimitException(String message){
        super(message);
    }
}

class InvalidCustomerException extends Exception{
    public InvalidCustomerException(String message){
        super(message);
    }
}