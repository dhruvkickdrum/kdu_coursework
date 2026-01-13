package com.dhruv.hospital_staffing.controller;

import com.dhruv.hospital_staffing.dto.common.PageResponseDto;
import com.dhruv.hospital_staffing.dto.user.CreateUserRequestDto;
import com.dhruv.hospital_staffing.dto.user.UpdateUserRequestDto;
import com.dhruv.hospital_staffing.exception.NotFoundException;
import com.dhruv.hospital_staffing.model.User;
import com.dhruv.hospital_staffing.repository.UserRepository;
import com.dhruv.hospital_staffing.service.UserQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tenant/{tenantId}/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    // Exercise 1 - Task 1: Native Insert
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CreateUserRequestDto req
            ) {
        UUID id = userRepository.insert(tenantId, req.getUsername(), req.getLoggedIn(), req.getTimezone());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    // Exercise 1 - Task 2: Fetch all users
    // Exercise 3: pagination and sorting
    @GetMapping
    public ResponseEntity<?> getUsers(
            @PathVariable UUID tenantId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "username") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        if(page != null || size != null) {
            int p = page == null ? 0 : page;
            int s = size == null ? 10 : size;
            PageResponseDto<User> resp = userQueryService.getUsers(tenantId, p, s, sort, dir);
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.ok(userRepository.findAll(tenantId));
    }


    // Exercise 1 - Task3: Native update
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable UUID tenantId,
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequestDto req
            ) {
        int updated = userRepository.update(tenantId, userId, req.getUsername(), req.getLoggedIn(), req.getTimezone());
        if(updated == 0) throw new NotFoundException("User not found for tenant");
        return ResponseEntity.ok(Map.of("updated", updated));
    }
}
