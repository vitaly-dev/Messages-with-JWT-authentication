package com.github.vitalydev.messages.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MealsUtil {

  /*  public static List<MessageTo> getTos(Collection<Message> messages, int caloriesPerDay) {
        return filterByPredicate(messages, caloriesPerDay, meal -> true);
    }

    public static List<MessageTo> getFilteredTos(Collection<Message> messages, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(messages, caloriesPerDay, meal -> Util.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public static List<MessageTo> filterByPredicate(Collection<Message> messages, int caloriesPerDay, Predicate<Message> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = messages.stream()
                .collect(
                        Collectors.groupingBy(Message::getDate, Collectors.summingInt(Message::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return messages.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static MessageTo createTo(Message message, boolean excess) {
        return new MessageTo(message.getId(), message.getDateTime(), message.getDescription(), message.getCalories(), excess);
    }*/
}
