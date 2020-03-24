var first = {
    name: "Whiskey",
    age: 5
};

var second = {
    volume: 100
};

Object.bindProperties(first, second);

print(first.volume);

second.volume = 1000;
print(first.volume);
