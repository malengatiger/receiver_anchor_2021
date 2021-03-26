package com.boha.receiver.services;

import com.boha.receiver.util.E;
import com.boha.receiver.ReceiverAnchorApplication;
import com.moandjiezana.toml.Toml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStellarTOML {
    public static final Logger LOGGER = LoggerFactory.getLogger(TestStellarTOML.class.getSimpleName());
    public static final String mm = E.HEART_GREEN+  E.HEART_GREEN +  E.HEART_GREEN + ReceiverAnchorApplication.class.getSimpleName() + " : ";

    public static void main(String[] args) throws Exception {

        TOMLService tomlService = new TOMLService();
        String toml = tomlService.getStellarTOMLString();

        LOGGER.info(E.CHIPS+E.CHIPS+E.CHIPS+ toml);

    }
}
