package io.clearstreet.swdn.position;

import static io.clearstreet.swdn.Fixtures.IBM_STOCK;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN_ACCOUNT_1;

import io.clearstreet.swdn.model.Position;
import io.clearstreet.swdn.model.Trade;
import io.clearstreet.swdn.model.TradeSide;
import io.clearstreet.swdn.model.TradeType;
import io.clearstreet.swdn.refdata.ReferenceDataRepository;
import java.util.Optional;

import io.clearstreet.swdn.store.PriceStore;
import io.clearstreet.swdn.store.TradeStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PositionManagerTest {

  public static final double DELTA = 0.001;
  @Mock(strictness = Strictness.LENIENT)
  private ReferenceDataRepository referenceDataRepository;

  @BeforeEach
  void clearStores() {
    TradeStore.clear();
    PriceStore.clear();
  }

  @Test
  void enterSingleBuyTrade() {
    // Given
    PositionManager positionManager = getPositionManager();

    // When
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.NEW,
            50.0)));

    // Then
    Assertions.assertEquals(1,
        positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()).size());
    Position position = positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName())
        .get(0);
    Assertions.assertEquals(100.0, position.quantity(), DELTA);
    Assertions.assertEquals(IBM_STOCK.instrumentName(), position.instrumentName());
    Assertions.assertEquals(5000.0, position.initialValue(), DELTA);
  }

  @Test
  void enterSingleSellTrade() {
    // Given
    PositionManager positionManager = getPositionManager();

    // When
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.SELL, TradeType.NEW,
            50.0)));

    // Then
    Assertions.assertEquals(1,
        positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()).size());
    Position position = positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName())
        .get(0);
    Assertions.assertEquals(-100.0, position.quantity(), DELTA);
    Assertions.assertEquals(IBM_STOCK.instrumentName(), position.instrumentName());
    Assertions.assertEquals(-5000.0, position.initialValue(), DELTA);
  }

  @Test
  void enterNetZeroTrades() {
    // Given
    PositionManager positionManager = getPositionManager();

    // When
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.NEW,
            50.0)));
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade2", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.SELL, TradeType.NEW,
            49.0)));

    // Then
    Assertions.assertEquals(1,
        positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()).size());
    Position position = positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName())
        .get(0);
    Assertions.assertEquals(0.0, position.quantity(), DELTA);
    Assertions.assertEquals(IBM_STOCK.instrumentName(), position.instrumentName());
    Assertions.assertEquals(100.0, position.initialValue(), DELTA);
  }

  @Test
  void cancelTrade() {
    // Given
    PositionManager positionManager = getPositionManager();

    // When
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.NEW,
            50.0)));
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.CANCEL,
            50.0)));

    // Then
    Assertions.assertEquals(0,
        positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()).size());
  }

  @Test
  void amendTradePrice() {
    // Given
    PositionManager positionManager = getPositionManager();

    // When
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.NEW,
            50.0)));
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.REPLACE,
            51.0)));

    // Then
    Assertions.assertEquals(1,
        positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()).size());

    Position position = positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName())
        .get(0);
    Assertions.assertEquals(5100.0, position.initialValue(), DELTA);
    Assertions.assertEquals(100.0, position.quantity(), DELTA);
  }

  @Test
  void amendNonExistingTrade() {
    // Given
    PositionManager positionManager = getPositionManager();

    // When
    Assertions.assertTrue(positionManager.enterTrade(
        new Trade("trade1", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.NEW,
            50.0)));
    Assertions.assertFalse(positionManager.enterTrade(
        new Trade("trade2", JP_MORGAN_ACCOUNT_1.accountName(), IBM_STOCK.instrumentName(), 100.0,
            TradeSide.BUY, TradeType.REPLACE,
            51.0)));

    // Then
    Assertions.assertEquals(1,
        positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()).size());

    Position position = positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName())
        .get(0);
    Assertions.assertEquals(5000.0, position.initialValue(), DELTA);
    Assertions.assertEquals(100.0, position.quantity(), DELTA);
  }

  private PositionManager getPositionManager() {
    PositionManager positionManager = new PositionManager(referenceDataRepository);
    Mockito.when(referenceDataRepository.getInstrument(IBM_STOCK.instrumentName()))
        .thenReturn(Optional.of(IBM_STOCK));
    Mockito.when(referenceDataRepository.getMember(JP_MORGAN.memberName()))
        .thenReturn(Optional.of(JP_MORGAN));
    Mockito.when(referenceDataRepository.getAccount(JP_MORGAN_ACCOUNT_1.accountName()))
        .thenReturn(Optional.of(JP_MORGAN_ACCOUNT_1));
    return positionManager;
  }
}