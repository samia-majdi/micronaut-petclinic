package io.micronaut.samples.petclinic.repository.h2;

import io.micronaut.context.annotation.Requires;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.GenericRepository;
import io.micronaut.samples.petclinic.model.*;
import io.micronaut.samples.petclinic.repository.*;
import java.util.Collection;
import java.util.List;

public final class H2Repositories {
    private H2Repositories() {
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2OwnerRepository extends OwnerRepository {
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2PetRepository extends PetRepository {
        @Override
        @Query(value = "SELECT p.* FROM pets p WHERE p.owner_id = :ownerId ORDER BY p.name", nativeQuery = true)
        Collection<Pet> findByOwnerId(Integer ownerId);

        @Override
        @Query(value = "SELECT p.* FROM pets p WHERE p.owner_id IN (:ownerIds) ORDER BY p.owner_id, p.name", nativeQuery = true)
        List<Pet> findByOwnerIdIn(List<Integer> ownerIds);
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2PetTypeRepository extends PetTypeRepository {
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2SpecialtyRepository extends SpecialtyRepository {
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2VetRepository extends VetRepository {
        @Override
        @Query(value = "SELECT v.* FROM vets v ORDER BY v.last_name", nativeQuery = true)
        Collection<Vet> findAllWithSpecialties();
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2VisitRepository extends VisitRepository {
        @Override
        @Query(value = "SELECT v.* FROM visits v WHERE v.pet_id = :petId ORDER BY v.visit_date DESC", nativeQuery = true)
        Collection<Visit> findByPetId(Integer petId);
    }

    @Requires(notEnv = {"mysql", "postgres", "oracle"})
    @JdbcRepository(dialect = Dialect.H2)
    public interface H2VetSpecialtyRepository extends VetSpecialtyRepository {
        @Override
        @Query(value = "SELECT s.* FROM specialties s JOIN vet_specialties vs ON vs.specialty_id = s.id WHERE vs.vet_id = :vetId ORDER BY s.name", nativeQuery = true)
        List<Specialty> findSpecialtiesByVetId(Integer vetId);
    }
}
