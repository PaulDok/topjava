package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);

    // false if not found or from other user
    boolean delete(int id);

    // null if not found
    UserMeal get(int id);

    Collection<UserMeal> getAll();
}
