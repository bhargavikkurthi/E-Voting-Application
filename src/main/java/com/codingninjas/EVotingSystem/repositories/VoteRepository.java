package com.codingninjas.EVotingSystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingninjas.EVotingSystem.entities.Election;
import com.codingninjas.EVotingSystem.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

	@Query(value = "SELECT COUNT(*) FROM vote", nativeQuery = true)
	Long countTotalVotes();

//	@Query("SELECT COUNT(v) FROM Vote v")
//	Long countTotalVotes(); // JPQL version

	@Query("SELECT COUNT(v) FROM Vote v WHERE v.election = :election")
	Long countVotesByElection(@Param("election") Election election);

	Page<Vote> findAll(Pageable pageable); // pagination support
}
