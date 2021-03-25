package com.boha.receiver.directpayments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.boha.receiver.util.E;
/*
         🍀  🍀  🍀 Receiver Flow
            --- The Sending Anchor makes a request to the Receiving Anchor's DIRECT_PAYMENT_SERVER/info endpoint.
            --- The Sending Anchor makes a GET KYC_SERVER/customer request for each _sep12_type attribute included in the response.
            --- The Sending Anchor makes a PUT KYC_SERVER/customer request for each _sep12_type attribute.
            --- The Receiving Anchor must validate the KYC data provided and reject the request with useful error messages if invalid. This is critical for limiting the number of times a transaction ends up in a pending_customer_info_update status.
            --- The Sending Anchor makes a POST DIRECT_PAYMENT_SERVER/transactions request.
            🍐🍐 The Receiving Anchor must validate the asset, amount, transaction fields, and customers.
            🍐🍐 The Receiving Anchor must create a transaction record in their database and expose it via /transactions.
            🍐🍐 Transactions should initially be pending_sender. If any preprocessing is required before receiving a payment, mark the transaction as pending_receiving until ready.
            🍐🍐 The Receiving Anchor then waits to receive the payment identified by the stellar_memo returned in the POST /transactions response.
            🍐🍐 Once the Stellar payment has been received and matched with the internal transaction record, the Receiving Anchor must attempt to transfer an equivalent amount of the asset (minus fees) off-chain to the Receiving Client using the KYC and rails data collected by the Sending Anchor
            🍎 If the off-chain payment succeeds, the transaction's status should be updated to completed
            🍎 If the off-chain payment cannot be received by the Receiving Client almost immediately, the transaction's status should be updated to pending_external until received. Then, completed.
            🍎 If the off-chain payment fails, the Receiving Anchor must determine why, which is outside the scope of this document. Once determined, the Receiving Anchor must either correct it themselves (internal error) or receive updated values from the Sending Anchor for the fields that were discovered to be invalid.
            🍎 If the invalid values were described in /info's transaction.fields object, the transaction's status should be updated to pending_transaction_info_update and required_info_updates should contain an object describing the errors.
            --- If the invalid values were described in GET /customer responses, the transaction's status should be updated to pending_customer_info_update and the invalid field names should be returned in the next GET /customer?id= request for each Client.
            🍐🍐 The Sending Client will detect transaction's status and invalid fields, collect the info from the Sending Client, and make requests to the Receiving Anchor containing the updated information.
            🍐🍐 Once the passed information is validated, the Receiving Anchor should update the transaction's status to pending_receiver and retry the off-chain transfer. This loop of attempting the transfer and waiting for updated information should continue until the transfer is successful.

 */

@Service
public class DirectPaymentReceiverService {
    public static final Logger LOGGER = LoggerFactory.getLogger(DirectPaymentReceiverService.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private static final String mm = E.BLUE_DOT+E.BLUE_DOT+"DirectPaymentReceiverService: ";
    public DirectPaymentReceiverService() {
        LOGGER.info(mm +
                "constructed and ready to talk to other anchors!");
    }

}
