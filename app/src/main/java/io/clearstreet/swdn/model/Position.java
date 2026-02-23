package io.clearstreet.swdn.model;

/**
 * A position in the risk system. A position is a holding of an instrument on an account or member.
 *
 * @param portfolioName  the name of the portfolio - either account name or member name
 * @param instrumentName the instrument associated with the position
 * @param quantity       the quantity of the instrument (positive for long positions, negative for
 *                       short positions)
 * @param initialValue   the initial value of the position (positive for long positions, negative
 *                       for short positions)
 */
public record Position(String portfolioName, String instrumentName, double quantity,
                       double initialValue) {

}
