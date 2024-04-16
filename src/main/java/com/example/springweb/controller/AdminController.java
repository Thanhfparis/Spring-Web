package com.example.springweb.controller;

import com.example.springweb.model.entities.Admin;
import com.example.springweb.repository.AdminRepository;
import com.example.springweb.repository.ReservationRepository;
import com.example.springweb.repository.UserRepository;
import com.example.springweb.repository.VehicleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;
    private int userId = 0;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, ReservationRepository reservationRepository, VehicleRepository vehicleRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
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
        model.addAttribute("twoWheels", vehicleRepository.countTotalPriceOf2Wheels());
        model.addAttribute("fourWheels", vehicleRepository.countTotalPriceOf4Wheels());
        model.addAttribute("total", vehicleRepository.countTotalPriceOf2Wheels() + vehicleRepository.countTotalPriceOf4Wheels());
        return "admin/index";
    }

}
