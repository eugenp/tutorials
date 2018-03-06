<#--
usage:
<#import /lib/auth.ftl as auth>
auth.username
-->
<#assign username="cn.nonocast.tag.UserNameMethod"?new()>
<#assign username=username()>
<#assign admin="cn.nonocast.tag.IsAdminMethod"?new()>
<#assign admin=admin()>
