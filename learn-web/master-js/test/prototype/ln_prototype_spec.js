/**
 * Created by liekkas on 15/7/16.
 */
'use strict';

describe("原型", function () {

    function f1(){};
    var f2 = function(){};
    var f3 = new Function('str','console.log(str)');

    var o1 = {};
    var o2 = new Object();
    var o3 = new f1();

    //js中，分普通对象和函数对象
    //Object Function是JS自带的函数对象
    //如何区分：凡是通过new Function()创建的对象都是函数对鞋，其他的都是普通对象
    //f1,f2归根到底都是通过new Function()创建的，Function Object也都是通过New Function()创建的
    it("普通对象和函数对象", function () {

        expect(typeof Object).toBe('function');
        expect(typeof Function).toBe('function');
        expect(typeof o1).toBe('object');
        expect(typeof o2).toBe('object');
        expect(typeof o3).toBe('object');
        expect(typeof f1).toBe('function');
        expect(typeof f2).toBe('function');
        expect(typeof f3).toBe('function');
    });

    //在js中，每定义一个对象时，对象都会预定义一些属性。其中函数对象的一个属性就是原型对象prototype。
    //普通对象没有prototype，但有_proto_属性。
    //原型对象其实也是普通对象（Function.prototype除外，它是函数对象)
    it("原型对象", function () {
        var temp = new f1();
        //f1的原型就是f1的实例
        expect(f1.prototype).toEqual(temp);
        expect(typeof f1.prototype).toBe('object');

        //所以，Function.prototype为什么是函数对象就迎刃而解了，上文提到凡是new Function ()产生的对象都是函数对象，所以temp1是函数对象。
        //var temp1 = new Function ();
        //Function.prototype = temp1;
        expect(typeof Function.prototype).toBe('function');
        expect(typeof Object.prototype).toBe('object');
    });

    //把某个对象的__proto__属性串起来直到Object.prototype.__proto__为null得链叫做原型链
    it("原型链", function () {
        var liekkas = new person('liekkas');
        expect(liekkas.getName()).toBe('liekkas');

        //js在创建对象都有一个__proto__内置属性，用于指向创建它的函数对象的原型对象prototype
        expect(liekkas.__proto__).toBe(person.prototype);

        //同样，person.prototye对象也有__proto__属性，它指向创建它的函数对象(Object)的prototype
        expect(person.prototype.__proto__).toBe(Object.prototype);

        //继续，Object.prototype对象也有这个属性，但是为null
        expect(Object.prototype.__proto__).toBe(null);
    });

    //原型对象的prototype中都有个预定义的contructor属性，用来引用它的函数对象，这是一种循环引用
    it("构造函数", function () {
        expect(person.prototype.constructor).toBe(person);
        expect(Function.prototype.constructor).toBe(Function);
        expect(Object.prototype.constructor).toBe(Object);
    });

    it("真正实现原型链的是__proto__属性，而不是prototype", function () {
        //  这说明什么问题呢，执行dog.price的时候，发现没有price这个属性，虽然prototype指向的animal有这个属性，
        // 但它并没有去沿着这个“链”去寻找。同样，执行tidy.price的时候，也没有这个属性，但是__proto__指向了animal，
        // 它会沿着这个链去寻找，animal中有price属性，所以tidy.price输出2000。由此得出，原型链的真正形成是靠的__proro__，而不是prototype。
        //因此，如果在这样指定dog.__proto__ = animal。那dog.price = 2000。

        var tidy = new dog();
        expect(dog.price).toBe(undefined);
        expect(tidy.price).toBe(2000);
    });

    it("其他", function () {
        //Object是函数对象，是通过new Function()创建的，所以Object.__proto__指向Function.prototype.
        expect(Object.__proto__).toBe(Function.prototype);

        //Function也是函数对象，也是通过new Function()创建的，所以同上。
        expect(Function.__proto__).toBe(Function.prototype);

        //自己是由自己创建的，好像不符合逻辑，但仔细想想，现实世界也有些类似，你是怎么来的，你妈生的，你妈怎么来的，你姥姥生的，
        // ……类人猿进化来的，那类人猿从哪来，一直追溯下去……，就是无，（NULL生万物）
        //正如《道德经》里所说“无，名天地之始”。

        //其实这一点我也有点困惑，不过也可以试着解释一下。
        //Function.prototype是个函数对象，理论上他的__proto__应该指向 Function.prototype，就是他自己，自己指向自己，没有意义。
        //JS一直强调万物皆对象，函数对象也是对象，给他认个祖宗，指向Object.prototype。Object.prototype.__proto__ === null，保证原型链能够正常结束。
        expect(Function.prototype.__proto__).toBe(Object.prototype);



    });


});











