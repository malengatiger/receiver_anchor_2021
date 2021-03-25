package com.boha.receiver.services;

import com.boha.receiver.data.ReceivingAnchor;
import com.boha.receiver.util.Constants;
import com.boha.receiver.util.E;
import com.boha.receiver.ReceiverAnchorApplication;
import com.boha.receiver.data.Anchor;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FirebaseService {
    public FirebaseService() {
        LOGGER.info(mm + "constructed, ready to dance");
    }

    private boolean isInitialized = false;
    public static final Logger LOGGER = LoggerFactory.getLogger(FirebaseService.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();

    public static final String mm = E.HEART_GREEN+  E.HEART_GREEN +  E.HEART_GREEN + ReceiverAnchorApplication.class.getSimpleName() + " : ";


    public void initializeFirebase() throws Exception {
        LOGGER.info(mm+"initializeFirebase: ... \uD83C\uDF4F" +
                ".... \uD83D\uDC99 \uD83D\uDC99 isInitialized: " + isInitialized
                + " " + E.HEART_BLUE + E.HEART_BLUE);

        try {
            if (!isInitialized) {
                FirebaseOptions prodOptions = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();

                FirebaseApp app = FirebaseApp.initializeApp(prodOptions);
                isInitialized = true;
                LOGGER.info(E.HEART_BLUE + E.HEART_BLUE + "Firebase has been set up and initialized. " +
                        "\uD83D\uDC99 URL: " + app.getOptions().getDatabaseUrl() + E.HAPPY);
                LOGGER.info(E.HEART_BLUE + E.HEART_BLUE + "Firebase has been set up and initialized. " +
                        "\uD83E\uDD66 Name: " + app.getName() + " " + E.HEART_ORANGE + E.HEART_GREEN);
                Firestore fs = FirestoreClient.getFirestore();
                int cnt = 0;
                for (CollectionReference listCollection : fs.listCollections()) {
                    cnt++;
                    LOGGER.info(E.RAIN_DROPS + E.RAIN_DROPS + "Collection: #" + cnt + " \uD83D\uDC99 collection: " + listCollection.getId());
                }
                List<Anchor> list = getDummyAnchors();
                LOGGER.info(E.HEART_BLUE + E.HEART_BLUE +
                        "Firebase Initialization complete; ... " +
                        "\uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E dummy anchors found: " + list.size());
            }
        } catch (Exception e) {
            String msg = "Unable to initialize Firebase";
            LOGGER.info(msg);
            throw new Exception(msg, e);
        }


    }

    public void addDummyAnchor(Anchor anchor) {
        Firestore fs = FirestoreClient.getFirestore();
        fs.collection(Constants.DUMMY_ANCHORS).add(anchor);
        LOGGER.info(mm+"Dummy Anchor added to Firestore");
    }
    public Anchor getDummyAnchor() throws Exception {
        Firestore fs = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = fs.collection(Constants.DUMMY_ANCHORS).get();
        int cnt = 0;
        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            Map<String, Object> map = document.getData();
            String object = G.toJson(map);
            Anchor anchor = G.fromJson(object, Anchor.class);
            cnt++;
            LOGGER.info("\uD83C\uDF51 \uD83C\uDF51 DUMMY ANCHOR: #" + cnt +
                    " \uD83D\uDC99 " + anchor.getName() + "  \uD83E\uDD66 anchorId: "
                    + anchor.getAnchorId());
            return anchor;
        }
        return null;
    }
    public Anchor getAnchor() throws Exception {
        Firestore fs = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = fs.collection(Constants.ANCHORS)
                .limit(1)
                .get();
        int cnt = 0;
        Anchor anchor;
        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            Map<String, Object> map = document.getData();
            String object = G.toJson(map);
            anchor = G.fromJson(object, Anchor.class);
            cnt++;
            LOGGER.info("\uD83C\uDF51 \uD83C\uDF51 REAL ANCHOR: #" + cnt +
                    " \uD83D\uDC99 " + anchor.getName() + "  \uD83E\uDD66 anchorId: "
                    + anchor.getAnchorId());
            return anchor;
        }
        return null;
    }
    public ReceivingAnchor getReceivingAnchor(String assetCode) throws Exception {
        Firestore fs = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = fs.collection(Constants.RECEIVING_ANCHORS)
                .whereEqualTo("assetCode", assetCode)
                .limit(1)
                .get();
        int cnt = 0;
        ReceivingAnchor anchor = null;
        List<QueryDocumentSnapshot> list = future.get().getDocuments();
        for (QueryDocumentSnapshot document : list) {
            Map<String, Object> map = document.getData();
            String object = G.toJson(map);
            anchor = G.fromJson(object, ReceivingAnchor.class);
            cnt++;
            LOGGER.info("\uD83C\uDF51 \uD83C\uDF51 RECEIVING ANCHOR: #" + cnt +
                    " \uD83D\uDC99 " + anchor.getName() + "  \uD83E\uDD66 anchorId: "
                    + anchor.getAnchorId());
        }
        return anchor;
    }
    public List<Anchor> getDummyAnchors() throws Exception {
        Firestore fs = FirestoreClient.getFirestore();
        List<Anchor> mList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = fs.collection(Constants.DUMMY_ANCHORS).get();
        int cnt = 0;
        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            Map<String, Object> map = document.getData();
            String object = G.toJson(map);
            Anchor anchor = G.fromJson(object, Anchor.class);
            cnt++;
            LOGGER.info("\uD83C\uDF51 \uD83C\uDF51 ANCHOR: #" + cnt +
                    " \uD83D\uDC99 " + anchor.getName() + "  \uD83E\uDD66 anchorId: "
                    + anchor.getAnchorId());
            mList.add(anchor);
        }
        return mList;
    }
}
