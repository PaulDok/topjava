package ru.javawebinar.topjava.repository.mock;

import org.springframework.core.CollectionFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        UserMeal meal = repository.get(id);
        if (meal == null || meal.getUserId() != LoggedUser.id())
            return false;
        repository.remove(id);
        return true;
    }

    @Override
    public UserMeal get(int id) {
        UserMeal meal = repository.get(id);
        if (meal == null || meal.getUserId() != LoggedUser.id())
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        Collection<UserMeal> meals = repository.values();
        List<UserMeal> mealList = meals.stream()
                .filter(userMeal -> userMeal.getUserId() == LoggedUser.id())
                .collect(Collectors.toList());
        Collections.sort(mealList, Comparator.comparing(UserMeal::getDateTime).reversed());
        return mealList;
    }
}

