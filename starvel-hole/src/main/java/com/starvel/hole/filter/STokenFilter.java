package com.starvel.hole.filter;

import com.alibaba.fastjson.JSON;
import com.starvel.auth.service.AuthService;
import com.starvel.common.result.CommonError;
import com.starvel.common.result.HoleError;
import com.starvel.common.result.Result;
import com.starvel.common.util.BaseUtil;
import com.starvel.hole.service.HoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class STokenFilter implements Filter {

    @Autowired
    private HoleUserService holeUserService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Token, Origin, X-Requested-With, Content-Type, Accept, Set-cookie");
        response.setHeader("Access-Control-Expose-Headers", "Token");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(200);
            return;
        }

        //接口不需要拦截
        if (path.startsWith("/api/hole/stoken")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getHeader("STOKEN");
        //token不为空
        if (BaseUtil.NOTNULL(token)) {
            //校验token
            Result authInfo = holeUserService.validateStoken(token);
            if (authInfo.getCode() == 0) {
                //token校验成功
                String holeUserId = ((Map) (authInfo.getData())).get("holeUserId").toString();

                HashMap<String, Object> param = new HashMap<>();
                param.put("_huid_", holeUserId);
                STokenParameterWrapper requestParameterWrapper = new STokenParameterWrapper(request);
                requestParameterWrapper.addParameters(param);
                filterChain.doFilter(requestParameterWrapper, servletResponse);
                return;
            }
        }
        //token为空或校验失败
        respMsg(response, Result.getResult(HoleError.HOLE_STOKEN_WRONG));
    }

    //向客户端发送消息
    private static void respMsg(HttpServletResponse response, Result result) {
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            String json = JSON.toJSONString(result);

            out.write(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();
        }
    }
}