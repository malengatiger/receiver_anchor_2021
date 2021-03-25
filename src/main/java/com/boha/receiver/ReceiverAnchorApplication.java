package com.boha.receiver;

import com.boha.receiver.data.Anchor;
import com.boha.receiver.services.TOMLService;
import com.boha.receiver.util.E;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import com.boha.receiver.services.FirebaseService;

import java.io.PrintStream;
import java.util.Date;

/**
 * ReceiverAnchorApplication takes the role of the other Anchor
 * During test this anchor can send and receive DUMMY information
 */
@SpringBootApplication
public class ReceiverAnchorApplication implements ApplicationListener<ApplicationReadyEvent>	{
	public static final Logger LOGGER = LoggerFactory.getLogger(ReceiverAnchorApplication.class.getSimpleName());
	@Autowired
	private Environment environment;

	@Autowired
	private FirebaseService firebaseService;

	@Autowired
	private TOMLService tomlService;


	@Value("${status}")
	private String status;

	@Value("${spring.profiles.active}")
	private String activeProfile;
		
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ReceiverAnchorApplication.class);
		app.setLogStartupInfo(true);
		app.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment,
									Class<?> sourceClass,
									PrintStream out) {
				out.println(getBanner());
			}
		});
		app.run(args);
		LOGGER.info(E.LEAF + E.LEAF + E.LEAF + "ReceiverAnchorApplication started; ready to move money around .........."
				+ new Date().toString() + " " + E.LEAF + E.LEAF);

	}
	private static String getBanner() {
		StringBuilder sb = new StringBuilder();
		sb.append("########################################################################### ####\n");
		sb.append("#### " + E.HEART_ORANGE + "MONEY PLATFORM SERVICES TEST RECEIVER ANCHOR " + E.HEART_ORANGE + "                     ####\n");
		sb.append("#### " + E.HEART_ORANGE + "A Digital Platform to move money " + E.HEART_ORANGE + "                                 ####\n");
		sb.append("#### " + E.HEART_ORANGE + "I am a Dummy! DUMMY! know-nothing a#&hole " + E.BLUE_THINGY+E.BROCCOLI + "                     ####\n");
		sb.append("#### ".concat(E.FLOWER_RED).concat(new Date().toString().concat("                                        ####\n")));
		sb.append("########################################################################### ####\n");
		return sb.toString();
	}

	@Override
	public void onApplicationEvent(@NonNull  ApplicationReadyEvent applicationReadyEvent) {
		LOGGER.info(mm+"STELLAR :: \uD83C\uDF51 ReceiverAnchorApplication: onApplicationEvent: " +
				"ApplicationReadyEvent fired: \uD83C\uDF3C \uD83C\uDF3C app is ready to initialize Firebase .... ");
		LOGGER.info(mm+"onApplicationEvent: DEVELOPMENT STATUS: " +
				"\uD83C\uDF51 " + status + " \uD83C\uDF51 ");
		LOGGER.info(mm+"onApplicationEvent: ACTIVE PROFILE : " +
				"\uD83C\uDF51 " + activeProfile + " \uD83C\uDF51 ");

		for (String profile : environment.getActiveProfiles()) {
			LOGGER.info(E.PEAR+E.PEAR+"Active Profile from environment: " + profile);
		}
		try {
			LOGGER.info(E.FERN+E.FERN+"STELLAR TOML" + tomlService.getStellarToml().toMap());

			firebaseService.initializeFirebase();
			tomlService.editCurrency();
			tomlService.editAccounts();
		} catch (Exception e) {
			LOGGER.info(E.ERROR+E.ERROR+E.ERROR+e.getMessage());
			e.printStackTrace();
		}

	}
	public static final String mm = E.HEART_ORANGE +  E.HEART_ORANGE +  E.HEART_ORANGE + ReceiverAnchorApplication.class.getSimpleName() + " : ";

}
