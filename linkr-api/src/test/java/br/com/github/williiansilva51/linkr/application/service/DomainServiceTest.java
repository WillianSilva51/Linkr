package br.com.github.williiansilva51.linkr.application.service;

import br.com.github.williiansilva51.linkr.database.model.DomainEntity;
import br.com.github.williiansilva51.linkr.database.repository.DomainRepository;
import br.com.github.williiansilva51.linkr.dto.request.domain.DomainRequest;
import br.com.github.williiansilva51.linkr.dto.response.domain.DomainResponse;
import br.com.github.williiansilva51.linkr.handler.exceptions.domain.DomainAlreadyExistsException;
import br.com.github.williiansilva51.linkr.handler.exceptions.domain.DomainNotFoundException;
import br.com.github.williiansilva51.linkr.handler.exceptions.user.AuthenticationRequiredException;
import br.com.github.williiansilva51.linkr.handler.exceptions.user.ForbiddenOperationException;
import br.com.github.williiansilva51.linkr.security.CurrentUserProvider;
import br.com.github.williiansilva51.linkr.service.DomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DomainServiceTest {
    @Mock
    private DomainRepository domainRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private DomainService domainService;

    private DomainEntity createDomainValid() {
        return DomainEntity.builder()
                .id(1L)
                .domainUrl("example.com")
                .build();
    }

    @Nested
    @DisplayName("Create Domain")
    class CreateDomainTest {
        String domainUrl;
        DomainEntity domain;
        DomainRequest domainRequest;

        @BeforeEach
        void setUp() {
            domainUrl = "example.com";
            domain = createDomainValid();
            domainRequest = new DomainRequest(domainUrl);
        }

        @Test
        @DisplayName("Should create a new domain")
        void shouldCreateANewDomainSuccessfully() {
            when(currentUserProvider.isAdmin()).thenReturn(true);
            when(domainRepository.existsByDomainUrl(domainRequest.domainUrl())).thenReturn(false);
            when(domainRepository.save(any(DomainEntity.class))).thenReturn(domain);

            DomainResponse domainResponse = domainService.createDomain(domainRequest);

            ArgumentCaptor<DomainEntity> domainCaptor = ArgumentCaptor.forClass(DomainEntity.class);

            verify(currentUserProvider).isAdmin();
            verify(domainRepository).existsByDomainUrl(domainRequest.domainUrl());
            verify(domainRepository).save(domainCaptor.capture());


            assertThat(domainResponse.id()).isEqualTo(domain.getId());
            assertThat(domainResponse.domainUrl()).isEqualTo(domain.getDomainUrl());

            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throws `ForbiddenOperationException` when non-admin user")
        void shouldThrowsForbiddenOperationWhenNonAdminUser() {
            when(currentUserProvider.isAdmin()).thenReturn(false);

            assertThatThrownBy(() -> domainService.createDomain(domainRequest))
                    .isInstanceOf(ForbiddenOperationException.class)
                    .hasMessage("Only admin users can create domains");

            verify(currentUserProvider).isAdmin();
            verify(domainRepository, never()).save(any(DomainEntity.class));
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throw `DomainAlreadyExistsException` when duplicate domain")
        void shouldThrowDomainAlreadyExistsWhenDuplicateDomain() {
            when(currentUserProvider.isAdmin()).thenReturn(true);
            when(domainRepository.existsByDomainUrl(domainRequest.domainUrl())).thenReturn(true);

            assertThatThrownBy(() -> domainService.createDomain(domainRequest))
                    .isInstanceOf(DomainAlreadyExistsException.class)
                    .hasMessage("Domain already exists with url: " + domainRequest.domainUrl());

            verify(currentUserProvider).isAdmin();
            verify(domainRepository).existsByDomainUrl(domainRequest.domainUrl());
            verify(domainRepository, never()).save(any(DomainEntity.class));
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }
    }

    @Nested
    @DisplayName("Get Domain")
    class GetDomainTest {
        List<DomainEntity> domainEntities;
        String domainUrl;
        DomainEntity domain;

        @BeforeEach
        void setUp() {
            domainEntities = List.of(
                    new DomainEntity(1L, "example.com"),
                    new DomainEntity(2L, "example2.com"),
                    new DomainEntity(3L, "example3.com")
            );
            domainUrl = "example.com";
            domain = createDomainValid();
        }

        @Test
        @DisplayName("Should get all domains")
        void shouldGetAllDomains() {
            when(currentUserProvider.isAuthenticated()).thenReturn(true);
            when(domainRepository.findAll()).thenReturn(domainEntities);

            List<DomainResponse> domainResponses = domainService.getAllDomains();

            assertThat(domainResponses).hasSize(3);
            assertThat(domainResponses.get(0).id()).isEqualTo(1L);
            assertThat(domainResponses.get(0).domainUrl()).isEqualTo("example.com");
            assertThat(domainResponses.get(1).id()).isEqualTo(2L);
            assertThat(domainResponses.get(1).domainUrl()).isEqualTo("example2.com");
            assertThat(domainResponses.get(2).id()).isEqualTo(3L);
            assertThat(domainResponses.get(2).domainUrl()).isEqualTo("example3.com");

            verify(currentUserProvider).isAuthenticated();
            verify(domainRepository).findAll();
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should return empty list when user is authenticated but no domains exist")
        void shouldReturnEmptyListIfNoDomainsExists() {
            when(currentUserProvider.isAuthenticated()).thenReturn(true);
            when(domainRepository.findAll()).thenReturn(Collections.emptyList());

            List<DomainResponse> domainResponses = domainService.getAllDomains();

            assertThat(domainResponses).isEmpty();

            verify(currentUserProvider).isAuthenticated();
            verify(domainRepository).findAll();
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throw `AuthenticationRequiredException` when user is not authenticated")
        void shouldThrowAuthenticationRequiredWhenUserIsNotAuthenticated() {
            when(currentUserProvider.isAuthenticated()).thenReturn(false);

            assertThatThrownBy(() -> domainService.getAllDomains())
                    .isInstanceOf(AuthenticationRequiredException.class)
                    .hasMessage("Only authenticated users can access domains");

            verify(currentUserProvider).isAuthenticated();
            verify(domainRepository, never()).findAll();
            verifyNoMoreInteractions(currentUserProvider);
        }

        @Test
        @DisplayName("Should get domain by domain url")
        void shouldGetDomainByDomainUrl() {
            when(domainRepository.findByDomainUrl(domainUrl)).thenReturn(Optional.of(domain));

            DomainResponse domainResponse = domainService.getDomainByDomainUrl(domainUrl);

            assertThat(domainResponse.id()).isEqualTo(domain.getId());
            assertThat(domainResponse.domainUrl()).isEqualTo(domain.getDomainUrl());
            assertThat(domainResponse.domainUrl()).isEqualTo(domainUrl);

            verify(domainRepository).findByDomainUrl(domainUrl);
            verifyNoMoreInteractions(domainRepository);
        }

        @Test
        @DisplayName("Should throw `DomainNotFoundException` when domain not exists")
        void shouldThrowDomainNotFoundWhenDomainNotExists() {
            when(domainRepository.findByDomainUrl(domainUrl)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> domainService.getDomainByDomainUrl(domainUrl))
                    .isInstanceOf(DomainNotFoundException.class)
                    .hasMessage("Domain not exists with url: " + domainUrl);

            verify(domainRepository).findByDomainUrl(domainUrl);
            verifyNoMoreInteractions(domainRepository);
        }
    }

    @Nested
    @DisplayName("Update Domain")
    class UpdateDomainTest {
        Long domainId;
        DomainRequest domainRequest;

        @BeforeEach
        void setUp() {
            domainId = 1L;
            domainRequest = new DomainRequest("example.com");
        }

        @Test
        @DisplayName("Should update domain successfully")
        void shouldUpdateDomainSuccessfully() {
            DomainEntity domain = createDomainValid();
            DomainRequest request = new DomainRequest("newExample.com");

            when(currentUserProvider.isAdmin()).thenReturn(true);
            when(domainRepository.findById(domainId)).thenReturn(Optional.of(domain));
            when(domainRepository.save(domain)).thenReturn(domain);

            DomainResponse domainResponse = domainService.updateDomain(domainId, request);

            assertThat(domainResponse.id()).isEqualTo(domainId);
            assertThat(domainResponse.domainUrl()).isEqualTo(request.domainUrl());

            verify(currentUserProvider).isAdmin();
            verify(domainRepository).findById(domainId);
            verify(domainRepository).save(domain);
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throws `ForbiddenOperationException` when non-admin user")
        void shouldThrowsForbiddenOperationWhenNonAdminUser() {
            when(currentUserProvider.isAdmin()).thenReturn(false);

            assertThatThrownBy(() -> domainService.updateDomain(domainId, domainRequest))
                    .isInstanceOf(ForbiddenOperationException.class)
                    .hasMessage("Only admin users can update domains");

            verify(currentUserProvider).isAdmin();
            verify(domainRepository, never()).save(any(DomainEntity.class));
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throws `DomainNotFoundException` when domain not found")
        void shouldThrowsDomainNotFoundExceptionWhenDomainNotFound() {
            when(currentUserProvider.isAdmin()).thenReturn(true);
            when(domainRepository.findById(domainId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> domainService.updateDomain(domainId, domainRequest))
                    .isInstanceOf(DomainNotFoundException.class)
                    .hasMessage("Domain not exists with id: " + domainId);

            verify(currentUserProvider).isAdmin();
            verify(domainRepository, never()).deleteById(domainId);
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }
    }

    @Nested
    @DisplayName("Delete Domain")
    class DeleteDomainTest {
        Long domainId;

        @BeforeEach
        void setUp() {
            domainId = 1L;
        }

        @Test
        @DisplayName("Should delete domain by id")
        void shouldDeleteDomainById() {
            when(currentUserProvider.isAdmin()).thenReturn(true);
            when(domainRepository.existsById(domainId)).thenReturn(true);

            domainService.deleteDomainById(domainId);

            verify(currentUserProvider).isAdmin();
            verify(domainRepository).existsById(domainId);
            verify(domainRepository).deleteById(domainId);

            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throws `ForbiddenOperationException` when non-admin user")
        void shouldThrowsForbiddenOperationExceptionWhenNonAdminUser() {
            when(currentUserProvider.isAdmin()).thenReturn(false);

            assertThatThrownBy(() -> domainService.deleteDomainById(domainId))
                    .isInstanceOf(ForbiddenOperationException.class)
                    .hasMessage("Only admin users can delete domains");

            verify(currentUserProvider).isAdmin();
            verify(domainRepository, never()).deleteById(domainId);
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }

        @Test
        @DisplayName("Should throws `DomainNotFoundException` when domain not found")
        void shouldThrowsDomainNotFoundExceptionWhenDomainNotFound() {
            when(currentUserProvider.isAdmin()).thenReturn(true);
            when(domainRepository.existsById(domainId)).thenReturn(false);

            assertThatThrownBy(() -> domainService.deleteDomainById(domainId))
                    .isInstanceOf(DomainNotFoundException.class)
                    .hasMessage("Domain not exists with id: " + domainId);

            verify(currentUserProvider).isAdmin();
            verify(domainRepository, never()).deleteById(domainId);
            verifyNoMoreInteractions(currentUserProvider, domainRepository);
        }
    }
}
