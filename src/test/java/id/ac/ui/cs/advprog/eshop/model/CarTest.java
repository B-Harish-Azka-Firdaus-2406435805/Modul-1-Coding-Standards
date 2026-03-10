package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarGettersAndSetters() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        assertEquals("car-1", car.getCarId());
        assertEquals("Toyota Avanza", car.getCarName());
        assertEquals("Red", car.getCarColor());
        assertEquals(5, car.getCarQuantity());
    }
}
