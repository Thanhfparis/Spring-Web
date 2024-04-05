package com.example.springweb.controller;

import com.example.springweb.model.dto.CommentDto;
import com.example.springweb.model.dto.ReservationDto;
import com.example.springweb.model.entities.Comment;
import com.example.springweb.model.entities.Reservation;
import com.example.springweb.model.entities.User;
import com.example.springweb.model.entities.Vehicle;
import com.example.springweb.repository.CommentRepository;
import com.example.springweb.repository.ReservationRepository;
import com.example.springweb.repository.UserRepository;
import com.example.springweb.repository.VehicleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final CommentRepository commentRepository;
    private final ReservationRepository reservationRepository;
    private int userId = 0;
    private int vehicleId = 0;

    public UserController(UserRepository userRepository, VehicleRepository vehicleRepository, CommentRepository commentRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.commentRepository = commentRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/user")
    public String indexUser(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "signin";
    }

    @PostMapping("/login/try")
    public String login(
            @RequestParam String email,
            @RequestParam String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            this.userId = user.getId();
            if (Objects.equals(user.getRole(), "CLIENT")) {
                this.userId = user.getId();
                return "redirect:/user";
            } else if (Objects.equals(user.getRole(), "SALES")) {
                return "redirect:/sales/" + user.getId();
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user";
    }

    @GetMapping("/user/reservation")
    public String reservation(Model model) {
        model.addAttribute("reservations", reservationRepository.findAllByUser_Id(userId));
        return "user/reservations";
    }

    @GetMapping("/user/reservation/form-create-reservation")
    public String getFormCreateReservation(Model model) {
        ReservationDto reservationDto = new ReservationDto();
        model.addAttribute("reservation", reservationDto);
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "user/insertReservation";
    }

    @PostMapping("/user/reservation/save")
    public String save(@ModelAttribute(name = "reservation") ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        User user = userRepository.findById(userId).get();
        Vehicle vehicle = vehicleRepository.findById(reservationDto.getVehicleId()).get();

        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setReturnDate(reservationDto.getReturnDate());
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservation.setTotalPrice(reservationDto.getTotalPrice());
        reservationRepository.save(reservation);
        return "redirect:/user/reservations";
    }

    @GetMapping("/user/reservation/delete/{id}")
    public String deleteReservation(@PathVariable("id") int id) {
        reservationRepository.deleteById(id);
        return "redirect:/user/reservations";
    }

    @GetMapping("/user/comment")
    public String indexComment(Model model) {
        model.addAttribute("comments", commentRepository.findAll());
        return "user/comments";
    }

    @GetMapping("/user/comment/form-create-comment")
    public String getFormCreateComment(Model model) {
        CommentDto comment = new CommentDto();
        model.addAttribute("comment", comment);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "user/insertComment";
    }

    @PostMapping("/user/comment/save")
    public String saveComment(@ModelAttribute(name = "comment") CommentDto commentDto) {
        Comment comment = new Comment();
        User user = userRepository.findById(commentDto.getUserId()).get();
        Vehicle vehicle = vehicleRepository.findById(commentDto.getVehicleId()).get();

        comment.setCommentDate(LocalDate.now());
        comment.setUser(user);
        comment.setVehicle(vehicle);
        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);
        return "redirect:/user/comments";
    }

    @GetMapping("/user/comment/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        commentRepository.deleteById(id);
        return "redirect:/user/comments";
    }
}
