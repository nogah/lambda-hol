package reduction;

public class Person {

   private String firstName;
   private final City city;
   private int age;

   public Person(City city, String firstName, int age) {
      this.firstName = firstName;
      this.city = city;
      this.age = age;
   }

   public String firstName() {
      return firstName;
   }

   public City city() {
      return city;
   }

   public int age() {
      return age;
   }
}
