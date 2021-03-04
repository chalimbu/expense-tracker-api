package com.pairlearning.expensetrackerapi.filters;

import com.pairlearning.expensetrackerapi.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest= (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        final Optional<String> optAuthHeader = Optional.ofNullable(httpRequest.getHeader("Authorization"));
        var optToken=extractToke(optAuthHeader);
        try{
        var requestUpdate=optToken.map(token -> {
            final Claims claims=Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
            httpRequest.setAttribute("userId",Integer.parseInt(claims.get("userId").toString()));
            return token;
        });
        if(requestUpdate.isPresent()){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"invalid/expired token");
            return;
        }
        }catch (Exception e){
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"invalid/expired token");
            return;
        }
    }

    private Optional<String> extractToke(Optional<String> optAuthHeader){
        return optAuthHeader
                .map(x-> {return x.split("Bearer");})
                .filter(arrayHeader->{return arrayHeader.length>1;})
                .flatMap(arrayHeader->{ return Optional.ofNullable(arrayHeader[1]);});
    }
}
