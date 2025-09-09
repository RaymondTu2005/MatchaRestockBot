package com.example.MatchaRestock.Restock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchaRepository extends JpaRepository<Matcha, Long> {

}
