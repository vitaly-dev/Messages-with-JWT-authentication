package com.github.vitalydev.messages.config;

import com.github.vitalydev.messages.repository.UserRepository;
import com.github.vitalydev.messages.util.TokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.github.vitalydev.messages.config.WebSecurity.HEADER_STRING;
import static com.github.vitalydev.messages.config.WebSecurity.TOKEN_PREFIX;

//@Component
//public class JWTAuthorizationFilter extends OncePerRequestFilter {
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
//public class JWTAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private UserDetailsService userDetailsServiceBean;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsServiceBean) {
        super(authManager);
        this.userDetailsServiceBean = userDetailsServiceBean;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        String token = TokenUtil.getToken(header);
        String username = TokenUtil.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsServiceBean.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = TokenUtil.getUsernameFromToken(TokenUtil.getToken(token));
      /*      String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();*/
            if (user != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
