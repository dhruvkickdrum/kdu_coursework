package com.dhruv.hospital_staffing.controller;

import com.dhruv.hospital_staffing.dto.shifttype.CreateShiftTypeRequestDto;
import com.dhruv.hospital_staffing.repository.ShiftTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tenants/{tenantId}/shift-types")
public class ShiftTypeController {

    private final ShiftTypeRepository shiftTypeRepository;

    // Exercise 1 - Task 1: Native Insert
    @PostMapping
    public ResponseEntity<Map<String, Object>> createShiftType(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CreateShiftTypeRequestDto req
    ) {
        UUID id = shiftTypeRepository.insert(tenantId, req.getName(), req.getDescription(), req.getActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable UUID tenantId) {
        return ResponseEntity.ok(shiftTypeRepository.findAll(tenantId));
    }
}

