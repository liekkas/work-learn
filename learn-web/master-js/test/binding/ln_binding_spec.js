/**
 * Created by liekkas on 15/7/11.
 */
'use strict';

describe("binding",function(){
    it("binding use constructor",function(){
        expect(john.greet("liekkas")).toBe("Hi liekkas, my name is John");

    });

    it("binding use function",function(){
        var fx = john.greet;
        expect(fx("liekkas")).toBe("Hi liekkas, my name is Ray");

    });

    it("binding use function",function(){
        var alex = new Person('Alex','Josha',27);
        var bill = new Person('Bill','Gates',61);
        expect(alex.greet(bill)).toBe("Hi Bill, my name is Alex");

    });

    it("binding use function",function(){
        var alex = new Person('Alex','Josha',27);
        var bill = new Person('Bill','Gates',61);
        expect(alex.greet(bill)).toBe("Hi Bill, my name is Alex");
        expect(alex.getFullName()).toBe("Alex Josha");

    });

    it("binding use apply",function(){
        var alex = new Person('Alex','Josha',27);
        var bill = new Person('Bill','Gates',61);

        var fx = alex.greet;
        expect(fx.apply(alex,[bill])).toBe("Hi Bill, my name is Alex");
    });

    it("binding use call",function(){
        var alex = new Person('Alex','Josha',27);
        var bill = new Person('Bill','Gates',61);

        var fx = alex.greet;
        expect(fx.call(alex,bill)).toBe("Hi Bill, my name is Alex");
    });

    it("binding use createBoundedWrapper",function(){
        var alex = new Person('Alex','Josha',27);
        var bill = new Person('Bill','Gates',61);

        var alexGreet = createBoundedWrapper(alex,alex.greet);
        expect(alexGreet(bill)).toBe("Hi Bill, my name is Alex");
    });
});