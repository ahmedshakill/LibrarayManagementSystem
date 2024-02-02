package com.example.lms.service;


import com.example.lms.entity.Member;
import com.example.lms.entity.Transaction;
import com.example.lms.error.MemberNotFoundException;
import com.example.lms.repository.MemberRepository;
import com.example.lms.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    final MemberRepository memberRepository;
    final TransactionRepository transactionRepository;

    public MemberService(MemberRepository memberRepository, TransactionRepository transactionRepository) {
        this.memberRepository = memberRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(long id) {
        return memberRepository.findById(id);
    }

    public Member createMemberEntry(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMemberEntry(long id, Member member) throws MemberNotFoundException {
        Optional<Member> storedMember = memberRepository.findById(id);
        if(storedMember.isPresent()){
            Member memberToSave = storedMember.get();
            memberToSave.setFirstName(member.getFirstName());
            memberToSave.setLastName(member.getLastName());
            memberToSave.setEmail(member.getEmail());
            memberToSave.setBooksCheckedOut(member.getBooksCheckedOut());
            memberRepository.save(memberToSave);
            return memberToSave;
        }else{
            throw new MemberNotFoundException("MemberDoesNotExist!");
        }
    }

    public void deleteMemberEntry(long id) {
        memberRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsByUserId(long id) {
        return transactionRepository.findAllByUserId(id);
    }

    @Transactional
    public void increaseCheckOutCount(Member member,int numberOfBooks) {
        member.setBooksCheckedOut(member.getBooksCheckedOut()+numberOfBooks);
        memberRepository.save(member);
    }

    public void decreaseCheckOutCount(Member member) {
        member.setBooksCheckedOut(member.getBooksCheckedOut()-1);
        memberRepository.save(member);
    }
}
