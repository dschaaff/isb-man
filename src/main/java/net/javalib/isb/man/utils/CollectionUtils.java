package net.javalib.isb.man.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

    public static <T> List<List<T>> partition(List<T> items, int partitionSize) {
        List<List<T>> partitions = new ArrayList<List<T>>();
        for (int i = 0; i < items.size(); i += partitionSize) {
            partitions.add(items.subList(i, i + Math.min(partitionSize, items.size() - i)));
        }
        return partitions;
    }

}
