package tw.justin.springbootvue.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tw.justin.springbootvue.common.Constants;
import tw.justin.springbootvue.config.AuthAccess;
import tw.justin.springbootvue.entity.User;
import tw.justin.springbootvue.exception.ServiceException;
import tw.justin.springbootvue.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通過
        if(!(handler instanceof HandlerMethod)){
            return true;
        }else{
            HandlerMethod h = (HandlerMethod) handler;
            AuthAccess authAccess = h.getMethodAnnotation(AuthAccess.class);
            if (authAccess != null){
                return true;
            }
        }

        // 執行認證
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"無 token 請重新登錄");
        }

        // 獲取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token 驗證失敗，請重新登錄");
        }

        // 根據 token 中的 userid 查詢數據庫
        User user=userService.getById(userId);
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"用戶不存在，請重新登錄");
        }

        // 用戶密碼加簽驗證 token
        JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 驗證 token
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"token 驗證失敗，請重新登錄");
        }

        return true;
    }
}
