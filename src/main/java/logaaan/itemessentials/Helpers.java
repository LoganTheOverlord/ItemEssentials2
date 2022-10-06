package logaaan.itemessentials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helpers {

    public static List<?> toList(Object[] array){
        return new ArrayList<>(Arrays.asList(array));
    }

    public static boolean arrayContains(Object[] array, Object search){
        List<Object> query = new ArrayList<>(Arrays.asList(array));
        return query.contains(search);
    }
}
