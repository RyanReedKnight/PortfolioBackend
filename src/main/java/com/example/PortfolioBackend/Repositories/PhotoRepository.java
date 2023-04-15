package com.example.PortfolioBackend.Repositories;

import com.example.PortfolioBackend.Models.PhotoRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoRecord, String> {



}
