package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.contentJson;
import static ru.javawebinar.topjava.UserTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        MealsUtil.createWithExceed(MEAL1, false)
                        , MealsUtil.createWithExceed(MEAL2, false)
                        , MealsUtil.createWithExceed(MEAL3, false)
                        , MealsUtil.createWithExceed(MEAL4, true)
                        , MealsUtil.createWithExceed(MEAL5, true)
                        , MealsUtil.createWithExceed(MEAL6, true)
                )));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        int saveUserId = AuthorizedUser.id();
        AuthorizedUser.setId(ADMIN_ID);
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(ADMIN_ID), ADMIN_MEAL2);
        AuthorizedUser.setId(saveUserId);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(mealService.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = getCreated();
        expected.setUser(ADMIN);
        int saveId = AuthorizedUser.id();
        AuthorizedUser.setId(ADMIN_ID);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        AuthorizedUser.setId(saveId);

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(ADMIN_ID), ADMIN_MEAL2, expected, ADMIN_MEAL1);
    }

    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2015-05-30")
                .param("endDate", "2015-05-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        MealsUtil.createWithExceed(MEAL1, false)
                        , MealsUtil.createWithExceed(MEAL2, false)
                        , MealsUtil.createWithExceed(MEAL3, false)

                )));
    }

    @Test
    public void testGetBetweenTime() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filter")
                .param("startTime", "09:00")
                .param("endTime", "11:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        MealsUtil.createWithExceed(MEAL4, true)
                        , MealsUtil.createWithExceed(MEAL1, false)
                )));
    }
}