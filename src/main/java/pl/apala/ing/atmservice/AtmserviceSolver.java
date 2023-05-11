package pl.apala.ing.atmservice;

import pl.apala.ing.atmservice.model.Task;

import java.util.*;

public class AtmserviceSolver {

    private final List<Task> allTasks = new ArrayList<>(1024); // w miare duza poczatkowa lista aby zminimalizowac liczbe resize

    private static final Comparator<Task> COMPARATOR = new SolutionComparator();
    private static final Task[] TASK_ARRAY_TYPE = new Task[0];

    public void processTask(Task task) {
        allTasks.add(task);
    }

    public Iterator<Task> getSolution() {
        Task[] tasks = allTasks.toArray(TASK_ARRAY_TYPE); // sortowanie na tablicy jest szybsze niz na List (nie musi kopiowac wyniku)
        Arrays.sort(tasks, COMPARATOR);
        return new SolutionIterator(tasks);
    }

}
