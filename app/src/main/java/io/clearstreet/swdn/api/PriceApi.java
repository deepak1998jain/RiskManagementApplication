package io.clearstreet.swdn.api;

import io.clearstreet.swdn.model.Price;

public interface PriceApi {

  /**
   * Enter the price for an instrument.
   *
   * @return true if price could be successfully entered, false otherwise
   */
  boolean enterPrice(Price price);
}
