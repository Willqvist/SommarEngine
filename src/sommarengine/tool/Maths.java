package sommarengine.tool;

import java.util.ArrayList;
import java.util.List;

public class Maths {
    public static <T> List<T> removeDuplicates(List<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}
