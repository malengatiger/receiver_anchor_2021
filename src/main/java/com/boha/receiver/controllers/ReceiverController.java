package com.boha.receiver.controllers;

import com.boha.receiver.ReceiverAnchorApplication;
import com.boha.receiver.data.Anchor;
import com.boha.receiver.data.CustomMessage;
import com.boha.receiver.data.ReceivingAnchor;
import com.boha.receiver.services.AccountService;
import com.boha.receiver.services.FirebaseService;
import com.boha.receiver.services.TOMLService;
import com.boha.receiver.services.directpayments.DirectPaymentSenderService;
import com.boha.receiver.services.misc.NetService;
import com.boha.receiver.transfer.sep10.AnchorSep10Challenge;
import com.boha.receiver.transfer.sep10.ChallengeResponse;
import com.boha.receiver.transfer.sep10.JWTToken;
import com.moandjiezana.toml.Toml;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.boha.receiver.util.E;
import org.stellar.sdk.requests.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReceiverController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReceiverController.class.getSimpleName());
    public static final String mm = E.HEART_GREEN+  E.HEART_GREEN +  E.HEART_GREEN + ReceiverAnchorApplication.class.getSimpleName() + " : ";

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AnchorSep10Challenge anchorSep10Challenge;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DirectPaymentSenderService directPaymentSenderService;

    @Autowired
    private TOMLService tomlService;

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object>  ping() {
       String c = mm+"Receiving Anchor getting pinged: " + new DateTime().toDateTimeISO().toString();
       LOGGER.info(c);
       return ResponseEntity.ok(new CustomMessage(200,c));
    }
    @GetMapping(value = "/.well-known/stellar.toml", produces = MediaType.TEXT_PLAIN_VALUE)
    public String  getWellKnown() throws Exception {
        String c = mm+"Dummy Anchor getWellKnown: " + new DateTime().toDateTimeISO().toString();
        LOGGER.info(c);
        Toml toml = tomlService.getStellarToml();
        if (toml != null) {
            return toml.toMap().toString();
        } else {
            throw new Exception("stellar.toml missing");
        }
    }

    @GetMapping(value = "/startAnchorConnection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startAnchorConnection(String assetCode)  {
        LOGGER.info(E.BROCCOLI + E.BROCCOLI + "startAnchorConnection starting ...");

        try {
            String res = directPaymentSenderService.startAnchorConnection(assetCode);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new CustomMessage(400,
                    "startAnchorConnection failed: " + e.getMessage()));
        }

    }


    @GetMapping(value = "/createAnchorAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAnchorAccounts(@RequestParam String fundingSeed) {
        String c = mm+" ........ createAnchorAccounts starting ..."+mm;
        LOGGER.info(c);
        try {
            Anchor anchor = accountService.createAnchorAccounts(fundingSeed);
            return ResponseEntity.ok(anchor);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    new CustomMessage(400, "createAnchorAccounts failed: " + e.getMessage()));
        }
    }

    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> auth(@RequestParam String account) {
        LOGGER.info(mm+"ReceiverController:auth ... authorizing account: ".concat(account));
        try {
            ChallengeResponse response = anchorSep10Challenge.newChallenge(account);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String msg = E.NOT_OK + E.NOT_OK + "Authentication Failed : " + e.getMessage();
            LOGGER.info(msg);
            return ResponseEntity.badRequest()
                    .body(msg);
        }
    }
    @Autowired
    FirebaseService firebaseService;

    private ReceivingAnchor receivingAnchor;
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> token(@RequestBody String transaction) throws Exception {
        LOGGER.info(mm+"token endpoint fired up! will try to return token from transaction .........");
        try {
            if (receivingAnchor == null) {
                LOGGER.info(E.FIRE + "receivingAnchor unavailable");
                receivingAnchor = firebaseService.getReceivingAnchor( "ZIMDOLLAR");
            }
            String token = anchorSep10Challenge.getToken(transaction);
            LOGGER.info(mm + "JWT Token returned: ".concat(token).concat(mm));
            JWTToken finalToken = new JWTToken(token);
            LOGGER.info(mm + "JWT Token; finalToken returned: ".concat(finalToken.getToken()).concat(mm));
            return ResponseEntity.ok(finalToken);
        } catch (Exception e) {
            String msg = E.ERROR + "Token acquisition failed " + E.ERROR + e.getMessage();
            LOGGER.info(msg);
            return ResponseEntity.badRequest()
                    .body(msg);
        }
    }

}
