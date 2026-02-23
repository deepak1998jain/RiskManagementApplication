package io.clearstreet.swdn.model;

/**
 * An instrument in the risk system. An instrument can be a stock or an option.
 */
public record Instrument(String instrumentName, InstrumentType type) {

}
