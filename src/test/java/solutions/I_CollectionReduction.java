package solutions;

import static java.util.Map.entry;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static reduction.City.Athens;
import static reduction.City.London;
import static reduction.City.Tulsa;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import reduction.City;
import reduction.Person;

public class I_CollectionReduction {

   static Person jon = new Person(Tulsa, "Jon", 42);
   static Person amy = new Person(Athens, "Amy", 21);
   static Person bill = new Person(London, "Bill", 33);
   static Person eric = new Person(Athens, "Eric", 33);

   static List<Person> people = Arrays.asList(jon, amy, bill, eric);

   @Test
   public void
   it_groups_people_by_age() {
      Map<Integer, List<Person>> peopleByAge = people.stream()
                                                     .collect(groupingBy(Person::age));

      assertEquals(Map.ofEntries(entry(42, List.of(jon)),
                                 entry(21, List.of(amy)),
                                 entry(33, List.of(bill, eric))
                   ),
                   peopleByAge);
   }

   @Test
   public void
   it_groups_names_by_age() {
      Map<Integer, List<String>> nameByAge = people.stream()
                                                   .collect(groupingBy(Person::age,
                                                                       mapping(Person::firstName,
                                                                               Collectors.toList())));

      assertEquals(Map.ofEntries(entry(42, List.of("Jon")),
                                 entry(21, List.of("Amy")),
                                 entry(33, List.of("Bill", "Eric"))
                   ),
                   nameByAge);
   }

   @Test
   public void
   it_computes_population_by_age() {
      Map<Integer, Long> populationByAge = people.stream()
                                                 .collect(groupingBy(Person::age,
                                                                     Collectors.counting()));

      assertEquals(Map.ofEntries(entry(42, 1L),
                                 entry(21, 1L),
                                 entry(33, 2L)
                   ),
                   populationByAge);
   }

   @Test
   public void
   it_computes_average_age_by_city() {
      Map<City, Double> averageAgeByCity = people.stream()
                                                 .collect(groupingBy(Person::city,
                                                                     averagingDouble(Person::age)));

      assertEquals(Map.ofEntries(entry(London, 33.0),
                                 entry(Athens, 27.0),
                                 entry(Tulsa, 42.0)
                   ),
                   averageAgeByCity);
   }

   @Test
   public void
   it_gives_cities_with_more_than_one_inhabitant() {
      Set<City> populousCity = people.stream()
                                     .collect(groupingBy(Person::city, counting()))
                                     .entrySet().stream()
                                     .filter(e -> e.getValue() > 1)
                                     .map(Map.Entry::getKey)
                                     .collect(toSet());

      assertEquals(Set.of(Athens), populousCity);
   }

   @Test
   public void
   it_computes_most_popular_age() {
      int mostPopularAge = people.stream()
                                 .collect(groupingBy(Person::age, Collectors.counting()))
                                 .entrySet().stream()
                                 .max(Map.Entry.comparingByValue())
                                 .map(Map.Entry::getKey)
                                 .orElse(-1);

      assertEquals(33, mostPopularAge);
   }

}
