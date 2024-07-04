package org.example.service;

import org.example.repository.AnimalRepository;
import org.example.entity.Animal;
import org.example.util.Diet;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class s {
    private final AnimalRepository animalRepository;
    private final Scanner scanner;

    public s(AnimalRepository animalRepository, Scanner scanner) {
        this.animalRepository = animalRepository;
        this.scanner = scanner;
    }

    public void addAnimal() {
        try {
            System.out.print("Entrez le nom de l'animal: ");
            String name = scanner.nextLine();
            System.out.print("Entrez l'âge de l'animal: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Entrez le régime alimentaire (HERBIVORE, CARNIVORE, OMNIVORE): ");
            String diet = scanner.nextLine().toUpperCase();
            Diet dietEnum;
            try {
                dietEnum = Diet.valueOf(diet);
            } catch (IllegalArgumentException e) {
                System.out.println("Régime alimentaire invalide.");
                return;
            }
            LocalDate arrivalDate = LocalDate.now();
            Animal animal = Animal.builder()
                    .name(name)
                    .age(age)
                    .diet(dietEnum)
                    .arrivalDate(arrivalDate)
                    .build();

            animalRepository.createAnimal(animal);

            System.out.println("Animal ajouté avec succès !");
        } catch (NumberFormatException e) {
            System.out.println("Erreur: âge invalide.");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void searchAnimalById() {
        try {
            System.out.print("Entrez l'ID de l'animal: ");
            int id = Integer.parseInt(scanner.nextLine());
            Animal animal = animalRepository.findById(id);
            if (animal != null) {
                System.out.println("Animal trouvé: " + animal);
            } else {
                System.out.println("Aucun animal trouvé avec cet ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erreur: ID invalide.");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void searchAnimalByName() {
        try {
            System.out.print("Entrez le nom de l'animal: ");
            String nom = scanner.nextLine();
            List<Animal> animals = animalRepository.findByName(nom);
            if (!animals.isEmpty()) {
                animals.forEach(System.out::println);
            } else {
                System.out.println("Aucun animal trouvé avec ce nom.");
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    public void searchAnimalByRegime() {
        try {
            System.out.print("Entrez le régime alimentaire (HERBIVORE, CARNIVORE, OMNIVORE): ");
            String diet = scanner.nextLine().toUpperCase();
            Diet dietEnum;
            try {
                dietEnum = Diet.valueOf(diet);
            } catch (IllegalArgumentException e) {
                System.out.println("Régime alimentaire invalide.");
                return;
            }
            List<Animal> animals = animalRepository.findByDiet(dietEnum);
            if (!animals.isEmpty()) {
                animals.forEach(System.out::println);
            } else {
                System.out.println("Aucun animal trouvé avec ce régime alimentaire.");
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}
