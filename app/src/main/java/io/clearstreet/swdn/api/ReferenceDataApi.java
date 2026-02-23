package io.clearstreet.swdn.api;

import io.clearstreet.swdn.model.Account;
import io.clearstreet.swdn.model.Instrument;
import io.clearstreet.swdn.model.Member;
import io.clearstreet.swdn.model.Trade;

public interface ReferenceDataApi {
  /**
   * Enters an instrument into the system.
   *
   * @param instrument the instrument to enter
   * @return true if the instrument was successfully entered, false otherwise
   */
  boolean enterInstrument(Instrument instrument);
  /**
   * Enters an account into the system.
   *
   * @param account the account to enter
   * @return true if the account was successfully entered, false otherwise
   */
  boolean enterAccount(Account account);

  /**
   * Enters a member into the system.
   *
   * @param member the member to enter
   * @return true if the member was successfully entered, false otherwise
   */
  boolean enterMember(Member member);
}
