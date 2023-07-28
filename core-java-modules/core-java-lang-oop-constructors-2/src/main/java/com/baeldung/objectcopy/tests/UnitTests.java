public class PersonTest {
   private List<String> hobbies;
    @Test
    public void testClone() {
        hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Swimming");
    }
    @Test
    public givenPersonObject_whenCreatingShallowCopy_thenBothReferToSameData() {
        Person original = new Person("John Bull", 69, hobbies);

        Person shallowCopy = original;

        original.getHobbies().add("Cycling");

        assertEquals(original.getHobbies(), shallowCopy.getHobbies());
    }

    @Test
    public void givenPersonObject_whenCreatingDeepCopy_thenBothReferToDifferentData() {
        Person original = new Person("John Bull", 69, hobbies);

        Person deepCopy = (Person) original.clone();

        original.getHobbies().add("Cycling");

        assertNotEquals(original.getHobbies(), deepCopy.getHobbies());
    }
}
