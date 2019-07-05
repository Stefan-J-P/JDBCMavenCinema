package maven.jdbc.cinema.dataGenerators.generators;
import java.util.Random;

public class IntegerGenerator implements Generator<Integer>
{
    @Override
    public Integer generate(Integer min, Integer max)
    {
        return new Random().nextInt(max - min + 1) + min;
    }
}
