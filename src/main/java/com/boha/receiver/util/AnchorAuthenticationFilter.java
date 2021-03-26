package com.boha.receiver.util;

import com.boha.receiver.services.FirebaseService;
import com.boha.receiver.services.JWTokenService;
import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.moandjiezana.toml.Toml;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
@Service
public class AnchorAuthenticationFilter extends OncePerRequestFilter {
    public AnchorAuthenticationFilter() {
        LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 " +
                "BFNAuthenticationFilter which extends OncePerRequestFilter: constructor \uD83D\uDE21");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AnchorAuthenticationFilter.class);

    @Autowired
    private JWTokenService tokenService;
    @Autowired
    private FirebaseService firebaseService;

    private Toml toml;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,
                                    @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURL().toString();
        LOGGER.info(E.BELL + "Authenticating this url: " + E.BELL + " " + url + " - " + httpServletRequest.getRemoteHost());
        //
//        if (url.contains("192.168.86.240")) {
//            LOGGER.info(E.ANGRY + "this request is not subject to authentication because: DEVELOPMENT from localhost: "
//                    + E.HAND2 + url);
//            doFilter(httpServletRequest, httpServletResponse, filterChain);
//            return;
//        }

        //todo - figure out how to secure the upload endpoints - maybe get auth token from firebaseAdmin sdk?
        if (url.contains("auth")
                || url.contains("token")
                || url.contains("info")
                || url.contains(".well-known")
                || url.contains("startAnchorConnection")
                || url.contains("createAnchorAccounts")) {
            LOGGER.info(E.ANGRY + "this request is not subject to authentication: "
                    + E.HAND2 + url);
            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }
        Enumeration<String> mm = httpServletRequest.getHeaderNames();
        LOGGER.info(E.PEAR + E.PEAR + E.PEAR + "Headers from request ");
        while (mm.hasMoreElements()) {
            String name = mm.nextElement();
            String val = httpServletRequest.getHeader(name);
            LOGGER.info(E.PEAR + E.PEAR + "Header: " + name + " - " + val);

        }

        String token = getToken(httpServletRequest);
        LOGGER.info("\uD83D\uDE21 Authentication token retrieved: " + token);
        boolean verifiedToken = verifyToken(httpServletRequest, httpServletResponse, filterChain, url, token);
        LOGGER.info(E.PEAR + E.PEAR + E.PEAR + "Token verified: " + verifiedToken);
        if (verifiedToken) {
            doFilter(httpServletRequest,httpServletResponse,filterChain);
        }

    }
    private String getToken(@NotNull HttpServletRequest httpServletRequest) throws ServletException {
        String m = httpServletRequest.getHeader("Authorization");
        if (m == null) {
            String msg = "\uD83D\uDD06 \uD83D\uDD06 \uD83D\uDD06 " +
                    "Authorization Header is missing. Needs JWT token! \uD83C\uDF4E "
                    + httpServletRequest.getContextPath() + " \uD83C\uDF4E \uD83C\uDF4E";
            LOGGER.info(msg);
            throw new ServletException(msg);
        }
        if (m.length() < 10) {
            String msg = "\uD83D\uDD06 \uD83D\uDD06 \uD83D\uDD06 " +
                    "Invalid Authorization Header detected \uD83C\uDF4E "
                    + httpServletRequest.getContextPath() + " \uD83C\uDF4E \uD83C\uDF4E";
            LOGGER.info(msg);
            throw new ServletException(msg);
        }
        return m.substring(7);
    }

    private boolean verifyToken(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain, String url, String token) throws ServletException {
        try {
            try {
                ApiFuture<FirebaseToken> future = FirebaseAuth.getInstance().verifyIdTokenAsync(token, true);
                FirebaseToken mToken = future.get();
                String uid = mToken.getUid();
                LOGGER.info("\uD83D\uDE21 Authentication for request executed, uid: "
                        + mToken.getUid() + " \uD83D\uDE21 email: " + mToken.getEmail()
                        + "  \uD83C\uDF38 isEmailVerified: " + mToken.isEmailVerified() + "  \uD83C\uDF38" +
                        " - going on to do the filter - \uD83C\uDF4E request has been authenticated OK; \uD83C\uDF4E user uid: " + uid);
                doFilter(httpServletRequest, httpServletResponse, filterChain);
                return true;
            } catch (Exception e) {
                LOGGER.info("\uD83D\uDD25 \uD83D\uDD25 " +
                        "this auth request may not be from our Firebase auth users: " + url);
            }
            LOGGER.info("😡 😡 how do we verify that this is our Platform Stellar based token ..... ?? 😡 😡");

            Claims claims = tokenService.decodeJWT(token);
            LOGGER.info("\n " + E.FLOWER_YELLOW +
                    "Claims: JWT issuer: " + claims.getIssuer() + "\n " +
                    E.RED_APPLE + "subject: " + claims.getSubject() + "\n \uD83C\uDF3C iat: "
                    + claims.getIssuedAt().toString() + "\n " + E.PEAR +
                    "exp: " + claims.getExpiration().toString());

            LOGGER.info("😡 😡 how do we verify that this is our token ..... ?? 😡 😡");
            DateTime claimExpires = new DateTime(claims.getExpiration());
            DateTime now = new DateTime();
            if (claimExpires.isBefore(now)) {
                throw new Exception("Key expired");
            }
            String char1 = claims.getSubject().substring(0,1);
            if (!char1.contains("G")) {
                throw new Exception("Bad Key");
            }
            LOGGER.info("😡 😡 how do we verify that this is our token? WE GOOD! \uD83C\uDF50 \uD83C\uDF50");
            //todo - what other check is made of the claims? we have a Stellar accountId in claims
            return true;
        } catch (Exception e) {
            String msg = "\uD83D\uDD06 \uD83D\uDD06 \uD83D\uDD06 " +
                    "Authorization Exception happened: \uD83C\uDF4E " + e.getMessage();
            e.printStackTrace();
            LOGGER.info(msg);
            throw new ServletException(msg);
        }
    }

    private void doFilter(@NotNull HttpServletRequest httpServletRequest,
                          @NotNull HttpServletResponse httpServletResponse,
                          FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        LOGGER.info("\uD83D\uDD37 \uD83D\uDD37 \uD83D\uDD37 Response Status Code is: "
                + httpServletResponse.getStatus() + "  \uD83D\uDD37  \uD83D\uDD37");
    }

    private void print(@NotNull HttpServletRequest httpServletRequest) {
        System.out.println("\uD83D\uDE21 \uD83D\uDE21 parameters ...");
        Enumeration<String> parms = httpServletRequest.getParameterNames();
        while (parms.hasMoreElements()) {
            String m = parms.nextElement();
            LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 parameterName: " + m);

        }
        System.out.println("\uD83D\uDE21 \uD83D\uDE21 headers ...");
        Enumeration<String> names = httpServletRequest.getHeaderNames();
        while (names.hasMoreElements()) {
            String m = names.nextElement();
            LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 headerName: " + m);
        }
        System.out.println("\uD83D\uDC9A\uD83D\uDC9A\uD83D\uDC9A Header: Authorization: "
                + httpServletRequest.getHeader("Authorization") + " \uD83D\uDC9A");
    }

}
