package com.suri.blogic.ports;

import com.suri.model.Member;

public interface MemberOpsPort {
    String addMember(Member member);
    String removeMember(Member member);
}
