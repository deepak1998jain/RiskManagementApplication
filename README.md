# Risk Management Application

## Financial introduction

Some things may be confusing when reading about them for the first time. Here is a brief glossary of
terms used throughout the code:

* **Instrument** - a contract of some sort, e.g. a stock, bond, option, future, etc., traded on a
  financial market. The Apple stock is an instrument.
* **Member** - an organization that is a member of a financial market, e.g. a bank. Swedbank is a
  member of the Nasdaq Stockholm stock exchange.
* **Account** - in this context a position account, belonging to a member. One member can have many
  accounts. An account can hold many instruments.
* **Position** - a holding of an instrument in a portfolio (i.e. either a member or an account). *
  *It is entirely possible for the holding
  to be negative**, this is referred to as a short position.
* **Trade** - a transaction in an instrument on an account, e.g. a purchase or sale.
    * The quantity of a trade is always positive.
    * A trade can be a "buy" or a "sell" trade, where the buy trade increases the position and the
      sell trade decreases the position.
    * A trade can be of three types - "new", indicating an entirely new trade that should be added
      to
      the account, "replace", indicating that an existing trade should be updated, and "cancel",
      indicating that an existing trade should be removed from the account.
* **Market risk** - the risk of loss for an account or member due to changes in market conditions.
* **PnL** - profit and loss, the net gain or loss on a position or portfolio (compared with
  market prices). If I buy 10 stocks for $100 each and they are now worth $110 each, my PnL is
  $100 ($10 * 10 stocks).

## Design

This application is a risk management application.

It is designed to take **input data** in the form of

* "Reference data", e.g. data about instruments, members and accounts.
* "Market data", for this application only instrument prices will be required.
* "Trade data", i.e. which trades have occurred on the accounts.

It will calculate **positions** based on the trades.

The application will then calculate the **market risk** for each account and member.

## Tasks

The application is not yet complete. It has a few bugs and missing features. There are failing tests
that test the missing or broken functionality.
It is up to you to fix them. You can make any change you want, as long as the application still
supports the existing api
interfaces.

**N.B** If the requirements are unclear to you - feel free to make an assumption and document them
in the
code.

**Part 1**: Fix the bugs and add the missing features. Add tests where needed.

1. Position keeping bugs - trades are not updating account positions correctly! Find all bugs and
   write tests to verify the expected behaviour.
   See the definition of a trade above and the `TradeApi` to understand how trades should be
   applied.
2. Member aggregation feature - the application currently only calculates the positions for each
   account. It should also calculate the positions for each member (containing multiple accounts).
   Add this feature. `AppTest.twoTradesSameMemberPnl` is a failing test that tests that two trades
   on different accounts should get aggregated member pnl.

**Part 2**: Implement one of the following features:

1. Recovery - when the application restarts, it has forgotten everything about what happened
   before
   it crashed!
   It should be able to recover the last known state! I.e. if I have entered a trade through the
   API, and received a response, and then the application crashes, when I restart the application,
   the trade should still be there. `AppTest.recoverState` is a failing test that tests this.
2. Market risk calculation - the `RiskCalculator` class currently only calculates PnL (profit and
   loss). It should also calculate the market risk for a member. Market risk is calculated as the
   MAXIMUM of the "UP" and "DOWN" scenarios, where:
    * Summed over each position:
        * If it is an option, the UP scenario is market risk is 15% of the market price * quantity
          and the DOWN scenario is market risk is -10% of the market price * quantity.
        * If it is a stock, the UP scenario is market risk is 20% of the market price * quantity and
          the DOWN scenario is market risk is -20% of the market price * quantity.
    * Example:
        - Position 1 has UP -15 and DOWN 10
        - Position 2 has UP 20 and DOWN -20
        - Total UP is 5 and total DOWN is -10
        - Market risk is 5

**Remember to write tests for this feature!**

## Setup

#### Prerequisites

- Java 21
- Gradle 8.5
- An editor, such as IntelliJ IDEA or vscode

#### Running the application

No need - for this exercise, we will verify the application with tests only.

#### Running the tests

Run the command `gradle test`, or run the tests from your editor.

#### Submitting a solution

Create a branch with your name, e.g. `git checkout -b john-doe`, and commit your changes to that.
When you are done, push the branch to the git repository and **create a pull request**.