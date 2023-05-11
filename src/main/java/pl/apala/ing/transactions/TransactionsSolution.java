package pl.apala.ing.transactions;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import pl.apala.ing.Application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

public class TransactionsSolution {



    public static void parseAndSolve(InputStream in, OutputStream out) throws IOException {

        TransactionsSolver solver = new TransactionsSolver();
        try (JsonIterator jsonIterator = JsonIterator.parse(in, Application.IO_BUFFER_SIZE)) {
            while (jsonIterator.readArray()) {
                String debitAccount = null, creditAccount = null;
                BigDecimal amount = null;
                for (String field = jsonIterator.readObject(); field != null; field = jsonIterator.readObject()) {
                    switch (field) {
                        case "debitAccount" -> debitAccount = jsonIterator.readString();
                        case "creditAccount" -> creditAccount = jsonIterator.readString();
                        case "amount" -> amount = jsonIterator.readBigDecimal();
                        default -> throw new IllegalStateException("Unexpected json field: " + field);
                    }
                }
                solver.processTransaction(creditAccount, debitAccount, amount);
            }
        }

        try (JsonStream outStream = new JsonStream(out, Application.IO_BUFFER_SIZE)) {
            outStream.writeVal(solver.getSolution());
        }
    }

}
