package ch.bbzbl.facade;

import java.io.Serializable;
import java.util.List;

import ch.bbzbl.dao.EntityDAO;
import ch.bbzbl.dao.EntityManagerHelper;
import ch.bbzbl.entity.GeneralEntity;

public class EntityFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityDAO entityDAO = new EntityDAO();
	
	public void createEntity(GeneralEntity generalEntity) {
		EntityManagerHelper.beginTransaction();
		entityDAO.save(generalEntity);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public void updateEntity(GeneralEntity generalEntity) {
		EntityManagerHelper.beginTransaction();
		entityDAO.update(generalEntity);
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void deleteEntity(GeneralEntity generalEntity){
		EntityManagerHelper.beginTransaction();
		GeneralEntity persistedEntityWithIdOnly = entityDAO.findReferenceOnly(generalEntity.getId());
		entityDAO.delete(persistedEntityWithIdOnly);
		EntityManagerHelper.commitAndCloseTransaction();
		
	}

	public GeneralEntity findEntity(int personId) {
		EntityManagerHelper.beginTransaction();
		GeneralEntity generalEntity = entityDAO.find(personId);
		EntityManagerHelper.commitAndCloseTransaction();
		return generalEntity;
	}

	public List<GeneralEntity> listAll() {
		EntityManagerHelper.beginTransaction();
		List<GeneralEntity> result = entityDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();

		return result;
	}
}