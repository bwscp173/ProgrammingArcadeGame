class InvalidGameIdException extends Exception{
    public InvalidGameIdException(){}
    public InvalidGameIdException(String message){
        super(message);
    }
}

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

class InvalidCustomerException extends Exception{
    public InvalidCustomerException(){}
    public InvalidCustomerException(String message){
        super(message);
    }
}