package io.namoosori.tc.logic;

import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.ClubService;
import io.namoosori.tc.service.dto.TravelClubDto;
import io.namoosori.tc.shared.NameValueList;
import io.namoosori.tc.store.repository.ClubRepository;
import io.namoosori.tc.utility.NoSuchClubException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceLogic implements ClubService {
    private final ClubRepository clubRepository;

    public ClubServiceLogic(ClubRepository clubRepository) {
        super();
        this.clubRepository = clubRepository;
    }

    @Override
    public TravelClub registerClub(TravelClub club) {
        return clubRepository.save(club);
    }

    @Override
    public TravelClub findClubById(String clubId) {
        Optional<TravelClub> result = clubRepository.findById(clubId);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoSuchClubException("No club with such id exists.");
        }
    }

    @Override
    public List<TravelClub> findClubsByName(String clubName) {
        return clubRepository.findAllByClubName(clubName);
    }

    @Override
    public List<TravelClub> findAll() {
        return clubRepository.findAll();
    }

    @Override
    public TravelClub modify(String clubId, TravelClub clubRequest) {
        TravelClub club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchClubException("No such club exists."));

        if (clubRequest.getClubName() != null) {
            club.setClubName(clubRequest.getClubName());
        }

        if (clubRequest.getClubIntro() != null) {
            club.setClubIntro(clubRequest.getClubIntro());
        }

        return clubRepository.save(club);
    }

    @Override
    public void remove(String clubId) {
        TravelClub club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchClubException("Club does not exist."));

        clubRepository.delete(club);
        System.out.println(clubId + " has been deleted.");
    }
}
