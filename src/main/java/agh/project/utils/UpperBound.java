package agh.project.utils;

import java.util.Arrays;
import java.util.List;

public class UpperBound {
    public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key){
        int l=0,r=list.size();
        while (l+1<r){
            int middle = (r+l)/2;
            int compareValue = list.get(middle).compareTo(key);
//            System.out.println(""+list.get(middle)+" "+key + " " + compareValue);
            if(compareValue<=0) { //list[m]<=key
                l = middle;
            }
            else {
                r = middle;
            }
        }
        return l+1;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2,2);
        System.out.println(UpperBound.binarySearch(list,2));
    }
}
