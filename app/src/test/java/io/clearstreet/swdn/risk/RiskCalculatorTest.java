package io.clearstreet.swdn.risk;

import static io.clearstreet.swdn.Fixtures.IBM_STOCK;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN_ACCOUNT_1;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN_POSITION_1;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN_POSITION_2;
import static io.clearstreet.swdn.Fixtures.JP_MORGAN_POSITION_3;
import static io.clearstreet.swdn.Fixtures.TSLA_STOCK;

import io.clearstreet.swdn.position.PositionManager;
import io.clearstreet.swdn.price.PriceRepository;
import io.clearstreet.swdn.refdata.ReferenceDataRepository;
import java.util.List;
import java.util.Optional;

import io.clearstreet.swdn.store.PriceStore;
import io.clearstreet.swdn.store.TradeStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RiskCalculatorTest {

  public static final double DELTA = 0.0001;
  @Mock
  private PriceRepository priceRepository;
  @Mock
  private PositionManager positionManager;
  @Mock
  private ReferenceDataRepository referenceDataRepository;


  @BeforeEach
  void clearStores() {
    TradeStore.clear();
    PriceStore.clear();
  }

  @Test
  void calculateAccountPnl() {
    // Given
    RiskCalculator riskCalculator = new RiskCalculator(positionManager, priceRepository,
        referenceDataRepository);
    Mockito.when(positionManager.getPositionsForAccount(JP_MORGAN_ACCOUNT_1.accountName()))
        .thenReturn(List.of((JP_MORGAN_POSITION_1)));
    Mockito.when(priceRepository.getPrice(IBM_STOCK.instrumentName()))
        .thenReturn(Optional.of(100.0));

    // When
    double accountPnl = riskCalculator.calculateAccountPnl(JP_MORGAN_ACCOUNT_1.accountName());

    // Then
    double expectedPnl =
        100.0 * JP_MORGAN_POSITION_1.quantity() - JP_MORGAN_POSITION_1.initialValue();
    Assertions.assertEquals(expectedPnl, accountPnl, DELTA);
  }

  @Test
  void calculateMemberPnl() {
    // Given
    RiskCalculator riskCalculator = new RiskCalculator(positionManager, priceRepository,
        referenceDataRepository);
    Mockito.when(positionManager.getPositionsForMember(JP_MORGAN.memberName()))
        .thenReturn(List.of(JP_MORGAN_POSITION_1, JP_MORGAN_POSITION_3));
    Mockito.when(priceRepository.getPrice(IBM_STOCK.instrumentName()))
        .thenReturn(Optional.of(100.0));
    Mockito.when(priceRepository.getPrice(TSLA_STOCK.instrumentName()))
        .thenReturn(Optional.of(100.0));

    // When
    double memberPnl = riskCalculator.calculateMemberPnl(JP_MORGAN.memberName());

    // Then
    double expectedPnl =
        100.0 * (JP_MORGAN_POSITION_1.quantity() + JP_MORGAN_POSITION_2.quantity()) - (
            JP_MORGAN_POSITION_1.initialValue() + JP_MORGAN_POSITION_2.initialValue());
    Assertions.assertEquals(expectedPnl, memberPnl, DELTA);
  }
}