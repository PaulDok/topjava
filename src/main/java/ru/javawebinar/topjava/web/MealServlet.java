package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_USERMEAL = "/mealList.jsp";
    private UserMealDao dao;

    public MealServlet() {
        super();
        dao = new UserMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        String forward = "";
        String action = request.getParameter("action");
        if (action == null)
            action = "listUserMeal";

        if (action.equalsIgnoreCase("delete")) {
            int userMealId = Integer.parseInt(request.getParameter("userMealId"));
            dao.deleteUserMeal(userMealId);
            forward = LIST_USERMEAL;
            List<UserMeal> mealList = dao.getAllUserMeals();
            List<UserMealWithExceed> mealListWithExceed = UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), Integer.MAX_VALUE);
            request.setAttribute("meals", mealListWithExceed);
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int userMealId = Integer.parseInt(request.getParameter("userMealId"));
            UserMeal userMeal = dao.getUserMealById(userMealId);
            request.setAttribute("meal", userMeal);
        } else if (action.equalsIgnoreCase("listUserMeal")) {
            forward = LIST_USERMEAL;
            List<UserMeal> mealList = dao.getAllUserMeals();
            List<UserMealWithExceed> mealListWithExceed = UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), Integer.MAX_VALUE);
            request.setAttribute("meals", mealListWithExceed);
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        long id = -1L;
        if (request.getParameter("userMealId") != null && !request.getParameter("userMealId").equals(""))
            id = Integer.parseInt(request.getParameter("userMealId"));
        UserMeal userMeal = new UserMeal(
                id,
                LocalDateTime.parse(request.getParameter("dateTime") + " 12:00", formatter),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (id == -1) {
            dao.addUserMeal(userMeal);
        } else {
            dao.updateUserMeal(userMeal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USERMEAL);
        List<UserMeal> mealList = dao.getAllUserMeals();
        List<UserMealWithExceed> mealListWithExceed = UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), Integer.MAX_VALUE);
        request.setAttribute("meals", mealListWithExceed);
        view.forward(request, response);
    }
}
