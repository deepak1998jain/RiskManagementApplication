package io.clearstreet.swdn;

import io.clearstreet.swdn.model.Account;
import io.clearstreet.swdn.model.Instrument;
import io.clearstreet.swdn.model.InstrumentType;
import io.clearstreet.swdn.model.Member;
import io.clearstreet.swdn.model.Position;

public class Fixtures {

  public static final Member JP_MORGAN = new Member("JP Morgan");
  public static final Instrument IBM_STOCK = new Instrument("IBM", InstrumentType.STOCK);
  public static final Instrument TSLA_STOCK = new Instrument("TSLA", InstrumentType.STOCK);
  public static final Account JP_MORGAN_ACCOUNT_1 = new Account("JP Morgan Account 1",
      JP_MORGAN.memberName());
  public static final Account JP_MORGAN_ACCOUNT_2 = new Account("JP Morgan Account 2",
      JP_MORGAN.memberName());

  public static final Position JP_MORGAN_POSITION_1 = new Position(
      JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0, 5000.0);

  public static final Position JP_MORGAN_POSITION_2 = new Position(
      JP_MORGAN_ACCOUNT_2.accountName(), IBM_STOCK.instrumentName(), -100.0, -6000.0);

  public static final Position JP_MORGAN_POSITION_3 = new Position(
      JP_MORGAN_ACCOUNT_2.accountName(), TSLA_STOCK.instrumentName(), -100.0, -6000.0);
}
