package manager;

public interface Factory<T extends Manageable> {
    T create();
}
