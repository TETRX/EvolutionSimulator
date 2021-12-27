package agh.project.game.information;

import agh.project.game.animals.Animal;
import agh.project.game.animals.Genom;
import agh.project.game.map.AnimalState;

import java.util.Objects;

public class AnimalData {
    private final Animal id; //shouldn't be accessed but useful for identifiyng data objects describing the same Animals at different times
    public final float energy;
    public final Genom genom;
    public final int age;
    public final AnimalState state;
    public final int childrenNumber;
    public final int descendantNumber;

    public AnimalData(Animal id, float energy, Genom genom, int age, AnimalState state, int childrenNumber, int descendantNumber) {
        this.id = id;
        this.energy = energy;
        this.genom = genom;
        this.age = age;
        this.state = state;
        this.childrenNumber = childrenNumber;
        this.descendantNumber = descendantNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalData that = (AnimalData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
