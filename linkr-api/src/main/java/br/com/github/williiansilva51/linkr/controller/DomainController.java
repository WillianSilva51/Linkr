package br.com.github.williiansilva51.linkr.controller;

import br.com.github.williiansilva51.linkr.dto.request.domain.DomainRequest;
import br.com.github.williiansilva51.linkr.dto.response.domain.DomainResponse;
import br.com.github.williiansilva51.linkr.service.DomainService;
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
@Validated
public class DomainController {
    private final DomainService domainService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DomainResponse> createDomain(@Valid @RequestBody DomainRequest domainRequest) {
        DomainResponse domain = domainService.createDomain(domainRequest);

        return ResponseEntity.ok(domain);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<DomainResponse>> getDomains() {
        List<DomainResponse> domains = domainService.getAllDomains();

        return ResponseEntity.ok(domains);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable Long id) {
        domainService.deleteDomainById(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DomainResponse> updateDomain(@PathVariable Long id, @Valid @RequestBody DomainRequest domainRequest) {
        DomainResponse domain = domainService.updateDomain(id, domainRequest);

        return ResponseEntity.ok(domain);
    }
}
