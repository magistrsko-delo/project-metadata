package si.fri.mag.util_entity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class EntityTransactions {
    @Inject
    private EntityManager em;

    public void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    public void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    public void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
