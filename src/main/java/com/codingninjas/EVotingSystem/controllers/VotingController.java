package com.codingninjas.EVotingSystem.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.codingninjas.EVotingSystem.entities.Election;
import com.codingninjas.EVotingSystem.entities.ElectionChoice;
import com.codingninjas.EVotingSystem.entities.User;
import com.codingninjas.EVotingSystem.entities.Vote;
import com.codingninjas.EVotingSystem.services.VotingService;

@RestController
public class VotingController {
	
	@Autowired
	VotingService votingService;


	//	Election Endpoints
	@PostMapping("/elections")
	public void addElection(@RequestBody Election election) {
		votingService.addElection(election);
	}

	@GetMapping("/elections")
	public List<Election> getAllElections(){
		return votingService.getAllElections();
	}


	//	ElectionChoice Endpoints
	@PostMapping("/election-choices")
	public void addElectionChoice(@RequestBody ElectionChoice electionChoice) {
		Election election =
				votingService.findElectionByName(electionChoice.getElection().getName());
		electionChoice.setElection(election);
		votingService.addElectionChoice(electionChoice);
	}

	@GetMapping("/election-choices")
	public List<ElectionChoice> getAllElectionChoices() {
		return votingService.getAllElectionChoices();
	}

	@PostMapping("/election-choices/count")
	public long getTotalNumberOfChoicesByElection(@RequestBody Election election) {
		Election actualElection = votingService.findElectionByName(election.getName());
		return votingService.countChoicesByElection(actualElection);
	}


//	User Endpoints
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return votingService.getAllUsers();
	}
	
	@PostMapping("/users")
	public void addUser(@RequestBody User user) {
		votingService.addUser(user);
	}


//	Vote Endpoints
	@GetMapping("/votes")
	public List<Vote> getAllVotes() {
		return votingService.getAllVotes();
	}

	@PostMapping("/votes")
	public void addVote(@RequestBody Vote vote) {
		User user =
				votingService.findUserByName(vote.getUser().getName());
		Election election =
				votingService.findElectionByName(vote.getElection().getName());
		ElectionChoice choice =
				votingService.findElectionChoiceByNameAndElection(
				vote.getElectionChoice().getName(), election);

		vote.setUser(user);
		vote.setElection(election);
		vote.setElectionChoice(choice);
		votingService.addVote(vote);
	}

	@GetMapping("/votes/count")
	public long getTotalVotes() {
		return votingService.countTotalVotes();
	}

	@PostMapping("/votes/count/election")
	public long getTotalNumberOfVotesByElection(@RequestBody Election election) {
		Election actualElection =
				votingService.findElectionByName(election.getName());
		return votingService.countVotesByElection(actualElection );
	}


//	Results Endpoints
	@PostMapping("/results/winner")
	public ElectionChoice getWinnerOfElection(@RequestBody Election election) {
		Election actualElection =
				votingService.findElectionByName(election.getName());
		return votingService.findElectionChoiceWithMaxVotes(actualElection);
	}




	@GetMapping("/get/users")
	public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
								  @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
		return votingService.getAllUsers(pageable);
	}

	@GetMapping("/get/votes")
	public Page<Vote> getAllVotes(@RequestParam(defaultValue = "0") int page,
								  @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
		return votingService.getAllVotes(pageable);
	}

	@GetMapping("/get/elections")
	public Page<Election> getAllElections(@RequestParam(defaultValue = "0") int page,
										  @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
		return votingService.getAllElections(pageable);
	}

	@GetMapping("/get/electionChoices")
	public Page<ElectionChoice> getAllElectionChoices(@RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
		return votingService.getAllElectionChoices(pageable);
	}

}
