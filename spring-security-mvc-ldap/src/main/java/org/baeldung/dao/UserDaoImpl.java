package org.baeldung.dao;

import org.baeldung.dto.User;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.util.List;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class UserDaoImpl implements UserDao {

	private LdapTemplate ldapTemplate;

    @Override
	public void create(User user) {
		Name dn = buildDn(user);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(user, context);
		ldapTemplate.bind(dn, context, null);
	}

    @Override
	public void update(User user) {
		Name dn = buildDn(user);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(user, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}

    @Override
	public void delete(User user) {
		ldapTemplate.unbind(buildDn(user));
	}

    @Override
	public List<String> getAllUserNames() {
        return ldapTemplate.search(query()
                .attributes("cn")
                .where("objectclass").is("person"),
				(AttributesMapper<String>) attrs -> attrs.get("cn").get().toString());
    }

    @Override
	public List<User> findAll() {
		return ldapTemplate.search(query()
                .where("objectclass").is("person"),
				USER_CONTEXT_MAPPER);
	}

    @Override
	public User findByPrimaryKey(String country, String company, String fullname) {
		LdapName dn = buildDn(country, company, fullname);
		return ldapTemplate.lookup(dn, USER_CONTEXT_MAPPER);
	}

	private LdapName buildDn(User user) {
		return buildDn(user.getOrganization(), user.getCompany(), user.getFullName());
	}

	private LdapName buildDn(String organization, String company, String fullname) {
        return LdapNameBuilder.newInstance()
                .add("dc", company)
                .add("ou", organization)
                .add("cn", fullname)
                .build();
	}

	private void mapToContext(User user, DirContextAdapter context) {
		context.setAttributeValues("objectclass", new String[] { "top", "person" });
		context.setAttributeValue("cn", user.getFullName());
		context.setAttributeValue("sn", user.getLastName());
		context.setAttributeValue("description", user.getDescription());
		context.setAttributeValue("mobile", user.getMobile());
	}

	private final static ContextMapper<User> USER_CONTEXT_MAPPER = new AbstractContextMapper<User>() {
        @Override
		public User doMapFromContext(DirContextOperations context) {
			User person = new User();
            LdapName dn = LdapUtils.newLdapName(context.getDn());
			person.setOrganization(LdapUtils.getStringValue(dn, 0));
			person.setCompany(LdapUtils.getStringValue(dn, 1));
			person.setFullName(context.getStringAttribute("cn"));
			person.setLastName(context.getStringAttribute("sn"));
			person.setDescription(context.getStringAttribute("description"));
			person.setMobile(context.getStringAttribute("mobile"));
			return person;
		}
	};

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
}
