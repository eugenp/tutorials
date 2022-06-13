#
# Simple authorization rule for accounts
#
# Assumes an input document with the following properties:
#
# resource: requested resource
# method: request method
# authorities: Granted authorities
# headers: Request headers
#
package baeldung.auth.account

# Not authorized by default
default authorized = false

# Authorize when there are no rules that deny access to the resource and
# there's at least one rule allowing
authorized = true {
    count(deny) == 0
    count(allow) > 0
}

# Allow access to /public
allow["public"] {
    regex.match("^/public/.*",input.uri)
}

# Account API requires authenticated user
deny["account_api_authenticated"] {
    regex.match("^/account/.*",input.uri)
    regex.match("ANONYMOUS",input.principal)
}

# Authorize access to account if principal has
# matching authority
allow["account_api_authorized"] {
    regex.match("^/account/.+",input.uri)
    parts := split(input.uri,"/")
    account := parts[2]
    role := concat(":",[ "ROLE_account", "read", account] )
    role == input.authorities[i]
}

