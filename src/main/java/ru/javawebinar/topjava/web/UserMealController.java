package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Павел on 11.07.2016.
 */
@Controller
public class UserMealController {
    @Autowired
    private UserMealRestController controller;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action == null) {
        } else if (action.equals("delete")){
            int id = getId(request);
            controller.delete(id);
            model.addAttribute("mealList", controller.getAll());
            return "mealList";
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    controller.get(getId(request));             // update
            model.addAttribute("meal", meal);
            return "mealEdit";
        }
        model.addAttribute("mealList", controller.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String updateMeal(Model model, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            if (action == null) {
                final UserMeal userMeal = new UserMeal(
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));

                if (request.getParameter("id").isEmpty()) {
                    controller.create(userMeal);
                } else {
                    controller.update(userMeal, getId(request));
                }
                model.addAttribute("mealList", controller.getAll());
                return "mealList";
            } else if (action.equals("filter")) {
                LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
                LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
                LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
                LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
                model.addAttribute("mealList", controller.getBetween(startDate, startTime, endDate, endTime));
                return "mealList";
            }
        } catch (UnsupportedEncodingException ignored) {}
        model.addAttribute("mealList", controller.getAll());
        return "mealList";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
