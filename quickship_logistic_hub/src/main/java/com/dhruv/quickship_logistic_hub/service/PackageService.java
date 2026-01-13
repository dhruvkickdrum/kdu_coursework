package com.dhruv.quickship_logistic_hub.service;

import com.dhruv.quickship_logistic_hub.model.Package;
import com.dhruv.quickship_logistic_hub.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final PackageRepository repo;


    public PackageService(PackageRepository repo) {
        this.repo = repo;
    }

    public Package add(Package pkg) {
        return repo.saveBookAsync(pkg);
    }

    public List<Package> getAllPackages() {
        return repo.getAllPackages();
    }

}
