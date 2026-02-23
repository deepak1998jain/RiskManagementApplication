package io.clearstreet.swdn.api;

import io.clearstreet.swdn.model.Trade;

public interface TradeApi {

  /**
   * Enters a trade into the system.
   * <ul>
   *   <li>For NEW trades, tradeId must not already exist</li>
   *   <li>For CANCEL trades, tradeId must exist and not already be cancelled</li>
   *   <li>For REPLACE trades, tradeId must exist and not be cancelled</li>
   *   <li>Multiple REPLACE trades for the same tradeId are allowed </li>
   * </ul>
   *
   * @param trade the trade to enter
   * @return true if the trade was successfully entered, false otherwise
   */
  boolean enterTrade(Trade trade);
}
