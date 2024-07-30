package com.baeldung.spock.helpermethods

import spock.lang.*

@Title("A selection of tests to demonstrate the use of Spock's Helper Methods")
class AccountUnitTest extends Specification {
    @Shared
    String ACCOUNT_NAME = "My Account"
    @Shared
    BigDecimal TEN_DOLLARS = BigDecimal.TEN
    @Shared
    int ZERO_OVERDRAFT = 0


    @Subject
    Account account = new Account()

    // 3. basic test
    def "given an account when we set its attributes then we get the same values back"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setCurrentBalance(TEN_DOLLARS)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        account.getAccountName() == ACCOUNT_NAME
        account.getCurrentBalance() == TEN_DOLLARS
        account.getOverdraftLimit() == ZERO_OVERDRAFT
    }

    // 4.1 & 4.2. extracted method
    def "given an account and extracted assertion method when we set its attributes then our method matches the values we set"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setCurrentBalance(TEN_DOLLARS)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        verifyAccountRefactoringTrap(account)
        verifyAccountAsserted(account)
        verifyAccountWith(account)
    }

    // 4.3. Return a Boolean
    def "given an account and extracted method that returns a boolean when we set its attributes then our method matches the values we set"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setCurrentBalance(TEN_DOLLARS)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        matchesAccount(account)
    }

    // 5.1. Helper Method: with
    def "given an account when we set its attributes then our 'with' Helper Method simplifies the comparison"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setCurrentBalance(TEN_DOLLARS)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        with(account) {
            getAccountName() == ACCOUNT_NAME
            getCurrentBalance() == TEN_DOLLARS
            getOverdraftLimit() == ZERO_OVERDRAFT
        }
    }


    // 5.2. Helper Method: with for mocks
    def "given a Mock account when we set its attributes then our with Helper Method has the mock in context"() {
        given: 'a mock mockAccount'
        Account mockAccount = Mock()

        when: "we invoke its setters"
        mockAccount.setAccountName("A Name")
        mockAccount.setOverdraftLimit(0)

        then: "our methods were invoked the expected number of times"
        with(mockAccount) {
            1 * setAccountName(_ as String)
            1 * setOverdraftLimit(_)
        }
    }

    // 5.3. Helper Method: verifyAll
    def "given an account when we set its attributes then our 'verifyAll' Helper Method simplifies the comparison"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setCurrentBalance(TEN_DOLLARS)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        verifyAll(account) {
            getAccountName() == ACCOUNT_NAME
            getCurrentBalance() == TEN_DOLLARS
            getOverdraftLimit() == ZERO_OVERDRAFT
        }
    }

    // 5.4. Helper Method: nested with
    def "given an account with an address when we set its attributes then our 'with' Helper Method simplifies the comparison"() {
        given: "an address"
        Address myAddress = new Address()
        def myStreet = "1, The Place"
        def myCity = "My City"
        myAddress.setStreet(myStreet)
        myAddress.setCity(myCity)

        when: "we set attributes on our account"
        account.setAddress(myAddress)
        account.setAccountName(ACCOUNT_NAME)

        then: "the values we retrieve match the ones that we set"
        with(account) {
            getAccountName() == ACCOUNT_NAME

            with(getAddress()) {
                getStreet() == myStreet
                getCity() == myCity
            }
        }

        and: "we can use properties and let Groovy derive the getters"
        with(account) {
            accountName == ACCOUNT_NAME

            with(address) {
                street == myStreet
                city == myCity
            }
        }

        and: "when we don't use helper methods, the structure isn't as clear"
        account.getAccountName() == ACCOUNT_NAME
        def address = account.getAddress()
        address.getCity() == myCity
        address.getStreet() == myStreet
    }

    // 6. How do they work? - with Closure parameter
    def "given an account when we set its attributes then our 'with' Helper Method verifies using a closure"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        with(account, (acct) -> {
            acct.getAccountName() == "My Account"
            acct.getOverdraftLimit() == 0
        })
    }

    // 6. How do they work? normal use with Closure after method call
    def "given an account when we set its attributes then our 'with' Helper Method closure is after the parenthesis"() {
        when: "we set attributes on our account"
        account.setAccountName(ACCOUNT_NAME)
        account.setOverdraftLimit(ZERO_OVERDRAFT)

        then: "the values we retrieve match the ones that we set"
        with(account) {
            getAccountName() == "My Account"
            getOverdraftLimit() == 0
        }
    }

    // Caution! Implicit assertions only occur directly when we use them in the test method, but not when inside called methods.
    // In called methods, equality checks are just statements that evaluate to true/false, but are not power assertions.
    // So, although the code might look like it's asserting equality, when we compare with someone else's account our test still passes!
    private static void verifyAccountRefactoringTrap(Account accountToVerify) {
        accountToVerify.getAccountName() == "Someone else's account"
        accountToVerify.getCurrentBalance() == BigDecimal.ZERO
        accountToVerify.getOverdraftLimit() == 9999
    }

    // we can combine our assertions with a boolean 'and' and return a boolean from our method.
    boolean matchesAccount(Account accountToVerify) {
        accountToVerify.getAccountName() == ACCOUNT_NAME
                && accountToVerify.getCurrentBalance() == TEN_DOLLARS
                && accountToVerify.getOverdraftLimit() == ZERO_OVERDRAFT
    }

    // or we can add 'assert' to explicitly make them assertions
    void verifyAccountAsserted(Account accountToVerify) {
        assert accountToVerify.getAccountName() == ACCOUNT_NAME
        assert accountToVerify.getCurrentBalance() == TEN_DOLLARS
        assert accountToVerify.getOverdraftLimit() == ZERO_OVERDRAFT
    }

    // or we can use 'with' since it invokes implicit assertions
    // but you can't use 'with' in a static method - it must be in an instance method.
    private void verifyAccountWith(Account accountToVerify) {
        with(accountToVerify) {
            getAccountName() == ACCOUNT_NAME
            getCurrentBalance() == TEN_DOLLARS
            getOverdraftLimit() == ZERO_OVERDRAFT
        }
    }

}
