package UnitTest;

import model.People;
import org.assertj.core.api.Assertions;

import org.junit.BeforeClass;
import org.junit.Test;
import service.DataService;
import service.RandomDataGenerator;

import java.util.List;

public class DataGeneratorTest {
    private static DataService dataService;
    private static RandomDataGenerator randomDataGenerator;

   @BeforeClass
   public static void init(){
       dataService = new DataService();
       randomDataGenerator = new RandomDataGenerator();
   }

   @Test
   public void testGetGenderMale(){
       String str = randomDataGenerator.getGender(5);
       Assertions.assertThat(str).as("validate sex").isEqualTo("Male");
   }

    @Test
    public void testGetFemale(){
        String str = randomDataGenerator.getGender(4);
        Assertions.assertThat(str).as("validate sex").isEqualTo("Female");
    }

    @Test
    public void testGetMartialStatus_Married(){
        String str = randomDataGenerator.getMartialStatus(4);
        Assertions.assertThat(str).as("validate martial status").isEqualTo("Married");
    }
    @Test
    public void testGetMartialStatus_Unmarried(){
        String str = randomDataGenerator.getMartialStatus(5);
        Assertions.assertThat(str).as("validate martial status").isEqualTo("Unmarried");
    }


 @Test
  public void testEmail(){
      RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
      String str =randomDataGenerator.getDateOfBirth();
      Assertions.assertThat(str).as("validate date of birth").isNotNull();
  }

}
