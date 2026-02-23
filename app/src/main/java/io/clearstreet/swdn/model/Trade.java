package io.clearstreet.swdn.model;

/**
 * A trade is associated with an account and an instrument, which can be either a buy or a sell. The
 * unique identifier for a trade is the tradeId.
 *
 * @param tradeId        the unique identifier for the trade
 * @param accountName    the name of the account associated with the trade
 * @param instrumentName the name of the instrument associated with the trade
 * @param quantity       the quantity of the trade, always positive
 * @param side           the side of the trade, either buy or sell
 * @param tradeType      the type of the trade, either NEW (a new trade), CANCEL (a cancel of a
 *                       trade), or REPLACE (a replace of a trade)
 * @param price          the price of the trade
 */
public record Trade(String tradeId, String accountName, String instrumentName, double quantity,
                    TradeSide side, TradeType tradeType, double price) {

}
