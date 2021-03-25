package com.boha.receiver.services;

import com.boha.receiver.data.Anchor;
import com.boha.receiver.util.E;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TOMLService {
    public static final Logger LOGGER = LoggerFactory.getLogger(TOMLService.class.getSimpleName());
    public static final String mm = E.HEART_GREEN + E.HEART_GREEN + E.HEART_GREEN + TOMLService.class.getSimpleName() + " : ";
    public static final String DOWNLOAD_PATH = "downloads";
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();


    public TOMLService() {
        LOGGER.info(mm + "constructed, will be working soon");
    }

    private Toml stellarToml;
    @Autowired
    private ResourceLoader resourceLoader;

    public Toml getStellarToml() {
        if (stellarToml != null) {
            return stellarToml;
        }
        LOGGER.info(E.PEPPER + E.PEPPER + E.PEPPER + "TOMLService getting Stellar toml file ..... ");
        try {
            //read from file stellar.toml
            File data = getStellarTomlFile();
            stellarToml = new Toml().read(data);
            LOGGER.info(stellarToml.toString());
        } catch (Exception e) {
            LOGGER.info(E.PEPPER + E.PEPPER + E.PEPPER + "....... Failed to get stellar.toml file from dir ." +
                    " solve this by adding stellar.toml to resources \uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 ");
        }
        return stellarToml;
    }

    public void editCurrency() throws Exception {
        LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + "editCurrency; .......... ");
        Toml toml = getStellarToml();
        Anchor anchor = firebaseService.getDummyAnchor();
        if (anchor == null) {
            LOGGER.info(E.ERROR+E.ERROR+"editCurrency: anchor missing in action");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        List<Object> curr = toml.getList("CURRENCIES");
        if (curr == null) {
            throw new Exception(E.NOT_OK+E.ERROR+"Fucked. No currencies list");
        }
        for (Object o : curr) {
            LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + "currency: " + o);
            HashMap<String, Object> json = (HashMap) o;
            String code = (String) json.get("code");
            if (code != null) {
                String issuer = (String) json.get("issuer");
                LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + "code: "
                        + code + " " + E.RED_APPLE + " issuer: " + issuer);
                json.put("issuer", anchor.getIssuingStellarAccount().getAccountId());
            }


        }
        LOGGER.info(E.FERN + E.FERN + "Toml after change: " + toml.toMap().toString());
        try {

            Path path = Paths.get("stellar.toml");
            new TomlWriter().write(toml.toMap(), path.toFile());
            LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + " updated file: " + path.getFileName().toAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Autowired
    FirebaseService firebaseService;

    public void editAccounts() throws Exception {
        Anchor anchor = firebaseService.getDummyAnchor();
        if (anchor == null) {
            LOGGER.info(E.ERROR+E.ERROR+"editAccounts: anchor missing in action");
            return;
        }
        LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + "editAccounts on Stellar Toml file ... ");
        Toml toml = getStellarToml();
        List<String> mList = new ArrayList<>();
        mList.add(anchor.getBaseStellarAccount().getAccountId());
        mList.add(anchor.getIssuingStellarAccount().getAccountId());
        mList.add(anchor.getDistributionStellarAccount().getAccountId());

        LOGGER.info("Existing Accounts: " + toml.toMap().get("ACCOUNTS").toString());
        LOGGER.info(E.FERN + "New Accounts"+mList.toString());;

        Map<String,Object> map = toml.toMap();
        map.put("ACCOUNTS", mList);
        map.put("SIGNING_KEY", anchor.getDistributionStellarAccount().getAccountId());

        LOGGER.info(E.FERN + E.FERN + "Toml after change: " + toml.toMap().toString());
        try {
            Path path = Paths.get("stellar.toml");
            new TomlWriter().write(map, path.toFile());
            Toml tom = getStellarToml();
            List<String> list = tom.getList("ACCOUNTS");
            LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + " updated file, check accounts: " + list);
            LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + " issuer account: " + E.RED_APPLE + anchor.getIssuingStellarAccount().getAccountId() + " ");
            LOGGER.info(E.COOL_MAN + E.COOL_MAN + E.COOL_MAN + " distribution account: " + E.RED_APPLE + anchor.getDistributionStellarAccount().getAccountId() + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private File getStellarTomlFile() throws IOException {
        LOGGER.info(E.BLUE_THINGY + E.BLUE_THINGY + E.BLUE_THINGY + E.BLUE_THINGY +
                "getStellarTomlFile: ");
        File file = new File("stellar.toml");
        if (file.exists()) {
            LOGGER.info(E.BLUE_THINGY + E.BLUE_THINGY + E.BLUE_THINGY + E.BLUE_THINGY +
                    "existing file from disk: " + file.getAbsolutePath());
            return file;
        } else {
            Resource resource = resourceLoader.getResource("classpath:stellar.toml");
            File resourceFile = resource.getFile();
            LOGGER.info(E.BLUE_THINGY + E.BLUE_THINGY + E.BLUE_THINGY + E.BLUE_THINGY +
                    "resourceFile from resource loader: " + resourceFile.getAbsolutePath());
            return resourceFile;
        }
    }


}
