package main.Models.CONS;

public enum FighterStatus {
    SUCCESS("You are ready to fight!"),
    FAILURE("Unexpected error, please try again");

    private String _message;

    FighterStatus(String message) {
        _message = message;
    }

    public String Status() {
        return _message;
    }
}
