package com.dhruv.hospital_staffing.service;

import com.dhruv.hospital_staffing.dto.common.PageResponseDto;
import com.dhruv.hospital_staffing.model.User;
import com.dhruv.hospital_staffing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public PageResponseDto<User> getUsers(UUID tenantId, int page, int size, String sort, String dir) {
        String sortField = (sort == null || sort.isBlank()) ? "username" : sort;
        if (!"username".equalsIgnoreCase(sortField)) {
            throw new IllegalArgumentException("Only sorting by 'username' is supported.");
        }

        String sortDir = "ASC";
        if (dir != null && dir.equalsIgnoreCase("desc")) sortDir = "DESC";
        if (dir != null && !(dir.equalsIgnoreCase("asc") || dir.equalsIgnoreCase("desc"))) {
            throw new IllegalArgumentException("dir must be 'asc' or 'desc'.");
        }

        int safeSize = Math.min(Math.max(size, 1), 100);
        int safePage = Math.max(page, 0);
        int offset = safePage * safeSize;

        long total = userRepository.count(tenantId);
        List<User> items = userRepository.findPage(tenantId, safeSize, offset, sortDir);

        return PageResponseDto.<User>builder()
                .page(safePage)
                .size(safeSize)
                .total(total)
                .items(items)
                .build();
    }
}

