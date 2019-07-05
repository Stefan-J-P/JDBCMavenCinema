package maven.jdbc.cinema.exceptions;
import java.time.LocalDateTime;

public class MyException extends RuntimeException
{
    private ExceptionInfo exceptionInfo;

    // ARG CONSTRUCTOR
    public MyException(ExceptionCode code, String message, LocalDateTime dateTime)
    {

        this.exceptionInfo = new ExceptionInfo(code, message, dateTime);
        //this.exceptionInfo = new ExceptionInfo(code, message, dateTime);
    }

    public MyException(ExceptionCode code, String message)
    {
        this.exceptionInfo = new ExceptionInfo(code, message);
    }

    // GETTER
    public ExceptionInfo getExceptionInfo()
    {
        return exceptionInfo;
    }

}
