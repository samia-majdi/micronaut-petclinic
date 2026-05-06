package io.micronaut.samples.petclinic.repository.mysql;

import io.micronaut.context.annotation.Requires;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.samples.petclinic.model.*;
import io.micronaut.samples.petclinic.repository.*;
import java.util.Collection;
import java.util.List;

public final class MySqlRepositories {
    private MySqlRepositories() {
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlOwnerRepository extends OwnerRepository {
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlPetRepository extends PetRepository {
        @Override
        @Query(value = "SELECT p.* FROM pets p WHERE p.owner_id = :ownerId ORDER BY p.name", nativeQuery = true)
        Collection<Pet> findByOwnerId(Integer ownerId);

        @Override
        @Query(value = "SELECT p.* FROM pets p WHERE p.owner_id IN (:ownerIds) ORDER BY p.owner_id, p.name", nativeQuery = true)
        List<Pet> findByOwnerIdIn(List<Integer> ownerIds);
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlPetTypeRepository extends PetTypeRepository {
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlSpecialtyRepository extends SpecialtyRepository {
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlVetRepository extends VetRepository {
        @Override
        @Query(value = "SELECT v.* FROM vets v ORDER BY v.last_name", nativeQuery = true)
        Collection<Vet> findAllWithSpecialties();
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlVisitRepository extends VisitRepository {
        @Override
        @Query(value = "SELECT v.* FROM visits v WHERE v.pet_id = :petId ORDER BY v.visit_date DESC", nativeQuery = true)
        Collection<Visit> findByPetId(Integer petId);
    }

    @Requires(env = "mysql")
    @JdbcRepository(dialect = Dialect.MYSQL)
    public interface MySqlVetSpecialtyRepository extends VetSpecialtyRepository {
        @Override
        @Query(value = "SELECT s.* FROM specialties s JOIN vet_specialties vs ON vs.specialty_id = s.id WHERE vs.vet_id = :vetId ORDER BY s.name", nativeQuery = true)
        List<Specialty> findSpecialtiesByVetId(Integer vetId);
    }
}
