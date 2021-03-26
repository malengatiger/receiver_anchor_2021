package com.boha.receiver.services.directpayments;

import com.boha.receiver.data.ReceivingAnchor;
import com.boha.receiver.services.FirebaseService;
import com.boha.receiver.services.directpayments.inforesponse.InfoResponse;
import com.boha.receiver.services.directpayments.txresponse.TransactionResponse;
import com.boha.receiver.services.misc.NetService;
import com.boha.receiver.transfer.sep10.AnchorSep10Challenge;
import com.boha.receiver.util.Constants;
import com.boha.receiver.util.E;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stellar.sdk.KeyPair;
/*
    🥨 🥨 🥨 Sender Flow 🥨 🥨 🥨
    The Sending Client (user) initiates a direct payment to the Receiving Client.
    The Sending Anchor identifies the Receiving Anchor it will use for the payment based on the recipient's location and desired currency.
    The Sending Anchor makes a request to the Receiving Anchor's /info endpoint to collect asset information and the transaction.fields describing the pieces of information required by the Receiving Anchor.
    ---- If the Receiving Anchor has a sender_sep12_type and/or a receiver_sep12_type attribute in the /info endpoint response,
    ---- The Sending Anchor must collect the Receiving Anchor's KYC_SERVER URI from the Receiving Anchor's SEP-1 stellar.toml file.
    ---- The Sending Anchor must make a GET KYC_SERVER/customer request for each _sep12_type value listed in the /info response. Each /customer response contains SEP-9 fields required by the Receiving Anchor for that user.
    ---- The Sending Anchor collects all required information from the Sending Client. This includes the custom fields listed in the Receiving Anchor's /info response as well as the KYC fields described in the /customer responses for both the Sending and Recieving Clients. How the Sending Anchor collects this information from the Sending Client is out of the scope of this document, but the Receving Client should not be required to take any action in order for the Sending Client to make the payment to the Receiving Anchor.
    ---- The Sending Anchor makes PUT KYC_SERVER/customer requests containing the SEP-9 values listed in the GET KYC_SERVER/customer responses to register the Clients with the Receiving Anchor.
    ---- On successful registration (202 HTTP status), the Sending Anchor makes a POST DIRECT_PAYMENT_SERVER/transactions request to create a pending_sender transaction with the Receiving Anchor.
    Note that this request contains the ids returned by the PUT /customer requests, the transaction.fields values collected from the Sending Client, as well as the transaction amount and asset information.
    Once the transaction is included in the GET DIRECT_PAYMENT_SERVER/transactions response and the transaction's status is pending_sender,
        the Sending Anchor submits the path payment transaction to Stellar.
    🌼🌼 The destination account of the transaction must be the stellar_account_id returned in the POST /transactions response.
    🌼🌼 The memo of the transaction must match the stellar_memo and stellar_memo_type returned in the POST /transactions response.
    🌼🌼 The Sending Anchor polls the Receiving Anchor's GET DIRECT_PAYMENT_SERVER/transactions endpoint until the transaction's status changes to completed, error, pending_customer_info_update, or pending_transaction_info_update.
    🌼🌼 If completed, the job is done and the Sending Anchor should notify the Sending Client.
    🍎  If error, the Receiving Anchor should be contacted to resolve the situation.
    🍎 If pending_transaction_info_update, the transaction.fields values collected from the Sending Client were invalid and must be corrected by the Sending Client
    This requires the Sending Anchor to detect which fields were invalid from the required_info_updates object on the /transactions record.
    Then the Sending Anchor must reach out the Sending Client again, collect valid values, and make a PATCH DIRECT_PAYMENT_SERVER/transactions request to the Receiving Anchor
    If pending_customer_info_update, the SEP-9 KYC values collected were invalid and must be corrected by the Sending Client
    This requires the Sending Anchor to make a GET KYC_SERVER/customer request for each customer associated with the transaction to determine which fields need to be updated
    The Sending Anchor then reaches out to the Sending Client again, collects valid values for the invalid fields, and makes a PUT KYC_SERVER/customer request to the Receiving Anchor
    After providing the Receiving Anchor with updated values, the status should ultimately change to completed
 */

