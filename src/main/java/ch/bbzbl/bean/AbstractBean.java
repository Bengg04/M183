package ch.bbzbl.bean;
import org.primefaces.PrimeFaces;
import ch.bbzbl.util.JSFMessageUtil;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

//das ist ein testkommentar für den Pull
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
			LOGGER.info("Logger (AbstractBean) erfolgreich initialisiert!");

		} catch (IOException e) {
			e.printStackTrace(); // Zeigt detaillierte Informationen über den Fehler
			LOGGER.warning("Fehler beim Initialisieren des FileHandlers für den Logger (AbstractBean): " + e.getMessage());
		}
		System.out.println("testMessage");

	}

	protected void displayErrorMessageToUser(String message) {
		try{
			JSFMessageUtil messageUtil = new JSFMessageUtil();
			messageUtil.sendErrorMessageToUser(message);
			LOGGER.info("Fehlermeldung an Benutzer gesendet: " + message);
		}catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Fehler beim Senden einer Fehlermeldung an den Benutzer", e);
		}
	}
	
	protected void displayInfoMessageToUser(String message) {
		try{
			JSFMessageUtil messageUtil = new JSFMessageUtil();
			messageUtil.sendInfoMessageToUser(message);
			LOGGER.info("Informationsmeldung an Benutzer gesendet: " + message);
		}catch (Exception e){
			LOGGER.log(Level.SEVERE, "Fehler beim Senden einer Informationsmeldung an den Benutzer", e);
		}
	}
	
	protected void closeDialog(){
		try{
			PrimeFaces.current().ajax().addCallbackParam(KEEP_DIALOG_OPENED, false);
			LOGGER.info("Dialog wurde geschlossen.");
		}catch (Exception e){
			LOGGER.log(Level.SEVERE, "Fehler beim Schließen des Dialogs", e);
		}
	}
	
	protected void keepDialogOpen(){
		try{
			PrimeFaces.current().ajax().addCallbackParam(KEEP_DIALOG_OPENED, true);
			LOGGER.info("Dialog bleibt offen.");
		} catch (Exception e){
			LOGGER.log(Level.SEVERE, "Fehler beim Offenhalten des Dialogs", e);
		}
	}
	
}
