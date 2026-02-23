package io.clearstreet.swdn.store;

import io.clearstreet.swdn.model.Trade;
import io.clearstreet.swdn.model.TradeSide;
import io.clearstreet.swdn.model.TradeType;

import java.io.*;
import java.util.*;

public class TradeStore {

    private static final String FILE_NAME = "trades.txt";

    // Save a trade (append to file)
    public static void save(Trade trade) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(trade.tradeId() + "," +
                    trade.accountName() + "," +
                    trade.instrumentName() + "," +
                    trade.quantity() + "," +
                    trade.side() + "," +
                    trade.tradeType() + "," +
                    trade.price() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Load all trades from file
    public static List<Trade> load() {
        List<Trade> trades = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return trades;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                trades.add(new Trade(
                        p[0],
                        p[1],
                        p[2],
                        Double.parseDouble(p[3]),
                        TradeSide.valueOf(p[4]),
                        TradeType.valueOf(p[5]),
                        Double.parseDouble(p[6])
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return trades;
    }

    public static void clear() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

}
