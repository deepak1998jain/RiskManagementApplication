package io.clearstreet.swdn.price;

import io.clearstreet.swdn.api.PriceApi;
import io.clearstreet.swdn.model.Price;
import io.clearstreet.swdn.store.PriceStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PriceRepository implements PriceApi {

  Map<String, Price> prices = new HashMap<>();

  public PriceRepository() {
    for (Price price : PriceStore.load()) {
      prices.put(price.instrumentName(), price);
    }
  }

  @Override
  public boolean enterPrice(Price price) {
    prices.put(price.instrumentName(), price);
    PriceStore.save(price);
    return true;
  }

  public Optional<Double> getPrice(String instrumentName) {
    return Optional
        .ofNullable(prices.get(instrumentName))
        .map(Price::price);
  }
}
