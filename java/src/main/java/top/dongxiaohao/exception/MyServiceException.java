package top.dongxiaohao.exception;

public class MyServiceException extends RuntimeException{
    public MyServiceException() {
        super();
    }

    public MyServiceException(String message) {
        super(message);
    }

    public MyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyServiceException(Throwable cause) {
        super(cause);
    }
}
