package com.dhruv.hospital_staffing.controller;

import com.dhruv.hospital_staffing.dto.shift.CreateShiftRequestDto;
import com.dhruv.hospital_staffing.model.Shift;
import com.dhruv.hospital_staffing.repository.ShiftRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tenants/{tenantId}/shifts")
public class ShiftController {

    private final ShiftRepository shiftRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createShift(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CreateShiftRequestDto req
    ) {
        Shift s = Shift.builder()
                .tenantId(tenantId)
                .shiftTypeId(req.getShiftTypeId())
                .dateStart(req.getDateStart())
                .dateEnd(req.getDateEnd())
                .timeStart(req.getTimeStart())
                .timeEnd(req.getTimeEnd())
                .build();

        UUID id = shiftRepository.insert(s);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    // Exercise 1 - Task 2: Fetch shifts for tenant
    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable UUID tenantId) {
        return ResponseEntity.ok(shiftRepository.findAll(tenantId));
    }
}
