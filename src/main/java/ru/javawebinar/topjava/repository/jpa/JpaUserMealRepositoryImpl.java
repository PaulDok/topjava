package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (userMeal.isNew()) {
            userMeal.setUser(ref);
            em.persist(userMeal);
            return userMeal;
        } else {
            UserMeal oldMeal = em.find(UserMeal.class, userMeal.getId());
            if (oldMeal.getUser().getId() == userId) {
                userMeal.setUser(ref);
                return em.merge(userMeal);
            } else {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        UserMeal meal = em.find(UserMeal.class, id);
        if (meal.getUser().getId() != userId)
            return false;
        return em.createNamedQuery(UserMeal.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal meal = em.find(UserMeal.class, id);
        if (meal.getUser().getId() == userId)
            return meal;
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.BETWEEN_SORTED).setParameter(1, startDate).setParameter(2, endDate).getResultList();
    }
}