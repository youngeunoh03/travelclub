package io.namoosori.tc.controller;

import io.namoosori.tc.entity.club.ClubMembership;
import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.service.ClubService;
import io.namoosori.tc.service.MemberService;
import io.namoosori.tc.service.MembershipService;
import io.namoosori.tc.service.dto.MembershipDto;
import io.namoosori.tc.utility.NoSuchMembershipException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/membership")
@RestController
public class MembershipController {
    @Autowired
    private ModelMapper modelMapper;

    private MembershipService membershipService;
    private ClubService clubService;
    private MemberService memberService;

    public MembershipController(MembershipService membershipService, ClubService clubService, MemberService memberService) {
        super();
        this.membershipService = membershipService;
        this.clubService = clubService;
        this.memberService = memberService;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PostMapping
    public ResponseEntity<MembershipDto> register(@RequestBody MembershipDto membershipDto) {
        //
        ClubMembership membership = modelMapper.map(membershipDto, ClubMembership.class);

        String result = membershipService.registerMembership(membershipDto);

        if (result.equals("Membership registered.")) {
            return new ResponseEntity<>(membershipDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipDto> findMembership (@PathVariable(name = "id") Long id) {
        ClubMembership membership = membershipService.findMembership(id);

        if (membership == null) {
            throw new NoSuchMembershipException("No such membership exists.");
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MembershipDto membershipResponse = modelMapper.map(membership, MembershipDto.class);

        return ResponseEntity.ok().body(membershipResponse);
    }

    @GetMapping("/club/{clubId}")
    public List<ClubMembership> findAllMembershipsOfClub(@PathVariable(name = "clubId") Long clubId) {
        return membershipService.findAllMembershipsOfClub(clubId).stream()
                .map(memberships -> modelMapper.map(memberships, ClubMembership.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/member/{memberId}")
    public List<ClubMembership> findAllMembershipsOfMember(@PathVariable(name = "memberId") Long memberId) {
        return membershipService.findAllMembershipsOfMember(memberId).stream()
                .map(memberships -> modelMapper.map(memberships, ClubMembership.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public String modifyMembership(@PathVariable(name = "id") long id, @RequestBody MembershipDto membershipDto) {
        String membershipId = String.valueOf(id);
        ClubMembership membership = membershipService.modifyMembership(membershipId, membershipDto);
        return "Membership has been modified.";
    }

    @DeleteMapping("/{id}")
    public String deleteMembership(@PathVariable long id) {
        String membershipId = String.valueOf(id);
        membershipService.removeMembership(membershipId);
        return "Membership has been removed.";
    }
}
