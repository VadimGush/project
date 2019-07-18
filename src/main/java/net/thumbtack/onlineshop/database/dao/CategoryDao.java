package net.thumbtack.onlineshop.database.dao;

import net.thumbtack.onlineshop.database.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class CategoryDao implements Dao {

    private EntityManager manager;

    @Autowired
    public CategoryDao(EntityManager manager) {
        this.manager = manager;
    }

    public void insert(Category category) {
        manager.persist(category);
    }

    public void update(Category category) {
        manager.merge(category);
    }

    public void delete(Category category) {
        manager.remove(manager.merge(category));
    }

    public boolean exists(String name) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Category> from = criteria.from(Category.class);

        criteria.select(builder.count(from));
        criteria.where(builder.equal(from.get("name"), name));

        TypedQuery<Long> typed = manager.createQuery(criteria);

        return typed.getSingleResult() != 0;
    }

    public Category get(long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> from = criteria.from(Category.class);

        criteria.select(from);
        criteria.where(builder.equal(from.get("id"), id));

        TypedQuery<Category> typed = manager.createQuery(criteria);
        try {
            return typed.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    /**
     * Получает список всех
     *
     * @return
     */
    public List<Category> getAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> from = criteria.from(Category.class);

        criteria.select(from);

        TypedQuery<Category> typed = manager.createQuery(criteria);

        return typed.getResultList();
    }

    public void clear() {
        manager.createNativeQuery("delete from category")
                .executeUpdate();
    }
}
