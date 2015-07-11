/**
 * Created by liekkas on 15/7/11.
 */

/**
 * 基于以下文章做测测试
 * http://alistapart.com/article/getoutbindingsituations
 * */

name = "Ray";

var john = {
    name: 'John',
    greet: function(person){
        return "Hi " + person + ", my name is " + this.name;
    }
};

function Person(first,last,age){
    this.first = first;
    this.last = last;
    this.age = age;
}

Person.prototype  = {
    getFullName: function(){
        return this.first + ' ' + this.last;
    },
    greet: function(other){
        return "Hi " + other.first + ", my name is " + this.first;
    }
}

function createBoundedWrapper(object,method){
    return function(){
        return method.apply(object,arguments);
    };
}