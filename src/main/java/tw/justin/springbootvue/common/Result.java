package tw.justin.springbootvue.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 接口統一返回包裝類
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    public static Result error( ){
        return new Result(Constants.CODE_500,"系統錯誤",null);
    }

}
