package io.clearstreet.swdn.store;

import io.clearstreet.swdn.model.Price;
import java.io.*;
import java.util.*;

public class PriceStore {

    private static final String FILE = "prices.txt";

    public static void save(Price price) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(price.instrumentName() + "," + price.price() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Price> load() {
        List<Price> prices = new ArrayList<>();
        File file = new File(FILE);
        if (!file.exists()) return prices;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                prices.add(new Price(p[0], Double.parseDouble(p[1])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }

    public static void clear() {
        new File(FILE).delete();
    }
}
