var demo = {
    __noSuchProperty__: function (propName) {
        print("Accessed non-existing property: " + propName);
    },

    __noSuchMethod__: function (methodName) {
        print("Invoked non-existing method: " + methodName);
    }
};

demo;
