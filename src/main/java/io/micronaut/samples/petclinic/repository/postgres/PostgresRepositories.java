package io.micronaut.samples.petclinic.repository.postgres;

import io.micronaut.context.annotation.Requires;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.samples.petclinic.model.*;
import io.micronaut.samples.petclinic.repository.*;
import java.util.Collection;
import java.util.List;

public final class PostgresRepositories {
    private PostgresRepositories() {
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresOwnerRepository extends OwnerRepository {
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresPetRepository extends PetRepository {
        @Override
        @Query(value = "SELECT p.* FROM pets p WHERE p.owner_id = :ownerId ORDER BY p.name", nativeQuery = true)
        Collection<Pet> findByOwnerId(Integer ownerId);

        @Override
        @Query(value = "SELECT p.* FROM pets p WHERE p.owner_id IN (:ownerIds) ORDER BY p.owner_id, p.name", nativeQuery = true)
        List<Pet> findByOwnerIdIn(List<Integer> ownerIds);
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresPetTypeRepository extends PetTypeRepository {
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresSpecialtyRepository extends SpecialtyRepository {
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresVetRepository extends VetRepository {
        @Override
        @Query(value = "SELECT v.* FROM vets v ORDER BY v.last_name", nativeQuery = true)
        Collection<Vet> findAllWithSpecialties();
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresVisitRepository extends VisitRepository {
        @Override
        @Query(value = "SELECT v.* FROM visits v WHERE v.pet_id = :petId ORDER BY v.visit_date DESC", nativeQuery = true)
        Collection<Visit> findByPetId(Integer petId);
    }

    @Requires(env = "postgres")
    @JdbcRepository(dialect = Dialect.POSTGRES)
    public interface PostgresVetSpecialtyRepository extends VetSpecialtyRepository {
        @Override
        @Query(value = "SELECT s.* FROM specialties s JOIN vet_specialties vs ON vs.specialty_id = s.id WHERE vs.vet_id = :vetId ORDER BY s.name", nativeQuery = true)
        List<Specialty> findSpecialtiesByVetId(Integer vetId);
    }
}
