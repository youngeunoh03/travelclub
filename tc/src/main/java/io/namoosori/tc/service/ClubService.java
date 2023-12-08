package io.namoosori.tc.service;

import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.dto.TravelClubDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClubService {
    //
    TravelClub registerClub(TravelClub club);
    TravelClub findClubById(String clubId);
    List<TravelClub> findClubsByName(String clubName);
    List<TravelClub> findAll();
    TravelClub modify(String clubId, TravelClub clubRequest);
    void remove(String clubId);
}
