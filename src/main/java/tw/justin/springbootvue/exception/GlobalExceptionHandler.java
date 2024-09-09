package tw.justin.springbootvue.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.justin.springbootvue.common.Result;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果拋出的是 ServiceException ，則調用該方法
     * @param se 業務異常
     * @return  Result
     */

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }



}
