package io.clearstreet.swdn.model;

/**
 * An account in the risk system. An account always belongs to a member.
 */
public record Account(String accountName, String memberName) {

}
