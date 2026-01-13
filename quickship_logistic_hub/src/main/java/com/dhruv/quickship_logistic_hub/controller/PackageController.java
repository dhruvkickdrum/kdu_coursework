package com.dhruv.quickship_logistic_hub.controller;


import com.dhruv.quickship_logistic_hub.dto.PackageRequestDto;
import com.dhruv.quickship_logistic_hub.model.Package;
import com.dhruv.quickship_logistic_hub.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final PackageService service;


    public PackageController(PackageService service) {
        this.service = service;
    }

    // Controller to add the package which have restriction, only manager can add the packages
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addPackage(@Valid @RequestBody PackageRequestDto req) {
        Package saved = service.add(new Package(req.getDestination(), req.getWeight(), req.getStatus(), req.getDeliveryType()));
        // This return statemet return the 200 Status code which is not suitable in this case, because we have created a process it is not completed yet.
//        return ResponseEntity.ok(new Package(saved.getId(),saved.getDestination(), saved.getWeight(), saved.getStatus(), saved.getDeliveryType()));
        // So here we can return the status CREATED , which indicate that process has been created not completed.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // Controller to get all the packages which can only access by the manager and the driver
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER','DRIVER')")
    public ResponseEntity<List<Package>> getAllPackages() {
        return ResponseEntity.ok(service.getAllPackages());
    }
}
