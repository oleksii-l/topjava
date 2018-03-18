package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private static String INSERT_OR_EDIT = "/meal-create.jsp";
    private static String LIST_MEALS = "/meals.jsp";

    private MealsDao dao;

    public MealServlet() {
        super();
        this.dao = new MemoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        action = action != null ? action : "";

        if (action.equalsIgnoreCase("delete")) {
            dao.delete(UUID.fromString(request.getParameter("id")));
            forward = LIST_MEALS;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            Meal meal = dao.read(UUID.fromString(request.getParameter("id")));
            request.setAttribute("meal", meal);

        } else if (action.equalsIgnoreCase("listMeals") || action.isEmpty()) {
            forward = LIST_MEALS;
        } else {
            forward = INSERT_OR_EDIT;
        }

        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("mealWithExceed", meals);

        LOG.debug("forward to meals");
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.create(meal);
        } else {
            meal.setId(UUID.fromString(id));
            dao.update(UUID.fromString(id), meal);
        }

        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);

        RequestDispatcher view = req.getRequestDispatcher(LIST_MEALS);
        req.setAttribute("mealWithExceed", meals);
        view.forward(req, resp);

    }
}
