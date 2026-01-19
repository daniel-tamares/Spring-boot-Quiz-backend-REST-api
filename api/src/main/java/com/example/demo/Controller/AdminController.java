package com.example.demo.Controller;

import com.example.demo.DTO.CourseAndSubjectDTO.*;
import com.example.demo.Services.AuthService.AdminService;
import com.example.demo.Services.AuthService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(
            AdminService adminService
    )
    {
        this.adminService = adminService;
    }

    @PostMapping("/addCourse")
    public ResponseEntity<Map<String, Object>> addCourse(@Valid @RequestBody CourseRequestDTO requestDTO)
    {
        Map<String, Object> res = adminService.addCourse(requestDTO);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/addSubject/{id}")
    public ResponseEntity<Map<String, Object>> addCourse(@Valid @RequestBody SubjectRequestDTO requestDTO, @PathVariable("id") Long courseId)
    {
        Map<String, Object> res = adminService.addSubject(requestDTO, courseId);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/addSubjectQuiz/{userId}/{subjectId}")
    public ResponseEntity<Map<String, Object>> addSubjectQuiz(@Valid @RequestBody QuizRequestDTO requestDTO, @PathVariable("userId") Long userId, @PathVariable("subjectId") Long quizId)
    {
        Map<String, Object> res = adminService.addSubjectQuiz(requestDTO, userId, quizId);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/addQuizQuestion/{id}")
    public ResponseEntity<Map<String, Object>> addQuizQuestion(@Valid @RequestBody QuestionRequestDTO requestDTO, @PathVariable("id") Long quizId)
    {
        Map<String, Object> res = adminService.addQuizQuestion(requestDTO, quizId);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/addQuestionOption/{questionId}")
    public ResponseEntity<Map<String, Object>> addQuestionOption(@Valid @RequestBody OptionRequestDTO requestDTO, @PathVariable("questionId") Long questionId)
    {
        Map<String, Object> res = adminService.addQuestionOption(requestDTO, questionId);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/getQuizQuestions/{quizId}/{questionType}")
    public List<QuestionResponseDTO> getQuizQuestions(@PathVariable("quizId") Long quizId, @PathVariable("questionType") String questionType)
    {
        return adminService.getQuizQuestions(quizId, questionType);
    }

    @PostMapping("/submitAnswer/{quizId}")
    public ResponseEntity<QuizSubmitResponseDTO> checkAnswer(@Valid @RequestBody QuizSubmitDTO requestDTO,@PathVariable("quizId") Long quizId)
    {
        return ResponseEntity.ok(adminService.checkAnswer(requestDTO, quizId));
    }

    @GetMapping("/getSubject")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjects()
    {
        return ResponseEntity.ok(adminService.getSubjects());
    }

    @GetMapping("/getCourses")
    public ResponseEntity<List<CourseResponseDTO>> getCourses()
    {
        return ResponseEntity.ok(adminService.getCourses());
    }

    @GetMapping("/getAllQuizzes")
    public ResponseEntity<List<QuizResponseDTO>> getQuizzes()
    {
        return ResponseEntity.ok(adminService.getQuizzes());
    }

    @PostMapping("/deployQuiz/{userId}/{quizId}")
    public ResponseEntity<String> deployQuiz(@PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId, @Valid @RequestBody DeployQuizDTO requestDTO)
    {
        adminService.deployQuiz(userId, quizId, requestDTO);

        return ResponseEntity.ok("Successfully deploy...");
    }

}
