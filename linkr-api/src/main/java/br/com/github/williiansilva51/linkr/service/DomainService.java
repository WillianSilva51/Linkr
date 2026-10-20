package br.com.github.williiansilva51.linkr.service;

import br.com.github.williiansilva51.linkr.database.model.DomainEntity;
import br.com.github.williiansilva51.linkr.database.repository.DomainRepository;
import br.com.github.williiansilva51.linkr.dto.request.domain.DomainRequest;
import br.com.github.williiansilva51.linkr.dto.response.domain.DomainResponse;
import br.com.github.williiansilva51.linkr.handler.exceptions.domain.DomainAlreadyExistsException;
import br.com.github.williiansilva51.linkr.handler.exceptions.domain.DomainNotFoundException;
import br.com.github.williiansilva51.linkr.handler.exceptions.user.AuthenticationRequiredException;
import br.com.github.williiansilva51.linkr.handler.exceptions.user.ForbiddenOperationException;
import br.com.github.williiansilva51.linkr.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DomainService {
    private final DomainRepository domainRepository;
    private final CurrentUserProvider currentUserProvider;

    public DomainResponse createDomain(DomainRequest domain) {
        validateAdminPrivileges("Only admin users can create domains");

        if (domainRepository.existsByDomainUrl(domain.domainUrl())) {
            throw new DomainAlreadyExistsException("Domain already exists with url: " + domain.domainUrl());
        }

        DomainEntity domainEntity = DomainEntity.builder()
                .domainUrl(domain.domainUrl())
                .build();

        DomainEntity savedDomain = domainRepository.save(domainEntity);

        return new DomainResponse(savedDomain.getId(), savedDomain.getDomainUrl());
    }

    public List<DomainResponse> getAllDomains() {
        if (!currentUserProvider.isAuthenticated()) {
            throw new AuthenticationRequiredException("Only authenticated users can access domains");
        }

        List<DomainEntity> domainEntities = domainRepository.findAll();

        return domainEntities.stream()
                .map(domainEntity -> new DomainResponse(domainEntity.getId(), domainEntity.getDomainUrl()))
                .toList();
    }

    public DomainResponse getDomainByDomainUrl(String domainUrl) {
        DomainEntity domainEntity = domainRepository.findByDomainUrl(domainUrl)
                .orElseThrow(() -> new DomainNotFoundException("Domain not exists with url: " + domainUrl));

        return new DomainResponse(domainEntity.getId(), domainEntity.getDomainUrl());
    }

    public DomainResponse updateDomain(Long id, DomainRequest domainRequest) {
        validateAdminPrivileges("Only admin users can update domains");

        DomainEntity domainEntity = domainRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Domain not exists with id: " + id));

        domainEntity.setDomainUrl(domainRequest.domainUrl());

        DomainEntity updatedDomain = domainRepository.save(domainEntity);

        return new DomainResponse(updatedDomain.getId(), updatedDomain.getDomainUrl());
    }

    public void deleteDomainById(Long id) {
        validateAdminPrivileges("Only admin users can delete domains");

        if (!domainRepository.existsById(id)) {
            throw new DomainNotFoundException("Domain not exists with id: " + id);
        }

        domainRepository.deleteById(id);
    }

    private void validateAdminPrivileges(String message) {
        if (!currentUserProvider.isAdmin()) {
            throw new ForbiddenOperationException(message);
        }
    }
}
