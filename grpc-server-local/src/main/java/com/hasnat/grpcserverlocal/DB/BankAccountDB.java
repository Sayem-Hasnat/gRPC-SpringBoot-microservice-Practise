package com.hasnat.grpcserverlocal.DB;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BankAccountDB {
    /*
    This is a DB
    1 => 10
    2 => 20
    ...
    10 => 100
 */

    private static final Map<Integer, Integer> AccountData = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(
                    Function.identity(),
                    v -> v*100)
            );

    public static int getBalance(int accountId) {
        return AccountData.get(accountId);
    }

    public static Integer addBalance(int accountId, int amount) {
        return AccountData.computeIfPresent(accountId, (k, v) -> v + amount);
    }

    public static Integer deductBalance(int accountId, int amount) {
        return AccountData.computeIfPresent(accountId, (k, v) -> v - amount);
    }

    public static void printAccountDetails() {
        System.out.println(AccountData);
    }
}
