package io.namoosori.tc.controller;

import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.ClubService;
import io.namoosori.tc.service.dto.TravelClubDto;
import io.namoosori.tc.utility.NoSuchClubException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/club")
public class ClubController {
    @Autowired
    private ModelMapper modelMapper;

    private ClubService clubService;

    public ClubController(ClubService clubService) {
        super();
        this.clubService = clubService;
    }

    @PostMapping
    public ResponseEntity<TravelClubDto> register(@RequestBody TravelClubDto travelClubDto) {
        TravelClub clubRequest = modelMapper.map(travelClubDto, TravelClub.class);

        TravelClub club = clubService.registerClub(clubRequest);

        TravelClubDto clubResponse = modelMapper.map(club, TravelClubDto.class);

        return new ResponseEntity<TravelClubDto>(clubResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelClubDto> findClubById(@PathVariable(name = "id") long id) {
        String clubId = String.valueOf(id);
        TravelClub club = clubService.findClubById(clubId);

        if (club.equals(null)) {
            throw new NoSuchClubException("No such club exists.");
        }

        TravelClubDto clubResponse = modelMapper.map(club, TravelClubDto.class);

        return ResponseEntity.ok().body(clubResponse);
    }

    @GetMapping("/name/{name}")
    public List<TravelClubDto> findClubsByName(@PathVariable(name = "name") String name) {
        return clubService.findClubsByName(name).stream()
                .map(clubs -> modelMapper.map(clubs, TravelClubDto.class))
                .collect(Collectors.toList());
        // TravelClub club = clubService.findClubsByName(name);
    }

    @GetMapping("/all")
    public List<TravelClubDto> findAll() {
        return clubService.findAll().stream().map(clubs -> modelMapper.map(clubs, TravelClubDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelClubDto> updateClub(@PathVariable long id, @RequestBody TravelClubDto clubDto) {
        TravelClub clubRequest = modelMapper.map(clubDto, TravelClub.class);

        String clubId = String.valueOf(id);

        TravelClub club = clubService.modify(clubId, clubRequest);

        TravelClubDto clubResponse = modelMapper.map(club, TravelClubDto.class);

        return ResponseEntity.ok().body(clubResponse);
    }

    @DeleteMapping ("/{id}")
    public void deleteClub(@PathVariable long id) {
        String clubId = String.valueOf(id);
        clubService.remove(clubId);
    }
}
