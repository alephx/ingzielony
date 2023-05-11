package pl.apala.ing.atmservice;

import pl.apala.ing.atmservice.model.Task;

import java.io.Serializable;
import java.util.Comparator;

public class SolutionComparator implements Comparator<Task>, Serializable {
    @Override
    public int compare(Task o1, Task o2) {
        var region = Integer.compare(o1.getRegion(), o2.getRegion()); // region rosnąco
        if (region != 0) {
            return region;
        }
        return Integer.compare(o2.getRequestType(), o1.getRequestType()); // requestType malejaco
    }
}
