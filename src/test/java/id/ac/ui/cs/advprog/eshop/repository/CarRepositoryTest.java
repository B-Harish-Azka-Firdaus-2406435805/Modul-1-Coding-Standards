package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota Avanza");
        car.setCarColor("Red");
        car.setCarQuantity(2);

        Car result = carRepository.create(car);
        assertNotNull(result.getCarId());
        assertFalse(result.getCarId().isEmpty());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("car-123");
        car.setCarName("Honda Jazz");
        car.setCarColor("Blue");
        car.setCarQuantity(1);

        Car result = carRepository.create(car);
        assertEquals("car-123", result.getCarId());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("car-1");
        car1.setCarName("Toyota Avanza");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Honda Jazz");
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarId("car-abc");
        car.setCarName("Suzuki Ertiga");
        carRepository.create(car);

        Car found = carRepository.findById("car-abc");
        assertNotNull(found);
        assertEquals("car-abc", found.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car found = carRepository.findById("nonexistent");
        assertNull(found);
    }

    @Test
    void testUpdateFound() {
        Car car = new Car();
        car.setCarId("car-xyz");
        car.setCarName("Original Name");
        car.setCarColor("White");
        car.setCarQuantity(3);
        carRepository.create(car);

        Car updated = new Car();
        updated.setCarName("Updated Name");
        updated.setCarColor("Black");
        updated.setCarQuantity(10);

        Car result = carRepository.update("car-xyz", updated);
        assertNotNull(result);
        assertEquals("Updated Name", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updated = new Car();
        updated.setCarName("Ghost Car");
        Car result = carRepository.update("nonexistent", updated);
        assertNull(result);
    }

    @Test
    void testFindByIdNotFoundWithExistingCar() {
        Car car = new Car();
        car.setCarId("car-existing");
        car.setCarName("Existing Car");
        carRepository.create(car);

        Car found = carRepository.findById("car-nonexistent");
        assertNull(found);
    }

    @Test
    void testUpdateNotFoundWithExistingCar() {
        Car car = new Car();
        car.setCarId("car-existing");
        car.setCarName("Existing Car");
        carRepository.create(car);

        Car updated = new Car();
        updated.setCarName("Ghost Car");
        Car result = carRepository.update("car-nonexistent", updated);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("car-del");
        car.setCarName("Car to Delete");
        carRepository.create(car);

        carRepository.delete("car-del");
        assertNull(carRepository.findById("car-del"));
    }
}
