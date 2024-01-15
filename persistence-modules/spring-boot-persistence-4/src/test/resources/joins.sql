SELECT profile.id,
       profile.biography,
       profile.website,
       profile.profile_picture_url,
       user.id,
       user.email,
       user.username,
       user_group.members_id,
       interest_group.id,
       interest_group.name,
       post.id,
       post.author_id,
       post.content,
       comment.id,
       comment.text,
       comment.post_id,
       comment_author.id,
       comment_author.profile_id,
       comment_author.username,
       comment_author.email,
       comment_author_group_member.members_id,
       comment_author_group.id,
       comment_author_group.name
FROM profile profile
         LEFT JOIN simple_user user
ON profile.id = user.profile_id
    LEFT JOIN (interest_group_members user_group
    JOIN interest_group interest_group
    ON interest_group.id = user_group.groups_id)
    ON user.id = user_group.members_id
    LEFT JOIN post post ON user.id = post.author_id
    LEFT JOIN comment comment ON post.id = comment.post_id
    LEFT JOIN simple_user comment_author ON comment_author.id = comment.author_id
    LEFT JOIN (interest_group_members comment_author_group_member
    JOIN interest_group comment_author_group
    ON comment_author_group.id = comment_author_group_member.groups_id)
    ON comment_author.id = comment_author_group_member.members_id
WHERE profile.id = ?

SELECT u.id, u.email, u.username, p.id, p.author_id, p.content
FROM simple_user u
         LEFT JOIN post p ON u.id = p.author_id
WHERE u.id = ?


SELECT u.id, u.email, u.username
FROM simple_user u

SELECT p.id, p.author_id, p.content
FROM post p
WHERE p.author_id = ?