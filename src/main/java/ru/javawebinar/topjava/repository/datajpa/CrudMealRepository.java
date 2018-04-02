package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Meal save(Meal meal);

    Optional<Meal> findByIdAndUser_Id(Integer id, Integer userId);

    List<Meal> findAllByUser_Id(Integer userId, Sort sort);

    List<Meal> findByDateTimeBetweenAndUser_Id(LocalDateTime startDate, LocalDateTime endDate, Integer userId, Sort sort);

    @Query("select m from Meal m join fetch m.user where m.id=?1 and m.user.id=?2")
    Meal findByIdWithUser(Integer id, Integer userId);
}
