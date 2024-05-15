package com.baeldung.map.hashing;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class MemberWithGuavaHashing extends Member {
    @Override
    public int hashCode() {
        HashFunction hashFunction = Hashing.murmur3_32();
        return hashFunction.newHasher()
                .putInt(id)
                .putString(name, Charsets.UTF_8)
                .hash().hashCode();
    }


}
