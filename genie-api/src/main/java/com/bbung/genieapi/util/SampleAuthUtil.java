package com.bbung.genieapi.util;

import com.bbung.genieapi.common.MemberInfo;
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
