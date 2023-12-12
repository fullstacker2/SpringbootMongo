package com.example.springbootmongo.Configuration;

import com.example.springbootmongo.Service.TokenService;
import org.bson.types.ObjectId;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//  JWT filter to handle authentication by validating JWT tokens.
public class JwtFilter extends GenericFilterBean {

    private TokenService tokenService;
    // constructor for JwtFilter
    public JwtFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    /* filterChain: for handling multiple requests, ServletException:If a servlet exception occurs.*/
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

        //for extracting token from the Authorization header
        String token = httpServletRequest.getHeader("Authorization");



        // approves request types(get,post,etc) mentioned in the controller
        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK,"success");
            return ;
        }


        // if allowed without needing a token
        if(allowRequestWithoutToken(httpServletRequest)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK, "success");
            filterChain.doFilter(req,res);
            // basically recursion of requests / multiple requests (post,get,etc)
        }
        else {
            ObjectId userId = new ObjectId(tokenService.getUserIdToken(token));
            httpServletRequest.setAttribute("userId", userId);
            filterChain.doFilter(req,res);
        }
    }

    // check if the request can be allowed  without token
    public boolean allowRequestWithoutToken(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getRequestURI());
        if(httpServletRequest.getRequestURI().contains("/admin"))
            return true; //true if request is allowed without a token

        return false; //false if request is not allowed without a token
    }
}
