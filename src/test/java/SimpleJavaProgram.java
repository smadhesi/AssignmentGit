import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SimpleJavaProgram {
    public long sumOfAllNumber(long n){
        long sum =(n*(n+1)/2);
        return sum;
    }

    @Test
    public void testSumOfAllNumbers(){
        long sum = sumOfAllNumber(1000000);
        Assertions.assertThat(sum).as("validate sum").isEqualTo(500000500000L);
    }
}
