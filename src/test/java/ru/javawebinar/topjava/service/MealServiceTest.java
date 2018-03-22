package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEAL_SEQ;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService sut;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2018, Month.MARCH, 22, 10, 0), "new_item", 400);

        Meal created = sut.create(newMeal, UserTestData.USER_ID);

        newMeal.setId(created.getId());
        List<Meal> expectedResult = new ArrayList();
        expectedResult.add(newMeal);
        expectedResult.addAll(MEALS);
        assertMatch(sut.getAll(UserTestData.USER_ID), expectedResult);
    }

    @Test
    public void delete() throws Exception {
        assertEquals(6, sut.getAll(USER_ID).size());

        sut.delete(MEAL_SEQ, USER_ID);

        assertEquals(5, sut.getAll(USER_ID).size());
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        sut.delete(1, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = sut.get(MEAL_SEQ, USER_ID);
        assertMatch(meal, MEALS.stream().filter(m -> m.getId() == MEAL_SEQ).findFirst().get());
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        sut.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() throws Exception {
        sut.get(MEAL_SEQ, ADMIN_ID);
    }
}