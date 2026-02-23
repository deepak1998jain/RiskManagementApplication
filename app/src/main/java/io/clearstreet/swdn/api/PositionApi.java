package io.clearstreet.swdn.api;

import io.clearstreet.swdn.model.Position;
import java.util.List;

public interface PositionApi {

  /**
   * Get the positions for a member.
   *
   * @param memberName the member name
   * @return the positions for the member
   */
  List<Position> getPositionsForMember(String memberName);

  /**
   * Get the positions for an account.
   *
   * @param accountName the account name
   * @return the positions for the account
   */
  List<Position> getPositionsForAccount(String accountName);
}
