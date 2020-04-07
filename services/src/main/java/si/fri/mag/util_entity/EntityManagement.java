package si.fri.mag.util_entity;

import si.fri.mag.MainEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ApplicationScoped
public class EntityManagement {
    @Inject
    private EntityManager em;

    @Inject
    private EntityTransactions entityTransactions;

    public MainEntity createNewEntity(MainEntity entity) {
        try {
            entityTransactions.beginTx();
            em.persist(entity);
            entityTransactions.commitTx();
        } catch (Exception e) {
            entityTransactions.rollbackTx();
            return null;
        }

        return  entity;
    }

    public MainEntity updateEntity(MainEntity entity) {
        try {
            entityTransactions.beginTx();
            entity = em.merge(entity);
            entityTransactions.commitTx();
        } catch (Exception e) {
            entityTransactions.rollbackTx();
            return null;
        }

        return  entity;
    }


    public boolean executeUpdate(Query query) {
        try {
            entityTransactions.beginTx();
            query.executeUpdate();
            entityTransactions.commitTx();
        } catch (Exception e) {
            entityTransactions.rollbackTx();
            return false;
        }
        return true;
    }
}
