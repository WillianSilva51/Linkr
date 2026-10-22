package br.com.github.williiansilva51.linkr.controller;

import br.com.github.williiansilva51.linkr.dto.request.domain.DomainRequest;
import br.com.github.williiansilva51.linkr.dto.response.domain.DomainResponse;
import br.com.github.williiansilva51.linkr.service.DomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/domain")
@RequiredArgsConstructor
@Tag(name = "Domain", description = "Endpoints for managing domains")
@Validated
public class DomainController {
    private final DomainService domainService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new domain", description = "Creates a new domain with the provided information.")
    public ResponseEntity<DomainResponse> createDomain(@Valid @RequestBody DomainRequest domainRequest) {
        DomainResponse domain = domainService.createDomain(domainRequest);

        return ResponseEntity.ok(domain);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Get all domains", description = "Retrieves a list of all domains.")
    public ResponseEntity<List<DomainResponse>> getDomains() {
        List<DomainResponse> domains = domainService.getAllDomains();

        return ResponseEntity.ok(domains);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a domain", description = "Deletes a domain by its ID.")
    public ResponseEntity<Void> deleteDomain(@PathVariable Long id) {
        domainService.deleteDomainById(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a domain", description = "Updates a domain with the provided information.")
    public ResponseEntity<DomainResponse> updateDomain(@PathVariable Long id, @Valid @RequestBody DomainRequest domainRequest) {
        DomainResponse domain = domainService.updateDomain(id, domainRequest);

        return ResponseEntity.ok(domain);
    }
}
