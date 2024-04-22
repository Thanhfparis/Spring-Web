package com.example.springweb.controller;

import com.example.springweb.model.dto.AgencyDto;
import com.example.springweb.model.entities.Admin;
import com.example.springweb.model.entities.Agency;
import com.example.springweb.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;
    private final AgencyRepository agencyRepository;
    private int userId = 0;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, ReservationRepository reservationRepository, VehicleRepository vehicleRepository, AgencyRepository agencyRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
        this.agencyRepository = agencyRepository;
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin-signin";
    }

    @PostMapping("/admin/login/try")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {
        Admin admin = adminRepository.findByEmailAndPassword(email, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            this.userId = admin.getId();
            return "redirect:/admin";
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("countSale", userRepository.countAllByRole("SALES"));
        model.addAttribute("countCar", vehicleRepository.countAllByVehicleType_Id(1));
        model.addAttribute("countTruck", vehicleRepository.countAllByVehicleType_Id(3));
        model.addAttribute("countMotorcycle", vehicleRepository.countAllByVehicleType_Id(2));
        model.addAttribute("countClient", userRepository.countAllByRole("CLIENTS"));
        model.addAttribute("countReservation", reservationRepository.count());
        model.addAttribute("twoWheels", reservationRepository.countTotalPriceOf2Wheels());
        model.addAttribute("fourWheels", reservationRepository.countTotalPriceOf4Wheels());
        model.addAttribute("total", reservationRepository.countTotalPriceOf2Wheels() + reservationRepository.countTotalPriceOf4Wheels());

        Integer car = vehicleRepository.countAllByVehicleType_Id(1);
        Integer truck = vehicleRepository.countAllByVehicleType_Id(3);
        Integer twoWheels = vehicleRepository.countAllByVehicleType_Id(2) + vehicleRepository.countAllByVehicleType_Id(4);
        Integer sum = car + truck + twoWheels;


        DecimalFormat df = new DecimalFormat("#.##");
        double carPercent = Double.parseDouble(df.format((double) car / sum * 100));
        double truckPercent = Double.parseDouble(df.format((double) truck / sum * 100));
        double twoWheelPercent = Double.parseDouble(df.format((double) twoWheels / sum * 100));

        model.addAttribute("carPercent", carPercent);
        model.addAttribute("truckPercent", truckPercent);
        model.addAttribute("twoWheelPercent", twoWheelPercent);

        Long count = vehicleRepository.count();
        List<AgencyDto> agencyDtos = new ArrayList<>();
        List<Agency> agencies = agencyRepository.findAll();
        for(Agency agency : agencies) {
            Integer countVehicle = vehicleRepository.countByAgencyId(agency.getId());
            double percent = Double.parseDouble(df.format((double) countVehicle / count * 100));
            AgencyDto agencyDto = new AgencyDto();
            agencyDto.setId(agency.getId());
            agencyDto.setAgencyName(agency.getAgencyName());
            agencyDto.setPercent(percent);
            agencyDtos.add(agencyDto);
        }

        model.addAttribute("agencies", agencyDtos);
        return "admin/index";
    }

}
