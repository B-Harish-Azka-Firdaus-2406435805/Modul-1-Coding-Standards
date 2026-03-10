package id.ac.ui.cs.advprog.eshop.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarIdGeneratorTest {

    @Test
    void testGenerateReturnsNonNullUUID() {
        CarIdGenerator gen = new CarIdGenerator();
        String id = CarIdGenerator.generate();
        assertNotNull(id);
        assertFalse(id.isEmpty());
    }
}
