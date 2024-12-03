db.createUser(
    {
        user: "user1",
        pwd: "password",
        roles: [{role: "readWrite", db: "db1"}]
    }
)

db.createUser(
    {
        user: "user2",
        pwd: "password",
        roles: [{role: "readWrite", db: "db2"}]
    }
)