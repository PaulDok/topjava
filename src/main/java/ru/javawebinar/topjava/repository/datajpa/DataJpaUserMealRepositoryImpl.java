package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{
    private static final Sort SORT_DATE_TIME = new Sort("date_time");

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProxyUserMealRepository proxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setUser(em.getReference(User.class, userId));
            return proxy.save(userMeal);
        }
        UserMeal oldMeal = get(userMeal.getId(), userId);
        if (oldMeal != null) {
            userMeal.setUser(em.getReference(User.class, userId));
            return proxy.save(userMeal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal meal = proxy.findOne(id);
        User user = meal.getUser();
        if (meal != null && meal.getUser().getId() == userId) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> list = proxy.findAll();
        list = list.stream()
                .filter(m -> m.getUser().getId() == userId)
                .sorted(Comparator.comparing(UserMeal::getDateTime).reversed())
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<UserMeal> list = proxy.findBetween(userId, startDate, endDate);
        return list;
    }
}
