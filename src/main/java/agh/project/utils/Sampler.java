package agh.project.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Sampler<T> {
    private final Random random;

    public Sampler(Random random) {
        this.random = random;
    }

    public T randomElement(List<T> dataset){
        return dataset.get(random.nextInt(dataset.size()));
    }

    public List<T> randomSample(List<T> dataset, int n){
        for (int i=0;i<n; i++){
            Collections.swap(dataset, i, random.nextInt(dataset.size()-i)+i);
        }
        return dataset.stream().limit(n).collect(Collectors.toList());
    }
}
