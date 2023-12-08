package io.namoosori.tc.controller;

import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.service.MemberService;
import io.namoosori.tc.service.dto.MemberDto;
import io.namoosori.tc.utility.NoSuchMemberException;
import jakarta.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private ModelMapper modelMapper;

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        super();
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDto> register(@RequestBody MemberDto memberDto) {
        CommunityMember memberRequest = modelMapper.map(memberDto, CommunityMember.class);

        CommunityMember member = memberService.registerMember(memberRequest);

        MemberDto memberResponse = modelMapper.map(member, MemberDto.class);

        return new ResponseEntity<MemberDto>(memberResponse, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MemberDto> findMemberById(@PathVariable(name = "id") long id) {
        CommunityMember member = memberService.findMemberById(id);
        if (member.equals(null)) {
            throw new NoSuchMemberException("No such member exists.");
        }

        MemberDto memberResponse = modelMapper.map(member, MemberDto.class);

        return ResponseEntity.ok().body(memberResponse);
    }

    @GetMapping(value = "/email/{email:.+}")
    public ResponseEntity<MemberDto> findMemberByEmail(@PathVariable(name = "email") String email) {
        CommunityMember member = memberService.findMemberByEmail(email);

        if (member.equals(null)) {
            throw new NoSuchMemberException("No such member exists.");
        }

        MemberDto memberResponse = modelMapper.map(member, MemberDto.class);

        return ResponseEntity.ok().body(memberResponse);
    }

    @GetMapping(value = "/name/{name}")
    public List<MemberDto> findMemberByName(@PathVariable(name = "name") String name) {
        return memberService.findMembersByName(name).stream()
                .map(members -> modelMapper.map(members, MemberDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void modifyMember(@PathVariable long id, @RequestBody MemberDto memberDto) {
        CommunityMember member = memberService.modifyMember(id, memberDto);
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable long id) {
        memberService.removeMember(id);
        return "Member has been deleted.";
    }
}
