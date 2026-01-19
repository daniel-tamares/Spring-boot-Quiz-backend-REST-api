package com.example.demo.Services.AuthService;

import com.example.demo.DTO.CourseAndSubjectDTO.*;
import com.example.demo.Entity.*;
import com.example.demo.Repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final OptionRepository optionRepository;

    private final Map<String, String> taskStatus = new ConcurrentHashMap<>();

    public AdminServiceImpl(
            UserRepository userRepository,
            SubjectRepository subjectRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            CourseRepository courseRepository,
            OptionRepository optionRepository
    )
    {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    @Override
    public Map<String, Object> addCourse(CourseRequestDTO requestDTO)
    {
        if (courseRepository.existsByName(requestDTO.getCourse()))
        {
            throw new RuntimeException("Course is already exist");
        }

        Course savedCourse = new Course();
        savedCourse.setName(requestDTO.getCourse());
        courseRepository.save(savedCourse);

        Map<String, Object> res = new HashMap<>();
        res.put("id", savedCourse.getId());
        res.put("course", savedCourse.getName());

        return res;
    }

    @Transactional
    @Override
    public Map<String, Object> addSubject(SubjectRequestDTO requestDTO, Long courseId)
    {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (subjectRepository.existsByName(requestDTO.getSubject()))
        {
            throw new RuntimeException("Subject is alreadyy exists..");
        }

        Subject subject = new Subject();
        subject.setName(requestDTO.getSubject());
        subject.setCourse(course);
        subjectRepository.save(subject);

        Map<String, Object> res = new HashMap<>();
        res.put("id", subject.getId());
        res.put("name", subject.getName());
        res.put("course", subject.getCourse().getId());

        return res;
    }

    @Transactional
    @Override
    public Map<String, Object> addSubjectQuiz(QuizRequestDTO requestDTO, Long userId, Long subjectId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User nt found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (quizRepository.existsByTitle(requestDTO.getTitle()))
        {
            throw new RuntimeException("Quiz tile is already exists...");
        }

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(requestDTO.getTitle());
        newQuiz.setQuizType("Multiple Choice");
        newQuiz.setIsActive(false);
        newQuiz.setYear(1);
        newQuiz.setCourseId(1L);
        newQuiz.setSubject(subject);
        newQuiz.setUser(user);
        newQuiz.setQuizKey(UUID.randomUUID().toString() + "-" + LocalDateTime.now());
        Quiz saved = quizRepository.save(newQuiz);

        Map<String, Object> res = new HashMap<>();
        res.put("id", saved.getId());
        res.put("title", saved.getTitle());
        res.put("subjectId", saved.getSubject().getId());
        res.put("userId", saved.getUser().getId());

        return res;
    }

    @Transactional
    @Override
    public Map<String, Object> addQuizQuestion(QuestionRequestDTO requestDTO, Long quizId)
    {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not Found"));

        if (questionRepository.existsByTitle(requestDTO.getQuestion()))
        {
            throw new RuntimeException("Question is already exists...");
        }

        Question question = new Question();
        question.setTitle(requestDTO.getQuestion());
        question.setQuiz(quiz);
        question.setType("MultipleChoice");
        Question saved = questionRepository.save(question);

        quiz.getQuestions().add(question);

        Map<String, Object> res = new HashMap<>();
        res.put("id", saved.getId());
        res.put("question", saved.getTitle());
        res.put("quizId", saved.getQuiz().getId());

        return res;
    }

    @Transactional
    @Override
    public Map<String, Object> addQuestionOption(OptionRequestDTO requestDTO, Long questionId)
    {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("question is not found"));

        if (optionRepository.existsByOptionText(requestDTO.getOption()))
        {
            throw new RuntimeException("Option is already exist...");
        }

        boolean isCorrect = false;
        if ("true".equals(requestDTO.getIsCorrect()))
        {
            isCorrect = true;
        }

        Option newOption = new Option();
        newOption.setCorrect(isCorrect);
        newOption.setOptionText(requestDTO.getOption());
        newOption.setQuestion(question);
        Option saved = optionRepository.save(newOption);

        Map<String, Object> res = new HashMap<>();
        res.put("id", saved.getId());
        res.put("isCorrect", saved.getIsCorrect());
        res.put("optionText", saved.getOptionText());
        res.put("questionId", saved.getQuestion().getId());

        return res;
    }

    @Transactional
    @Override
    public List<QuestionResponseDTO> getQuizQuestions(Long quizId, String questionType)
    {
        // List<Question> questions = questionRepository.getQuizQuestions(quizId); getQuizQuestionsType

        List<Question> questions = questionRepository.getQuizQuestionsType(quizId, questionType);

        List<QuestionResponseDTO> response = new ArrayList<>();

        for (Question q : questions) {
            List<OptionResponseDTO> options = new ArrayList<>();

            for (Option o : q.getOptions()) {
                OptionResponseDTO optionDTO = new OptionResponseDTO(
                        o.getId(),
                        o.getOptionText(),
                        o.getIsCorrect()
                );
                options.add(optionDTO);
            }
            QuestionResponseDTO questionDTO = new QuestionResponseDTO(
                    q.getId(),
                    q.getTitle(),
                    options
            );

            response.add(questionDTO);
        }

        return response;

//        return questions.stream().map(q -> {
//            List<OptionResponseDTO> options = q.getOptions().stream()
//                    .map(o -> new OptionResponseDTO(o.getId(), o.getOptionText(), o.getIsCorrect()))
//                    .collect(Collectors.toList());
//
//            return new QuestionResponseDTO(q.getId(), q.getTitle(), options);
//        }).collect(Collectors.toList());
    }

