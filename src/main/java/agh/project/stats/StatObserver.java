package agh.project.stats;

public interface StatObserver<T> {
    void update(T newData);
}
