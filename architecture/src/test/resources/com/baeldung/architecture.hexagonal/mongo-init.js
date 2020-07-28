db.createUser(
    {
      user: "quote-user",
      pwd: "quote-pass",
      roles: [
        {
          role: "readWrite",
          db: "quotes-db"
        }
      ]
    }
);

db.quote.insert({quote: "To be or not to be, that is the question", author: "William Shakespeare"});
db.quote.insert({quote: "Learning never exhausts the mind", author: "Leonardo da Vinci"});
db.quote.insert({quote: "Simplicity is the ultimate sophistication", author: "Leonardo da Vinci"});
db.quote.insert({quote: "Nothing is certain except for death and taxes", author: "Benjamin Franklin"});
db.quote.insert({quote: "Life is what happens when you're busy making other plans", author: "John Lennon"});