//    @Transactional
//    @Override
//    public QuizSubmitResponseDTO checkAnswer(QuizSubmitDTO requestDTO, Long quizId)
//    {
//        Quiz quiz = quizRepository.findById(quizId)
//                .orElseThrow(() -> new RuntimeException("Quiz not found.."));
//
//        List<QuizAnswerResultDTO> result = new ArrayList<>();
//        int totalCorrectCount = 0;
//
//        for (QuizAnswerRequestDTO answer : requestDTO.getAnswers())
//        {
//            Option option = optionRepository.findByIdAndQuestionId(answer.getOptionId(), answer.getQuestionId())
//                    .orElse(null);
//
//            boolean isCorrect = false;
//            String questionTitle = "Unkown question..";
//            String optionText = "Unkown option...";
//
//            if (option != null)
//            {
//                isCorrect = option.getIsCorrect();
//                questionTitle = option.getQuestion().getTitle();
//                optionText = option.getOptionText();
//
//                if (isCorrect)
//                {
//                    totalCorrectCount++;
//                }
//            }
//
//            result.add(new QuizAnswerResultDTO(questionTitle, optionText, isCorrect));
//        }
//
//        return new QuizSubmitResponseDTO(result.size(), totalCorrectCount, result);
//
//    }

    @Transactional
    @Override
    public QuizSubmitResponseDTO checkAnswer(QuizSubmitDTO requestDTO, Long quizId)
    {
        quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found.."));

        List<Long> questionIds = requestDTO.getAnswers()
                .stream()
                .map(QuizAnswerRequestDTO::getQuestionId)
                .toList();
        List<Long> optionIds = requestDTO.getAnswers()
                .stream()
                .map(QuizAnswerRequestDTO::getOptionId)
                .toList();

        List<Question> questions = questionRepository.findByQuestionByIds(questionIds);
        Map<Long, String> questionTextMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, Question::getTitle));

        List<Option> options = optionRepository.findByOptionIds(optionIds);
        Map<Long, String> optionTextMap = options.stream()
                .collect(Collectors.toMap(Option::getId, Option::getOptionText));

        List<Option> correctOptions = optionRepository.findCorrectOptionsByQuestionIds(questionIds);

        Map<Long, Long> correctMap = correctOptions.stream()
                .collect(Collectors.toMap(o -> o.getQuestion().getId(), Option::getId));

        int score = 0;
        List<QuizAnswerResultDTO> result = new ArrayList<>();

        for (QuizAnswerRequestDTO a : requestDTO.getAnswers())
        {
            String question = questionTextMap.get(a.getQuestionId());
            String option = optionTextMap.get(a.getOptionId());

            Long correctOptionId = correctMap.get(a.getQuestionId());
            boolean isCorrect = correctOptionId != null && correctOptionId.equals(a.getOptionId());

            if (isCorrect) score++;

            result.add(new QuizAnswerResultDTO(
                    question,
                    option,
                    isCorrect
            ));
        }

        return new QuizSubmitResponseDTO(result.size(), score, result);

    }

    @Transactional
    public List<SubjectResponseDTO> getSubjects()
    {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectResponseDTO> response = new ArrayList<>();

        for (Subject subject : subjects)
        {
            SubjectResponseDTO res = new SubjectResponseDTO();
            res.setId(subject.getId());
            res.setCourseId(subject.getCourse().getId());
            res.setName(subject.getName());
            response.add(res);
        }

        return response;
    }

    @Transactional
    public List<CourseResponseDTO> getCourses()
    {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(course -> new CourseResponseDTO(course.getId(), course.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<QuizResponseDTO> getQuizzes()
    {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream()
                .map(quiz -> new QuizResponseDTO(quiz.getId(), quiz.getTitle()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deployQuiz(Long useId, Long quizId, DeployQuizDTO requestDtO)
    {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("No quiz found.."));

        System.out.println(requestDtO.getYear());

        quizRepository.deployQuiz(quizId,requestDtO.getYear());
    }

}
