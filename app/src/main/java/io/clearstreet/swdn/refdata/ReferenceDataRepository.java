package io.clearstreet.swdn.refdata;

import io.clearstreet.swdn.api.ReferenceDataApi;
import io.clearstreet.swdn.model.Account;
import io.clearstreet.swdn.model.Instrument;
import io.clearstreet.swdn.model.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReferenceDataRepository implements ReferenceDataApi {

  private final Map<String, Member> members = new HashMap<>();
  private final Map<String, Account> accounts = new HashMap<>();
  private final Map<String, Instrument> instruments = new HashMap<>();

  @Override
  public boolean enterInstrument(Instrument instrument) {
    instruments.put(instrument.instrumentName(), instrument);
    return true;
  }

  @Override
  public boolean enterAccount(Account account) {
    accounts.put(account.accountName(), account);
    return true;
  }

  @Override
  public boolean enterMember(Member member) {
    members.put(member.memberName(), member);
    return true;
  }

  public Optional<Instrument> getInstrument(String instrumentName) {
    return Optional.ofNullable(instruments.get(instrumentName));
  }

  public Optional<Account> getAccount(String accountName) {
    return Optional.ofNullable(accounts.get(accountName));
  }

  public Optional<Member> getMember(String memberName) {
    return Optional.ofNullable(members.get(memberName));
  }
}
