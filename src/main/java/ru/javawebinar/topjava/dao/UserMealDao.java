package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.ArrayList;
import java.util.List;

public class UserMealDao {
    private List<UserMeal> mealList = new ArrayList<>();
    private long nextId = 0L;

    public UserMealDao() {
    }

    public void addUserMeal(UserMeal userMeal) {
        long id = userMeal.getId();
        if (id == -1) {
            id = nextId;
            nextId++;
        }
        UserMeal localUserMeal = new UserMeal(id,
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories());
        mealList.add(localUserMeal);
    }

    public void deleteUserMeal(int id) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId() == id) {
                mealList.remove(i);
                return;
            }
        }
    }

    public void updateUserMeal(UserMeal userMeal) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId() == userMeal.getId()) {
                mealList.remove(i);
                mealList.add(i, userMeal);
                return;
            }
        }
        // if no such entry found
        addUserMeal(userMeal);
    }

    public List<UserMeal> getAllUserMeals() {
        return mealList;
    }

    public UserMeal getUserMealById(int id) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId() == id) {
                return mealList.get(i);
            }
        }
        // if no such entry found
        return null;
    }
}
