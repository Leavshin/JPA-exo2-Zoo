package org.example.repository;

import org.example.entity.Animal;
import org.example.util.Diet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class AnimalRepository {
    private final EntityManager entityManager;

    public AnimalRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createAnimal(Animal animal) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(animal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la création de l'animal.", e);
        }
    }

    public Animal findById(int id) {
        try {
            return entityManager.find(Animal.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'animal par ID.", e);
        }
    }

    public List<Animal> findByName(String nameSearch) {
        try {
            return entityManager.createQuery("SELECT a FROM Animal a WHERE a.name = :name", Animal.class)
                    .setParameter("name", nameSearch)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'animal par nom.", e);
        }
    }

    public List<Animal> findByDiet(Diet dietSearch) {
        try {
            return entityManager.createQuery("SELECT a FROM Animal a WHERE a.diet = :diet", Animal.class)
                    .setParameter("diet", dietSearch)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'animal par régime alimentaire.", e);
        }
    }
}
