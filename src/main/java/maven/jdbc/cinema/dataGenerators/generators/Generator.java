package maven.jdbc.cinema.dataGenerators.generators;

@FunctionalInterface
public interface Generator<T>
{
    T generate(T min, T max);
}
