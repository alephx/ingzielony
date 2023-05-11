package pl.apala.ing.atmservice;

import pl.apala.ing.atmservice.model.Task;

import java.util.Iterator;

// iterator, ktory pomija duble atmId na liscie allTasks.
class SolutionIterator implements Iterator<Task> {

    private final Task[] tasks;
    private int pos = 0;

    public SolutionIterator(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean hasNext() {
        return pos < tasks.length;
    }

    @Override
    public Task next() {
        var val = tasks[pos];
        while (++pos < tasks.length) { // jesli za obecna val sa jakies z tym samym region i atmId pomin je
            var next = tasks[pos];
            if (next.getAtmId() != val.getAtmId() || next.getRegion() != val.getRegion()) {
                break;
            }
        }
        return val;
    }
}
