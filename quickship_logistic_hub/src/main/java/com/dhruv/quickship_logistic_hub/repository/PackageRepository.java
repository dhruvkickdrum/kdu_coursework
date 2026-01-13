package com.dhruv.quickship_logistic_hub.repository;

import com.dhruv.quickship_logistic_hub.model.Package;
import com.dhruv.quickship_logistic_hub.model.PackageStatus;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PackageRepository {
    private final Map<String, Package> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    // Here i create the executor thread pool which automatically create the thread based on the runtime available processors
    private final ExecutorService executor = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
    );

    // Method to get all packages in the form of list.
    public List<Package> getAllPackages() {
        return new ArrayList<>(store.values());
    }

    // method to store the packages with the PENDING status, and after the few second of processing update the status with SORTED.
    public Package saveBookAsync(Package pkg) {
        String id = UUID.randomUUID().toString();
        Package packge = new Package(id, pkg.getDestination(), pkg.getWeight(), pkg.getStatus(), pkg.getDeliveryType());

        store.put(id,packge);

        executor.submit(() -> {
            try {
                Thread.sleep(15000);
                store.computeIfPresent(id, (k, existing) -> {
                    existing.setStatus(PackageStatus.SORTED);
                    return existing;
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return packge;

    }

    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
