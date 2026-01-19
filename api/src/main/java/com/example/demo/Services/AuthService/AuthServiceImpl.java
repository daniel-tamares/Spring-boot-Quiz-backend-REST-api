package com.example.demo.Services.AuthService;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.RefreshTokenRequestDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserStoreRequestDTO;
import com.example.demo.Entity.RefreshToken;
import com.example.demo.Entity.User;
import com.example.demo.Repositories.RefreshTokenRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.Security.JwtTokenProvider;
import com.example.demo.Security.PasswordEncoderService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepo;
    private final RefreshTokenRepository tokenRepo;
    private final PasswordEncoderService passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl( UserRepository userRepo, RefreshTokenRepository tokenRepo, PasswordEncoderService passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager)
    {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public UserResponseDTO register( UserStoreRequestDTO requestDTO, String ip )
    {
        if ( userRepo.existsByUsername(requestDTO.getUsername()) )
        {
            throw new RuntimeException("Already exist...dan");
        }

        User user = new User(
                requestDTO.getUsername(),
                passwordEncoder.encode(requestDTO.getPassword()),
                requestDTO.getYear(),
                "ip"
        );

        userRepo.save(user);

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId( user.getId() );
        dto.setUsername( user.getUsername() );
        dto.setPassword( user.getPassword() );
        dto.setYear( user.getYear() );
        dto.setRoles(user.getRoles());

        return dto;
    }

    @Transactional
    @Override
    public UserResponseDTO login(LoginRequestDTO requestDTO, String ip, HttpServletResponse response)
    {
        User user = userRepo.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new  RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Wrong password");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication authenticated = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        String jti = jwtTokenProvider.generateJti();
        String familyId = jwtTokenProvider.generateFamilyId();

        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails, jti, familyId);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(10080))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        saveRefreshToken(user, refreshToken, ip, familyId);

        return createAuthResponse(accessToken, refreshToken, user, jti, familyId);
    }

    @Transactional(noRollbackFor = RuntimeException.class)
    @Override
    public UserResponseDTO refreshToken(String refreshToken, String ip, HttpServletResponse response)
    {
        String familyId = jwtTokenProvider.extractFamilyId(refreshToken);
        String refreshTokenStatus = jwtTokenProvider.validateRefreshTokenStatus(refreshToken);

        if ("expired".equals(refreshTokenStatus))
        {
            tokenRepo.deleteByFamilyId(familyId);
            tokenRepo.flush();
            throw new RuntimeException("expire token....");
        }
        if ("tampered".equals(refreshTokenStatus))
        {
            tokenRepo.deleteByFamilyId(familyId);
            tokenRepo.flush();
            throw new RuntimeException("Untrusted token");
        }
        String username = jwtTokenProvider.extractUsername(refreshToken);
        String newJti = jwtTokenProvider.generateJti();

        User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found.."));

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(10080))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        saveRefreshToken(user, refreshToken, ip, familyId);

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId( 1L );
        dto.setUsername( username );
        dto.setPassword( user.getPassword() );
        dto.setYear(1);

        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteToken(String rt)
    {
        tokenRepo.deleteByFamilyId(rt);
    }

    @Transactional(noRollbackFor = RuntimeException.class)
    @Override
    public String logout(String refreshToken, HttpServletResponse response)
    {
        if (refreshToken == null)
        {
            throw new RuntimeException("No cookie..");
        }
        String familyId = jwtTokenProvider.extractFamilyId(refreshToken);
        tokenRepo.deleteByFamilyId(familyId);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(false)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return "Successfully logout";
    }

    private static UserResponseDTO createAuthResponse(String accessToken, String refreshToken, User user, String jti, String familyId)
    {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId( user.getId() );
        dto.setUsername( jti );
        dto.setPassword(familyId);
        dto.setYear( user.getYear() );
        dto.setRoles(user.getRoles());
        dto.setAccessToken(accessToken);
        dto.setRefreshToken(refreshToken);

        return dto;
    }

    @Transactional
    @Override
    public void saveRefreshToken(User user, String refreshToken, String ipAddress, String familyId)
    {
        if (tokenRepo.existsByToken(refreshToken))
        {
            throw new RuntimeException("Refrsh token already save..");
        }

        Instant expiryInstant = jwtTokenProvider.extractRefreshTokenExpiration(refreshToken);
        LocalDateTime expiry = LocalDateTime.ofInstant(expiryInstant, ZoneId.systemDefault());

        String newJti = jwtTokenProvider.generateJti();

        RefreshToken saved = new RefreshToken();
        saved.setToken(newJti);
        saved.setFamilyId(familyId);
        saved.setUser(user);
        saved.setExpiresAt(expiry);
        saved.setIpAddress(ipAddress);
        tokenRepo.save(saved);
    }

    @Override
    public String getClientIp(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

}
