//<start id="user_detail_query"/> 
select username,password,enabled
  from users
 where username = ?
//<end id="user_detail_query"/> 


//<start id="user_authorities_query"/> 
select username,authority
  from authorities
 where username = ?
//<end id="user_authorities_query"/> 


//<start id="group_authorities_query"/> 
select g.id, g.group_name, ga.authority
  from groups g, group_members gm, group_authorities ga
 where gm.username = ?
   and g.id = ga.group_id
   and g.id = gm.group_id
//<end id="group_authorities_query"/> 
   