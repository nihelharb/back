package com.SpringRestMongoDB.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SpringRestMongoDB.model.Historique;
import com.SpringRestMongoDB.model.Test;

public interface HistoriqueRepository extends MongoRepository<Historique, String>{
 List<Historique> findByNom(String nom);
}
