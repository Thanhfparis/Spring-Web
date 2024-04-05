package com.example.springweb.controller;

import com.example.springweb.model.entities.User;
import com.example.springweb.model.entities.Vehicle;
import com.example.springweb.model.dto.VehicleDto;
import com.example.springweb.model.entities.VehicleType;
import com.example.springweb.repository.ReservationRepository;
import com.example.springweb.repository.UserRepository;
import com.example.springweb.repository.VehicleRepository;
import com.example.springweb.repository.VehicleTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SalesController {

    private final UserRepository userRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;
    private int id = 0;

    public SalesController(UserRepository userRepository, VehicleTypeRepository vehicleTypeRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/sales/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        this.id = id;
        return "sales/index";
    }

    @GetMapping("/sales/vehicle")
    public String vehicles(Model model) {
        model.addAttribute("vehicles", vehicleRepository.findAllBySales_Id(this.id));
        return "sales/list-vehicle";
    }

    @GetMapping("/sales/reservation")
    public String reservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAllByVehicle_Sales_Id(this.id));
        return "sales/list-reservation";
    }

    @GetMapping("/sales/vehicle/form-create-vehicle")
    public String getFormCreateVehicle(Model model){
        VehicleDto vehicleDto = new VehicleDto();
        model.addAttribute("vehicle",vehicleDto);
        model.addAttribute("types", vehicleTypeRepository.findAll());
        return "sales/vehicle-create";
    }

    @PostMapping("/sales/vehicle/save")
    public String saveVehicle(@ModelAttribute(name = "vehicle") VehicleDto vehicleDto){
        Vehicle vehicle = new Vehicle();
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDto.getVehicleTypeId()).get();
        User user = userRepository.findById(this.id).get();
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setCylinder(vehicleDto.getCylinder());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setWeight(vehicleDto.getWeight());
        vehicle.setName(vehicleDto.getName());
        vehicle.setDoorNumbers(vehicleDto.getDoorNumbers());
        vehicle.setVehicleType(vehicleType);
        vehicle.setSales(user);
        vehicleRepository.save(vehicle);
        return "redirect:/sales/vehicle";
    }

    @GetMapping("/sales/vehicle/form-update-vehicle/{id}")
    public String getFormUpdateVehicle(@PathVariable("id") int id, Model model){
        Vehicle vehicle = vehicleRepository.findById(id).get();
        model.addAttribute("types", vehicleTypeRepository.findAll());
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setColor(vehicle.getColor());
        vehicleDto.setCylinder(vehicle.getCylinder());
        vehicleDto.setPrice(vehicle.getPrice());
        vehicleDto.setWeight(vehicle.getWeight());
        vehicleDto.setName(vehicle.getName());
        vehicleDto.setDoorNumbers(vehicle.getDoorNumbers());
        vehicleDto.setVehicleTypeId(vehicle.getVehicleType().getId());
        vehicleDto.setVehicleTypeName(vehicle.getVehicleType().getName());
        vehicleDto.setUserId(vehicle.getSales().getId());
        vehicleDto.setFullName(vehicle.getSales().getFullName());
        model.addAttribute("vehicle",vehicleDto);
        return "sales/vehicle-update";
    }

    @PostMapping("/sales/vehicle/update")
    public String update(@ModelAttribute(name = "vehicle") VehicleDto vehicleDto){
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId()).get();
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDto.getVehicleTypeId()).get();
        User user = userRepository.findById(this.id).get();
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setCylinder(vehicleDto.getCylinder());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setWeight(vehicleDto.getWeight());
        vehicle.setName(vehicleDto.getName());
        vehicle.setDoorNumbers(vehicleDto.getDoorNumbers());
        vehicle.setVehicleType(vehicleType);
        vehicle.setSales(user);
        vehicleRepository.save(vehicle);
        return "redirect:/sales/vehicle";
    }

    @GetMapping("/sales/vehicle/delete/{id}")
    public String delete(@PathVariable("id") int id){
        vehicleRepository.deleteById(id);
        return "redirect:/sales/vehicle";
    }
}
