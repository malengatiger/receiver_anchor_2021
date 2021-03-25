package com.boha.receiver.services.directpayments.inforesponse;
/*
{
  "receive":{
    "USD":{
      "enabled":true,
      "fee_fixed":5,
      "fee_percent":1,
      "min_amount":0.1,
      "max_amount":1000,
      "sep12": {
        "sender": {
          "types": {
            "sep31-sender": {
              "description": "U.S. citizens limited to sending payments of less than $10,000 in value"
            },
            "sep31-large-sender": {
              "description": "U.S. citizens that do not have sending limits"
            },
            "sep31-foreign-sender": {
              "description": "non-U.S. citizens sending payments of less than $10,000 in value"
            }
          }
        },
        "receiver": {
          "types": {
            "sep31-receiver": {
              "description": "U.S. citizens receiving USD"
            },
            "sep31-foreign-receiver": {
              "description": "non-U.S. citizens receiving USD"
            }
          }
        }
      },
      "fields":{
        "transaction":{
          "receiver_routing_number":{
            "description": "routing number of the destination bank account"
          },
          "receiver_account_number":{
            "description": "bank account number of the destination"
          },
          "type":{
            "description": "type of deposit to make",
            "choices":[
              "SEPA",
              "SWIFT"
            ]
          }
        }
      }
    }
  }
}
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class InfoResponse implements Serializable
{
    public InfoResponse(Receive receive) {
        this.receive = receive;
    }

    @SerializedName("receive")
    @Expose
    private Receive receive;
    private final static long serialVersionUID = 461840104717751146L;

    public Receive getReceive() {
        return receive;
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }

}













