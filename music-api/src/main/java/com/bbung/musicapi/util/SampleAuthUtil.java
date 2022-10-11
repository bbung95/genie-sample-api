package com.bbung.musicapi.util;

import com.bbung.musicapi.common.MemberInfo;
import org.springframework.stereotype.Component;

@Component
public class SampleAuthUtil implements AuthUtil{

    @Override
    public MemberInfo getAuth() {

        String name = "bbung";
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setName(name);

        return memberInfo;
    }
}
