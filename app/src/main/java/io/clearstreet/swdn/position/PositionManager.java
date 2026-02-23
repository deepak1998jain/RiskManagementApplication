package io.clearstreet.swdn.position;

import io.clearstreet.swdn.api.PositionApi;
import io.clearstreet.swdn.api.TradeApi;
import io.clearstreet.swdn.model.Position;
import io.clearstreet.swdn.model.Trade;
import io.clearstreet.swdn.model.TradeSide;
import io.clearstreet.swdn.model.TradeType;
import io.clearstreet.swdn.refdata.ReferenceDataRepository;
import io.clearstreet.swdn.store.TradeStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionManager implements TradeApi, PositionApi {

  private final ReferenceDataRepository referenceDataManager;
  private final Map<String,Trade> trades = new HashMap<>();

  public PositionManager(ReferenceDataRepository referenceDataManager) {
    this.referenceDataManager = referenceDataManager;

    // Replay old trade events if present for Recovery
    for (Trade t : TradeStore.load()) {
      applyRecoveredTrade(t);
    }
  }

  private void applyRecoveredTrade(Trade trade) {
    if (trade.tradeType() == TradeType.NEW) {
      trades.put(trade.tradeId(), trade);
    }
    else if (trade.tradeType() == TradeType.REPLACE) {
      if (trades.containsKey(trade.tradeId())) {
        trades.put(trade.tradeId(), trade);
      }
    }
    else if (trade.tradeType() == TradeType.CANCEL) {
      trades.remove(trade.tradeId());
    }
  }
  @Override
  public boolean enterTrade(Trade trade) {
    if (trade.tradeType().equals(TradeType.NEW)) {
      trades.put(trade.tradeId(), trade);
      TradeStore.save(trade);
    } else if(trade.tradeType().equals(TradeType.REPLACE)) {
        if (!trades.containsKey(trade.tradeId())) {
          return false; // Assuming this is invalid trade operation, replacing a trade that doesn't exist
        }
        trades.put(trade.tradeId(), trade);
        TradeStore.save(trade);
    } else if(trade.tradeType().equals(TradeType.CANCEL)){
        if (!trades.containsKey(trade.tradeId())) {
          return false; // Assuming this is invalid trade operation, canceling a trade that deesn't exist
        }
        trades.remove(trade.tradeId());
        TradeStore.save(trade);
    } else {
      throw new IllegalStateException("Unknown Trade Type");
    }
    return true;
  }

  @Override
  public List<Position> getPositionsForMember(String memberName) {
    Map<String, Position> positions = new HashMap<>();

    for (Trade trade : trades.values()) {
      // Checking if trade belongs to this member
      referenceDataManager.getAccount(trade.accountName())
              .filter(acc -> acc.memberName().equals(memberName))
              .orElse(null);

      var accountOpt = referenceDataManager.getAccount(trade.accountName());
      if (accountOpt.isEmpty() ||
              !accountOpt.get().memberName().equals(memberName)) {
        continue;
      }

      String instrument = trade.instrumentName();

      Position position = positions.getOrDefault(
              instrument,
              new Position(memberName, instrument, 0.0, 0.0)
      );

      double actualQty =
              trade.side() == TradeSide.BUY ? trade.quantity() : -trade.quantity();

      double actualValue = actualQty * trade.price();

      positions.put(
              instrument,
              new Position(
                      memberName,
                      instrument,
                      position.quantity() + actualQty,
                      position.initialValue() + actualValue
              )
      );
    }

    return new ArrayList<>(positions.values());
  }

  @Override
  public List<Position> getPositionsForAccount(String accountName) {
    Map<PositionKey, Position> positions = new HashMap<>();
    for (Trade trade : trades.values()) {
      if (trade.accountName().equals(accountName)) {
        PositionKey key = new PositionKey(trade.accountName(), trade.instrumentName());
        Position position = positions.get(key);
        if (position == null) {
          position = new Position(trade.accountName(), trade.instrumentName(), 0, 0);
        }
        double actualQty = trade.side() == TradeSide.BUY ? trade.quantity() : -trade.quantity();
        double actualValue = actualQty* trade.price();
        positions.put(key, new Position(trade.accountName(), trade.instrumentName(),
            position.quantity() + actualQty,
            position.initialValue() + actualValue));
      }
    }
    return new ArrayList<>(positions.values());
  }

  private record PositionKey(String accountName, String instrumentName) {

  }
}
