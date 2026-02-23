package io.clearstreet.swdn.api;


public interface RiskApi {

  /**
   * Calculate the PnL for an account.
   *
   * @param accountName account to calculate for
   * @return the profit and loss for the account
   */
  double calculateAccountPnl(String accountName);

  /**
   * Calculate the pnl for a member.
   *
   * @param memberName member to calculate for
   * @return the profit and loss for the member
   */
  double calculateMemberPnl(String memberName);

  /**
   * Calculate the margin for a member.
   *
   * @param memberName member to calculate for
   * @return the margin for the member
   */
  double calculateMemberMargin(String memberName);
}
