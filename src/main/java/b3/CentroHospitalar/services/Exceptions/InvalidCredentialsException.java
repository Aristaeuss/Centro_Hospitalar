package b3.CentroHospitalar.services.Exceptions;

public class InvalidCredentialsException extends Exception {

    public enum Problem {
        NO_USERNAME_INPUTTED,
        USERNAME_DOES_NOT_EXIST,
        NO_PASSWORD_INPUTTED,
        INCORRECT_PASSWORD,
        UNKNOWN_ERROR;

        public String getDisplayMessage() {
            switch (this) {
                case NO_USERNAME_INPUTTED:
                    return "Por favor insira o username";
                case USERNAME_DOES_NOT_EXIST:
                    return "Não foi possível localizar o username indicado";
                case NO_PASSWORD_INPUTTED:
                    return "Por favor insira o password";
                case INCORRECT_PASSWORD:
                    return "Password incorrecta: <a href=\"forgotPassword\">Recuperar password</a>";
                case UNKNOWN_ERROR:
                    return "I don't know how you caused this error";
            }
            return null;
        }

        private String getMessage() {
            switch (this) {
                case NO_USERNAME_INPUTTED:
                    return "No username inputted";
                case USERNAME_DOES_NOT_EXIST:
                    return "Username does not exist";
                case NO_PASSWORD_INPUTTED:
                    return "No password inputted";
                case INCORRECT_PASSWORD:
                    return "Incorrect password";
                case UNKNOWN_ERROR:
                    return "Unknown error";
            }
            return null;
        }
    }

    private Problem problem;

    public InvalidCredentialsException (Problem problem) {
        super(problem.getMessage());
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }

}
