package pl.apala.ing;

import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import io.undertow.Undertow;
import pl.apala.ing.atmservice.AtmserviceSolver;
import pl.apala.ing.atmservice.model.Task;
import pl.apala.ing.onlinegame.OnlinegameSolver;
import pl.apala.ing.onlinegame.model.Clan;
import pl.apala.ing.transactions.TransactionsSolver;

import java.math.BigDecimal;

public class Application {

    public static final int IO_BUFFER_SIZE = 15*1024; //15k. mniejszy niz bufor undertow 16k

    private static void setupServer() {
        var handler = new Routes().handler();
        var server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(handler)
                .setIoThreads(12)  // zakladamy 10 op/s, wiec tyle zeby mozna przyjac wszystkie jednoczesnie
                .setWorkerThreads(48)
                .setDirectBuffers(true)
                .build();
        server.start();
    }

    private static void tuneJson() {
//        JsonIterator.setMode(DecodingMode.DYNAMIC_MODE_AND_MATCH_FIELD_WITH_HASH);
        JsonStream.setMode(EncodingMode.DYNAMIC_MODE);
    }

    public static void main(String[] args) {
        System.out.println("Starting...");
        tuneJson();
        warmUp();
        setupServer();
    }


    private static void warmUp() {
        // probny przebieg, aby przed uruchomieniem zaladowaly sie wszystkie klasy itd.

        var transactionsSolver = new TransactionsSolver();
        transactionsSolver.processTransaction("06105023389842834748547303", "31074318698137062235845814",BigDecimal.TEN);
        var json = JsonStream.serialize(transactionsSolver.getSolution());
        System.out.println(json);

        AtmserviceSolver atmserviceSolver = new AtmserviceSolver();
        var task = new Task();
        task.setRegion(1);
        task.setAtmId(2);
        task.setRequestType(1);
        atmserviceSolver.processTask(task);
        json = JsonStream.serialize(atmserviceSolver.getSolution().next());
        System.out.println(json);

        OnlinegameSolver onlinegameSolver = new OnlinegameSolver();
        onlinegameSolver.setGroupCount(1);
        var clan = new Clan();
        clan.setNumberOfPlayers(1);
        clan.setPoints(23);
        onlinegameSolver.processClan(clan);
        json = JsonStream.serialize(onlinegameSolver.getSolution().next());
        System.out.println(json);

    }

}