@Service
public class DirectPaymentSenderService {
    public static final Logger LOGGER = LoggerFactory.getLogger(DirectPaymentSenderService.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private static final String mm = "\uD83C\uDF50 \uD83C\uDF50 \uD83C\uDF50 DirectPaymentSenderService: ";

    public DirectPaymentSenderService() {
        LOGGER.info(mm +
                "constructed and ready to talk to other anchors!");
    }
    @Autowired
    FirebaseService firebaseService;

    @Autowired
    AnchorSep10Challenge anchorSep10Challenge;

    @Autowired
    NetService netService;
    public static final String bb = "\uD83C\uDF50 \uD83C\uDF50 \uD83C\uDF50 \uD83C\uDF50";

    public static final String TEST_URL = "http://192.168.86.240:8092/anchor/api/v1/";
    public String startAnchorConnection(String assetCode) throws Exception {
        com.boha.receiver.data.ReceivingAnchor receivingAnchor =
                firebaseService.getReceivingAnchor(assetCode);
        if (receivingAnchor != null) {
            LOGGER.info(bb+G.toJson(receivingAnchor));
            String seed = receivingAnchor.getTestSecret();
            String url = TEST_URL +  "auth?account=" + receivingAnchor.getTestAccount();
            LOGGER.info(bb+"url: " + url);
            String trans = netService.get(url, null);
            AuthResponse authResponse = G.fromJson(trans,AuthResponse.class);
            LOGGER.info(bb+"RECEIVING anchor "+ receivingAnchor.getPhone()+" responded with Transaction: " +
                    bb);

            AnchorSep10Challenge.ChallengeTransaction transaction = anchorSep10Challenge.readChallengeTransaction(authResponse.transaction);
            LOGGER.info(bb+"RECEIVING anchor ChallengeTransaction acquired, ClientAccountId: " + transaction.getClientAccountId());
            LOGGER.info(bb+"RECEIVING anchor ChallengeTransaction acquired, SourceAccount: " + transaction.getTransaction().getSourceAccount());
            KeyPair keyPair = KeyPair.fromSecretSeed(seed);

            LOGGER.info(bb+"... will be Signing ChallengeTransaction from other Anchor .... keyPair.getAccountId: " + keyPair.getAccountId());
            transaction.getTransaction().sign(keyPair);
            LOGGER.info(bb+
                    "AnchorSep10Challenge.ChallengeTransaction has been signed with distribution keyPair! \uD83C\uDF4E YES!! " );


            //todo - The Client submits the signed challenge back to the Server using token endpoint

            String tokenJson = netService.post(TEST_URL + "token",
                    transaction.getTransaction().toEnvelopeXdrBase64());
            LOGGER.info(" \uD83E\uDD4F\uD83E\uDD4F\uD83E\uDD4F  JWT Token json returned: "
                    .concat(tokenJson).concat("  \uD83D\uDD11 \uD83D\uDD11 \uD83D\uDD11"));
            LOGGER.info(" \uD83E\uDD4F\uD83E\uDD4F\uD83E\uDD4F  Testing ping with new token ....");
            JSONObject object = new JSONObject(tokenJson);
            String token = object.getString("token");
            LOGGER.info(" \uD83E\uDD4F\uD83E\uDD4F\uD83E\uDD4F  JWT Token: "
                    .concat(token).concat("  \uD83D\uDD11 \uD83D\uDD11 \uD83D\uDD11"));
            testPingWithAuthentication(token);
            return tokenJson;
        } else {
            throw new Exception("\uD83D\uDD25 \uD83D\uDD25 No Receiving Anchor found");
        }
    }

    private void testPingWithAuthentication(String token) {
        LOGGER.info(" \uD83E\uDD4F\uD83E\uDD4F\uD83E\uDD4F ............. testPingWithAuthentication starting ........... : ");
        try {
            String pinged = netService.get("http://localhost:8092/anchor/api/v1/ping", token);
            LOGGER.info(E.FERN+E.FERN+pinged);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public InfoResponse getReceivingAnchorInfo(ReceivingAnchor receivingAnchor) {
        //👽👽 GET DIRECT_PAYMENT_SERVER/info
        //todo -     The Sending Anchor makes a request to the Receiving Anchor's /info endpoint to collect asset information and
        // the transaction.fields describing the pieces of information required by the Receiving Anchor.

        //todo  - get stellar.toml of receiving anchor and then get DIRECT_PAYMENT_SERVER url
        // use url to call /info endpoint

        String url = receivingAnchor.getDirectPaymentsURL() + "/info";
        LOGGER.info("info url: " + url);

        return null;
    }
    public InitiatePaymentResponse startPayment(InitiatePayment initiatePayment, String url) {
        //POST DIRECT_PAYMENT_SERVER/transactions
        //Content-Type: application/json
        LOGGER.info(mm+"Initiate talk with other anchor ...." + G.toJson(initiatePayment) );
        //todo -  The Sending Anchor identifies the Receiving Anchor it will use for the payment based on the recipient's location and desired currency. ????
        //todo -  May need to create model PartnerAnchor to hold location and currency

        //post initiatePayment to receiving anchor url/transactions with body ....
        //todo - return SUCCESS 201

        return null;
    }
    /*
           🥏🥏🥏 status should be one of:

               pending_sender -- awaiting payment to be initiated by sending anchor.
               pending_stellar -- transaction has been submitted to Stellar network, but is not yet confirmed.
               pending_customer_info_update -- certain pieces of information need to be updated by the sending anchor. See pending customer info update section
               pending_transaction_info_update -- certain pieces of information need to be updated by the sending anchor. See pending transaction info update section
               pending_receiver -- payment is being processed by the receiving anchor
               pending_external -- payment has been submitted to external network, but is not yet confirmed.
               completed -- deposit/withdrawal fully completed.
               error -- catch-all for any error not enumerated above.
            */
    public TransactionResponse queryTransaction(String transactionId) {
        //The transaction endpoint enables senders to query/validate a specific transaction at a receiving anchor.
        //
        //GET DIRECT_PAYMENT_SERVER/transactions/:id

        //todo - If the transaction cannot be found, the endpoint should return a 404 NOT FOUND result.

        TransactionResponse response = new TransactionResponse();

        //deal with the status as above ....
        String status = response.getTransaction().getStatus();
        LOGGER.info(mm+"Transaction().getStatus: " +  status);
        switch (status) {
            case Constants.STATUS_COMPLETED:
                LOGGER.info(mm+"STATUS_COMPLETED  - ");
                break;
            case Constants.STATUS_ERROR:
                LOGGER.info(mm+"STATUS_ERROR  - ");
                break;
            case Constants.STATUS_PENDING_SENDER:
                LOGGER.info(mm+"STATUS_PENDING_SENDER  - ");
                break;
            case Constants.STATUS_PENDING_RECEIVER:
                LOGGER.info(mm+"STATUS_PENDING_RECEIVER  - ");
                break;
            case Constants.STATUS_PENDING_CUSTOMER_INFO_UPDATE:
                LOGGER.info(mm+"STATUS_PENDING_CUSTOMER_INFO_UPDATE  - ");
                break;
            case Constants.STATUS_PENDING_EXTERNAL:
                LOGGER.info(mm+"STATUS_PENDING_EXTERNAL  - ");
                break;
            case Constants.STATUS_PENDING_STELLAR:
                LOGGER.info(mm+"STATUS_PENDING_STELLAR  - ");
                break;
            case Constants.STATUS_PENDING_TRANSACTION_INFO_UPDATE:
                LOGGER.info(mm+"STATUS_PENDING_TRANSACTION_INFO_UPDATE  - ");
                break;
        }
        return null;
    }


}
