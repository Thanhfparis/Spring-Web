package com.example.springweb.repository;

import com.example.springweb.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByVehicle_Id(Integer id);

}
