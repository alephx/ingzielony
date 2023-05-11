package pl.apala.ing.atmservice;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import pl.apala.ing.Application;
import pl.apala.ing.atmservice.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class AtmserviceSolution {


    public static void parseAndSolve(InputStream in, OutputStream out) throws IOException {
        AtmserviceSolver solver = new AtmserviceSolver();
        try (JsonIterator jsonIterator = JsonIterator.parse(in, Application.IO_BUFFER_SIZE)) {
            while (jsonIterator.readArray()) {
                Task task = new Task();
                for (String field = jsonIterator.readObject(); field != null; field = jsonIterator.readObject()) {
                    switch (field) {
                        case "region" -> task.setRegion(jsonIterator.readInt());
                        case "atmId" -> task.setAtmId(jsonIterator.readInt());
                        case "requestType" -> task.setRequestType(decodeRequestType(jsonIterator.readString()));
                        default -> throw new IllegalStateException("Unexpected json field: " + field);
                    }
                }
                solver.processTask(task);
            }
        }

        var solution = solver.getSolution();
        try (JsonStream outStream = new JsonStream(out, Application.IO_BUFFER_SIZE)) {
            if (!solution.hasNext()) {
                outStream.writeEmptyArray();
            } else {
                writeSolutionArray(outStream, solution);
            }
        }
    }

    // aby przyspieszyc aplikacje i nie kopiowac wyniku przy usuwaniu dubli atmId do nowej listy,
    // usuwamy je w locie w iteratorze, ale przez to musimy sami skladac tablice w json
    private static void writeSolutionArray(JsonStream out, Iterator<Task> iterator) throws IOException {
        out.writeArrayStart();
        out.writeIndention();
        out.writeVal(Task.class, iterator.next()); // 1-wszy element poza while zeby nie dublowac przecinkow
        while (iterator.hasNext()) {
            out.writeMore();
            out.writeVal(Task.class, iterator.next());
        }
        out.writeArrayEnd();
    }

    private static int decodeRequestType(String requestType) {
        return switch (requestType) {  // im wyzszy priorytem tym szybciej powinno byc realizowane
            case "STANDARD" -> 1;
            case "SIGNAL_LOW" -> 2;
            case "PRIORITY" -> 3;
            case "FAILURE_RESTART" -> 4;
            default -> throw new IllegalStateException("Unexpected requestType: " + requestType);
        };
    }

}
