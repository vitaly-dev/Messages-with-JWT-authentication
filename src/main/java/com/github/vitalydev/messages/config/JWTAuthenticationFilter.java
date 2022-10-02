package com.github.vitalydev.messages.config;

// We can use this Filter instead of AuthenticationController /login Request method
public class JWTAuthenticationFilter { //extends UsernamePasswordAuthenticationFilter {

   /* private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/authentication/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            AuthenticationRequest creds = JsonUtil.getMapper().readValue(req.getInputStream(), AuthenticationRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getName(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException {
     *//*   String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));*//*
        String token = TokenUtil.generateToken((User) auth.getPrincipal());
        // write body Response to client:
        //  {
        //    token: "generated token"
        //  }
       // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJjcmVhdGVkIjoxNjY0NDg2NzQxOTAzLCJuYW1lIjoiQWRtaW4iLCJhZG1pbiI6ZmFsc2UsImV4cCI6MjU2NDQ4Njc0MX0.sbnmgu88ZOJGyNibQcjCz_fTquo1Xh4u347duF2AYWxThAlxIOhrKPbD1SY_CKjp6dkOG2v1jczA7lYJjoOITA"
        String body = JsonUtil.writeValue(new AuthenticationResponse(token));
        res.getWriter().write(body);
        res.getWriter().flush();
    }*/
}