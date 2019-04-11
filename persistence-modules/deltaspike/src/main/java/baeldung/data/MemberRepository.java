/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package baeldung.data;

import baeldung.model.Member;
import baeldung.model.QMember;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
@EntityManagerConfig(entityManagerResolver = SecondaryEntityManagerResolver.class)
public abstract class MemberRepository extends AbstractEntityRepository<Member, Long> implements QueryDslSupport {

    public abstract Member findById(Long id);

    public abstract Member findByEmail(String email);

    @Query("from Member m order by m.name")
    public abstract List<Member> findAllOrderedByName();

    public List<Member> findAllOrderedByNameWithQueryDSL() {
        final QMember member = QMember.member;
        return jpaQuery()
          .from(member)
          .orderBy(member.email.asc())
          .list(member);
    }
}
