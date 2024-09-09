package tw.justin.springbootvue.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tw.justin.springbootvue.entity.User;
import tw.justin.springbootvue.service.IUserService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService=userService;
    }



    // 生成 token
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId) // 將 user id 保存到 token 裡面，作為載荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))  // 2 小時後 token 過期
                .sign(Algorithm.HMAC256(sign));     // 以 password 作為 token 的密鑰

    }

    /**
     *  獲取當前登錄的用戶信息
     */
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
            return null;
    }

}
