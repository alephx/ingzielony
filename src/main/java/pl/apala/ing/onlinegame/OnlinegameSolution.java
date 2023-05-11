package pl.apala.ing.onlinegame;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import pl.apala.ing.Application;
import pl.apala.ing.onlinegame.model.Clan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OnlinegameSolution {

    public static void parseAndSolve(InputStream in, OutputStream out) throws IOException {
        OnlinegameSolver solver = new OnlinegameSolver();
        try (JsonIterator jsonIterator = JsonIterator.parse(in, Application.IO_BUFFER_SIZE)) {

            for (String field = jsonIterator.readObject(); field != null; field = jsonIterator.readObject()) {
                switch (field) {
                    case "groupCount" -> solver.setGroupCount(jsonIterator.readInt());
                    case "clans" -> readClans(solver, jsonIterator);
                    default -> throw new IllegalStateException("Unexpected json field: " + field);
                }
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

    private static void readClans(OnlinegameSolver solver, JsonIterator jsonIterator) throws IOException {
        while (jsonIterator.readArray()) {
            Clan clan = new Clan();
            for (String field = jsonIterator.readObject(); field != null; field = jsonIterator.readObject()) {
                switch (field) {
                    case "numberOfPlayers" -> clan.setNumberOfPlayers(jsonIterator.readInt());
                    case "points" -> clan.setPoints(jsonIterator.readInt());
                    default -> throw new IllegalStateException("Unexpected json field: " + field);
                }
            }
            solver.processClan(clan);
        }
    }

    // aby przyspieszyc aplikacje i nie kopiowac wyniku do nowej listy,
    // przetwarzamy dane w locie w iteratorze, ale przez to musimy sami skladac tablice w json
    private static void writeSolutionArray(JsonStream out, Iterator<List<Clan>> iterator) throws IOException {
        out.writeArrayStart();
        out.writeIndention();
        out.writeVal(ArrayList.class, iterator.next()); // 1-wszy element poza while zeby nie dublowac przecinkow
        while (iterator.hasNext()) {
            out.writeMore();
            out.writeVal(ArrayList.class, iterator.next());
        }
        out.writeArrayEnd();
    }

}
