package ch.bbzbl.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ch.bbzbl.entity.GeneralEntity;
import ch.bbzbl.facade.EntityFacade;

@ViewScoped
@ManagedBean
public class EntityBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String SELECTED_ENTITY = "selectedEntity";

	private static final Logger LOGGER = Logger.getLogger(EntityBean.class.getName());
	public EntityBean(){
		try {
			FileHandler fileHandler = new FileHandler("src/main/java/ch/bbzbl/bean/logger", true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
			LOGGER.info("Logger (EntityBean) erfolgreich initialisiert!");
		} catch (IOException e) {
			e.printStackTrace(); // Zeigt detaillierte Informationen über den Fehler
			LOGGER.warning("Fehler beim Initialisieren des FileHandlers für den Logger (EntityBean): " + e.getMessage());
		}

	}

	private GeneralEntity generalEntity;
	private GeneralEntity generalEntityForDetail;

	private List<GeneralEntity> generalEntities;
	private EntityFacade entityFacade;

	@ManagedProperty(value = UserBean.DI_NAME)
	private UserBean userBean;

	public void createEntity() {
		if (this.userBean.isCurrentUserUserOrHigher()) {
			try {
				getEntityFacade().createEntity(generalEntity);
				closeDialog();
				displayInfoMessageToUser("Created with success");
				loadEntities();
				resetEntity();
				LOGGER.info("Entity erfolgreich erstellt.");
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Fehler beim Erstellen der Entity", e);
				keepDialogOpen();
				displayErrorMessageToUser("A problem occurred while saving. Try again later");
				e.printStackTrace();
			}
		} else {
			LOGGER.warning("Benutzer ist nicht berechtigt, um eine Entity zu erstellen.");
			displayErrorMessageToUser("You are not allowed to create an entity.");
		}
	}

	public void updateEntity() {
		if (this.userBean.isCurrentUserAdmin()) {
			try {
				getEntityFacade().updateEntity(generalEntity);
				closeDialog();
				displayInfoMessageToUser("Updated with success");
				loadEntities();
				resetEntity();
				LOGGER.info("Entity erfolgreich aktualisiert.");
			} catch (Exception e) {
				LOGGER.info("Fehler beim Aktualisieren der Entity.");
				keepDialogOpen();
				displayErrorMessageToUser("A problem occurred while updating. Try again later");
				e.printStackTrace();
			}
		} else {
			displayErrorMessageToUser("You are not allowed to update an entity.");
		}
	}

	public void deleteEntity() {
		if (this.userBean.isCurrentUserAdmin()) {
			try {
				getEntityFacade().deleteEntity(generalEntity);
				closeDialog();
				displayInfoMessageToUser("Deleted with success");
				loadEntities();
				resetEntity();
				LOGGER.info("Entity erfolgreich gelöscht.");
			} catch (Exception e) {
				LOGGER.info("Fehler beim Löschen der Entity.");
				keepDialogOpen();
				displayErrorMessageToUser("A problem occurred while removing. Try again later");
				e.printStackTrace();
			}
		} else {
			displayErrorMessageToUser("You are not allowed to delete an entity.");
		}
	}

	public void setGeneralEntityForDetail(GeneralEntity generalEntity) {
		generalEntityForDetail = generalEntity;
	}

	public GeneralEntity getGeneralEntityForDetail() {
		if (generalEntityForDetail == null) {
			generalEntityForDetail = new GeneralEntity();
		}

		return generalEntityForDetail;
	}

	public void resetGeneralEntityForDetail() {
		generalEntityForDetail = new GeneralEntity();
	}

	public EntityFacade getEntityFacade() {
		if (entityFacade == null) {
			entityFacade = new EntityFacade();
		}

		return entityFacade;
	}

	public GeneralEntity getGeneralEntity() {
		if (generalEntity == null) {
			generalEntity = new GeneralEntity();
		}

		return generalEntity;
	}

	public void setGeneralEntity(GeneralEntity generalEntity) {
		this.generalEntity = generalEntity;
	}

	public List<GeneralEntity> getAllEntities() {
		if (generalEntities == null) {
			loadEntities();
		}

		return generalEntities;
	}

	private void loadEntities() {
		try{
			generalEntities = getEntityFacade().listAll();
			LOGGER.info("Entity wurden erfolgreich geladen.");
		} catch (Exception e){
			LOGGER.log(Level.SEVERE, "Entity konnten nicht geladen werden!");
		}
	}

	public void resetEntity() {
		generalEntity = new GeneralEntity();
	}
	
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	public UserBean getUserBean() {
		return userBean;
	}

}