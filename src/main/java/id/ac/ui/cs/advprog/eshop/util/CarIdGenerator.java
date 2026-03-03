package id.ac.ui.cs.advprog.eshop.util;

import java.util.UUID;

public class CarIdGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}