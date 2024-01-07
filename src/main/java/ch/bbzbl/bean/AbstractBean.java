package ch.bbzbl.bean;

import org.primefaces.PrimeFaces;

import ch.bbzbl.util.JSFMessageUtil;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class AbstractBean {
	private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";

	//Logger
	private static final Logger LOGGER = Logger.getLogger(AbstractBean.class.getName());

	public AbstractBean() {
		super();
		try {
			FileHandler fileHandler = new FileHandler("src/main/java/ch/bbzbl/bean/logger", true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);

		} catch (IOException e) {
			e.printStackTrace(); // Zeigt detaillierte Informationen über den Fehler
		}
		System.out.println("testMessage");
		LOGGER.info("Konstuktor AbstractBean wurde aufgerufen!");
	}

	protected void displayErrorMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendErrorMessageToUser(message);
	}
	
	protected void displayInfoMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendInfoMessageToUser(message);
	}
	
	protected void closeDialog(){
		PrimeFaces.current().ajax().addCallbackParam(KEEP_DIALOG_OPENED, false);
	}
	
	protected void keepDialogOpen(){
		PrimeFaces.current().ajax().addCallbackParam(KEEP_DIALOG_OPENED, true);
	}
	
}