package ru.javawebinar.topjava.web.meal;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.web.meal.UserMealRestController.REST_URL;
import static ru.javawebinar.topjava.MealTestData.*;

public class UserMealRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "/" + MEAL1_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MEAL1_ID))
                .andExpect(status().isOk());
        List<UserMeal> list = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        MATCHER.assertCollectionEquals(list, mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(USER_MEALS));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = getUpdated();
        mockMvc.perform(put(REST_URL + "/" + updated.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(updated.getId(), AuthorizedUser.id()));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        UserMeal expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .param("startDate", "2015-05-30T12:00:00")
                .param("startTime", "2015-05-30T12:00:00")
                .param("endDate", "2015-05-30T21:00:00")
                .param("endTime", "2015-05-30T21:00:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MATCHER.contentListMatcher(Arrays.asList(MEAL3, MEAL2))));
    }

}