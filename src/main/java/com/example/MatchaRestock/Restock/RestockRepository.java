package com.example.MatchaRestock.Restock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestockRepository extends JpaRepository<Restock, Long> {
  boolean existsRestockByMatchaListContaining(List<Matcha> matchaList);
}
