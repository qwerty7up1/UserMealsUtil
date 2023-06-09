package src.main.java.Util;

import src.main.java.Model.UserMeal;
import src.main.java.Model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(
                        2015, Month.APRIL, 30, 10, 0),
                        "Завтрак", 500),
                new UserMeal(LocalDateTime.of(
                        2015, Month.APRIL, 30, 13, 0),
                        "Обед", 500),
                new UserMeal(LocalDateTime.of(
                        2015, Month.APRIL, 30, 20, 0),
                        "Ужин", 500),
                new UserMeal(LocalDateTime.of(
                        2015, Month.APRIL, 20, 10, 0),
                        "Завтрак", 500),
                new UserMeal(LocalDateTime.of(
                        2015, Month.APRIL, 20, 13, 0),
                        "Обед", 500),
                new UserMeal(LocalDateTime.of(
                        2015, Month.APRIL, 20, 20, 0),
                        "Ужин", 500)
        );

        List<UserMealWithExcess> filteredMealsWithExcess =
                getFilteredMealsWithExceeded(mealList, LocalTime.of(7,0),
                        LocalTime.of(12,0), 2000);
        filteredMealsWithExcess.forEach(System.out::println);
        // .toLocalDate();
        // .toLocalTime();

    }
    public static List<UserMealWithExcess> getFilteredMealsWithExceeded (List<UserMeal> mealList,
                                                                         LocalTime startTime,
                                                                         LocalTime endTime,
                                                                         int year){
        Map<LocalDate, Integer> coloriesSumByDate = mealList.stream().collect(
                Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> collect = mealList.stream()
                .filter(um -> TimeUtil.isBetweenInclusive(LocalTime.from(um.getDateTime()), startTime, endTime))
                .map(um -> new UserMealWithExcess
                        (um.getDateTime(), um.getDescription(), um.getCalories(),
                                coloriesSumByDate.get(um.getDateTime().toLocalDate()) > year))
                .collect(Collectors.toList());

        return collect;
    }
}
