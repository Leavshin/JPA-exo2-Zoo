package org.example.util;

import org.example.entity.Animal;
import org.example.repository.AnimalRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class IHMAnimal {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;
    private AnimalRepository animalRepository;

    public IHMAnimal(){
        emf = Persistence.createEntityManagerFactory("exo2");
        em = emf.createEntityManager();
        animalRepository = new AnimalRepository(em);
        sc = new Scanner(System.in);
    }

    public void start(){
        int entry;
        while(true){
            System.out.println(" ---- Menu Zoo -----");
            System.out.println("1. Ajouter un animal");
            System.out.println("2. Trouver un animal par son ID");
            System.out.println("3. Trouver un animal par son nom");
            System.out.println("4. Trouver un animal par son régime alimentaire");
            System.out.println("5. Quitter");
            entry = sc.nextInt();
            sc.nextLine();
            switch(entry){
                case 1 -> createAnimal();
                case 2 -> getAnimalById();
                case 3 -> getAnimalByName();
                case 4 -> getAnimalByDiet();
                case 5 -> {
                    em.close();
                    emf.close();
                    return;
                }
                default -> System.out.println("Choix invalide");
            }
        }
    }

    public void createAnimal(){
        System.out.println(" --- Ajouter un animal --- ");
        System.out.println("Nom de l'animal :");
        String name = sc.nextLine();
        System.out.println("Age de l'animal :");
        int age = sc.nextInt();
        sc.nextLine();
        Diet diet = getDiet();
        LocalDate arrivalDate = LocalDate.now();
        Animal animal = Animal.builder().name(name).age(age).diet(diet).arrivalDate(arrivalDate).build();
        animalRepository.createAnimal(animal);
        System.out.println("Animal créé : " + animal);
    }

    public void getAnimalById(){
        System.out.println(" --- Trouver un animal par son ID --- " );
        System.out.println("ID de l'animal :");
        int id = sc.nextInt();
        sc.nextLine();
        Animal animalFound = animalRepository.findById(id);
        if(animalFound != null){
            System.out.println(animalFound);
        }else{
            System.out.println("Animal introuvable");
        }
    }

    public void getAnimalByName(){
        System.out.println(" --- Trouver un animal par son nom --- ");
        System.out.println("Nom de l'animal :");
        String name = sc.nextLine();
        List<Animal> animals = animalRepository.findByName(name);
        if(animals.isEmpty()){
            System.out.println("Animal introuvable");
        }else{
            System.out.println(animals);
        }
    }

    public void getAnimalByDiet(){
        System.out.println(" --- Trouver un animal par son régime alimentaire --- ");
        Diet diet = getDiet();
        List<Animal> animals = animalRepository.findByDiet(diet);
        if(animals.isEmpty()){
            System.out.println("Animal introuvable");
        }else{
            System.out.println(animals);
        }
    }

    private Diet getDiet(){
        for (Diet value : Diet.values()){
            System.out.println(value);
        }
        System.out.println("Entrer le régime alimentaire :");
        String diet = sc.nextLine().toUpperCase().trim();
        try {
            return Diet.valueOf(diet);
        } catch (IllegalArgumentException e) {
            System.out.println("Régime alimentaire invalide");
            return getDiet();
        }
    }
}
