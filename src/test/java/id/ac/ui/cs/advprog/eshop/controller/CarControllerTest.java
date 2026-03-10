package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private Car car;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(viewResolver)
                .build();
        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Red");
        car.setCarQuantity(2);
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        when(carService.create(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Toyota Avanza")
                        .param("carColor", "Red")
                        .param("carQuantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> cars = Arrays.asList(car);
        when(carService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        when(carService.findById("car-1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/car-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));

        verify(carService, times(1)).findById("car-1");
    }

    @Test
    void testEditCarPost() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .param("carId", "car-1")
                        .param("carName", "Updated Car")
                        .param("carColor", "Blue")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).update(eq("car-1"), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "car-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).deleteCarById("car-1");
    }
}
