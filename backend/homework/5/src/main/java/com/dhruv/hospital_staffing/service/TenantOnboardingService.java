package com.dhruv.hospital_staffing.service;

import com.dhruv.hospital_staffing.dto.onboarding.OnboardTenantRequestDto;
import com.dhruv.hospital_staffing.model.Shift;
import com.dhruv.hospital_staffing.model.ShiftType;
import com.dhruv.hospital_staffing.model.User;
import com.dhruv.hospital_staffing.repository.ShiftRepository;
import com.dhruv.hospital_staffing.repository.ShiftTypeRepository;
import com.dhruv.hospital_staffing.repository.ShiftUserRepository;
import com.dhruv.hospital_staffing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TenantOnboardingService {
    private final ShiftTypeRepository shiftTypeRepository;
    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftUserRepository shiftUserRepository;


    @Transactional
    public Map<String, Object> onboard(UUID tenantId, OnboardTenantRequestDto req) {
        // Shift types
        List<ShiftType> types = req.getShiftTypes().stream()
                .map(st -> ShiftType.builder()
                        .tenantId(tenantId)
                        .name(st.getName())
                        .description(st.getDescription())
                        .active(Boolean.TRUE.equals(st.getActive()))
                        .build())
                .toList();
        shiftTypeRepository.batchInsert(tenantId, types);

        Map<String, UUID> shiftTypeIdByName = new HashMap<>();
        for (var st : req.getShiftTypes()) {
            shiftTypeIdByName.put(st.getName(), shiftTypeRepository.findIdByName(tenantId, st.getName()));
        }

        // 2) Users
        List<User> users = req.getUsers().stream()
                .map(u -> User.builder()
                        .tenantId(tenantId)
                        .username(u.getUsername())
                        .timezone(u.getTimezone())
                        .loggedIn(Boolean.TRUE.equals(u.getLoggedIn()))
                        .build())
                .toList();

        userRepository.batchInsert(tenantId, users);

        Map<String, UUID> userIdByUsername = new HashMap<>();
        for (var u : req.getUsers()) {
            userIdByUsername.put(u.getUsername(), userRepository.findIdByUsername(tenantId, u.getUsername()));
        }

        // 3) Shifts
        List<Shift> shifts = req.getShifts().stream()
                .map(s -> Shift.builder()
                        .tenantId(tenantId)
                        .shiftTypeId(shiftTypeIdByName.get(s.getShiftTypeName()))
                        .dateStart(s.getDateStart())
                        .dateEnd(s.getDateEnd())
                        .timeStart(s.getTimeStart())
                        .timeEnd(s.getTimeEnd())
                        .build())
                .toList();

        shiftRepository.batchInsert(tenantId, shifts);

        // fetch created shift ids in list order (by natural key)
        List<UUID> shiftIdsInOrder = new ArrayList<>();
        for (var s : req.getShifts()) {
            UUID stId = shiftTypeIdByName.get(s.getShiftTypeName());
            shiftIdsInOrder.add(shiftRepository.findIdByNaturalKey(tenantId, stId, s.getDateStart(), s.getTimeStart()));
        }

        // 4) Assignments
        List<Object[]> rows = new ArrayList<>();
        for (var a : req.getAssignments()) {
            UUID userId = userIdByUsername.get(a.getUsername());
            UUID shiftId = shiftIdsInOrder.get(a.getShiftIndex());
            rows.add(new Object[]{shiftId, userId});
        }
        shiftUserRepository.batchInsert(tenantId, rows);

        // 5) Trigger failure at END to prove rollback
        if (req.isTriggerFailureAtEnd()) {
            // violates NOT NULL username
            userRepository.insert(tenantId, null, false, "UTC");
        }

        return Map.of(
                "tenantId", tenantId,
                "shiftTypesSaved", types.size(),
                "usersSaved", users.size(),
                "shiftsSaved", shifts.size(),
                "assignmentsSaved", rows.size()
        );
    }
}
