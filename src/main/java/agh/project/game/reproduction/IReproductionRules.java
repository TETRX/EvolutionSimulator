package agh.project.game.reproduction;

import agh.project.game.animals.Animal;
import agh.project.game.map.AnimalLocationMap;
import agh.project.game.map.AnimalState;
import agh.project.game.map.MapLocation;
import agh.project.game.movement.Bounds;

import java.util.Map;
import java.util.SortedSet;

public interface IReproductionRules {
    ReproductionResults reproduce(AnimalLocationMap map);
}
