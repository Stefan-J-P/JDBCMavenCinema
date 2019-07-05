package maven.jdbc.cinema.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionInfo
{
    private ExceptionCode code;
    private String message;
    private LocalDateTime dateTime;

    public ExceptionInfo(ExceptionCode code, String message, LocalDateTime dateTime)
    {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public ExceptionInfo(ExceptionCode code, String message)
    {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
