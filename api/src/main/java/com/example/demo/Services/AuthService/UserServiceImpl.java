package com.example.demo.Services.AuthService;

import com.example.demo.DTO.CourseAndSubjectDTO.CourseRequestDTO;
import com.example.demo.DTO.CourseAndSubjectDTO.CourseResponseDTO;
import com.example.demo.DTO.CourseAndSubjectDTO.QuizResponseDTO;
import com.example.demo.Entity.Subject;
import com.example.demo.Entity.User;
import com.example.demo.Repositories.CourseRepository;
import com.example.demo.Repositories.QuizRepository;
import com.example.demo.Repositories.SubjectRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public UserServiceImpl(CourseRepository courseRepository,
                           SubjectRepository subjectRepository,
                           UserRepository userRepository,
                           QuizRepository quizRepository
    )
    {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    @Transactional
    @Override
    public Map<String, Object> addUserCourse(CourseRequestDTO requestDTO, Long id)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not Found"));

//        course.setName(requestDTO.getCourse());
//        course.setUser(user);
//        courseRepository.save(course);
//        Course course = new Course();

        Map<String, Object> res = new HashMap<>();
        res.put("username", user.getUsername());
        res.put("course", user.getCourse());

        return res;
    }

    @Transactional
    @Override
    public List<QuizResponseDTO> getAllUserQuizzes(Long userId, Long subjectId)
    {
        List<QuizResponseDTO> quizzes = quizRepository.getAllUserQuizzes(subjectId);

        System.out.println(userId + ":" + subjectId);
        return quizzes.stream()
                .map(quiz -> new QuizResponseDTO(quiz.getId(), quiz.getTitle()))
                .collect(Collectors.toList());
    }

}
