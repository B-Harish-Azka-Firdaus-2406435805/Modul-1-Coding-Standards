package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepositoryInterface carRepository;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Red");
        car.setCarQuantity(2);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);
        Car result = carService.create(car);
        assertEquals(car.getCarId(), result.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = Arrays.asList(car);
        Iterator<Car> iterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> results = carService.findAll();
        assertEquals(1, results.size());
        assertEquals(car.getCarId(), results.get(0).getCarId());
    }

    @Test
    void testFindById() {
        when(carRepository.findById("car-1")).thenReturn(car);
        Car result = carService.findById("car-1");
        assertEquals(car.getCarId(), result.getCarId());
    }

    @Test
    void testUpdate() {
        carService.update("car-1", car);
        verify(carRepository, times(1)).update("car-1", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("car-1");
        verify(carRepository, times(1)).delete("car-1");
    }
}
