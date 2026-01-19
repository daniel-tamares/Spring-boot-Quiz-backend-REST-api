package com.example.demo.Controller;

import com.example.demo.DTO.CourseAndSubjectDTO.CourseRequestDTO;
import com.example.demo.DTO.CourseAndSubjectDTO.QuizResponseDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserStoreRequestDTO;
import com.example.demo.Services.AuthService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/addCourse/{id}")
    public ResponseEntity<Map<String, Object>> addCourse(@Valid @RequestBody CourseRequestDTO requestDTO, @PathVariable("id") Long id)
    {
        Map<String, Object> res = userService.addUserCourse(requestDTO, id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/availableQuizzes/{userId}/{subjectId}")
    public ResponseEntity<List<QuizResponseDTO>> getAllUserQuizzes(@PathVariable("userId") Long userId, @PathVariable("subjectId") Long subjectId)
    {
        return ResponseEntity.ok(userService.getAllUserQuizzes(userId, subjectId));
    }

}
