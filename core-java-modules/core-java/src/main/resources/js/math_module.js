var math = {
    increment: function (num) {
        return ++num;
    },

    failFunc: function () {
        try {
            throw "BOOM";
        } catch (e if typeof e === 'string') {
            print("String thrown: " + e);
        }
        catch (e) {
            print("this shouldn't happen!");
        }
    }
};


math;
