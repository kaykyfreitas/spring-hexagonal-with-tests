package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.Pagination;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
public class UserPostgresGateway implements UserGateway {

    private final UserJpaRepository userJpaRepository;

    public UserPostgresGateway(final UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User create(final User user) {
        return this.save(user);
    }

    @Override
    public User update(final User user) {
        return this.save(user);
    }

    @Override
    public void deleteById(final UserId userId) {
        this.userJpaRepository.deleteById(userId.getValue());
    }

    @Override
    public Optional<User> findById(final UserId userId) {
        return this.userJpaRepository.findById(userId.getValue())
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public Pagination<User> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.Direction.fromString(query.direction()),
                query.sort()
        );

        if (isNull(query.terms())) {
            final var pageResult = userJpaRepository.findAll(page);

            return new Pagination<>(
                    pageResult.getNumber(),
                    pageResult.getSize(),
                    pageResult.getTotalElements(),
                    pageResult.map(UserJpaEntity::toDomain).toList()
            );
        }

        final var example = assembleExample(query.terms());

        final var pageResult = this.userJpaRepository.findAll(example, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(UserJpaEntity::toDomain).toList()
        );
    }

    private User save(final User user) {
        return this.userJpaRepository
                .save(UserJpaEntity.fromDomain(user))
                .toDomain();
    }

    private Example<UserJpaEntity> assembleExample(final String terms) {
        final UserJpaEntity userExample = new UserJpaEntity();
        userExample.setName(terms);
        userExample.setUsername(terms);
        userExample.setEmail(terms);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(userExample, matcher);
    }

}
