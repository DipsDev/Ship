
# Ship the programming language
A new version of Dubb, which features new functionalities.\
written purely in java

## Language features

### Variables
```javascript
const favorite_number = 49; // this is a constant variable
let age = 17; // this is not!
```
Supported variable types
```javascript
const string = "Hello World!"; // strings
const integer = 16; // integers
const double = 14.5634; // doubles
const arr = [10 40 14.5 0.4 "Hello World" "Geva"]; // even arrays!
```

### Functions
Ship creates a function using the `fn` keyword\
recursive functions are available too
```javascript
fn max(a, b) {
    if a > b { 
        return a;
    }
    return b;
}
```

#### Global Functions
There are some global functions, that come as default with Ship.

`puts(obj)`\
prints out obj to the screen
```javascript
puts("Hello World") -> Hello World
puts(4) -> 4
puts([3 2 1]) -> [3, 2, 1]
```
\
`get(obj, index)`\
returns the element in `index`
```javascript
get("Hello World", 0) -> "H"
get([1 2 3], 1) -> 2
```
\
`size(obj)`\
returns the length of an object
```javascript
size("Hello") -> 5
size([1 2 3 4 5 6]) -> 6
```
\
`time()`\
returns the time in nanoseconds
```javascript
const a = time()
// some function
const b = time()
const time_elapsed = b - a;
```
\
`paste(object1, object2)`\
returns a concatenated string version of the objects
```javascript
paste("Hello ", "World") -> "Hello World"
paste("My age is ", age) -> "My age is 17"
```

### If Statements
```javascript
if a > 3 {
    puts("a is larger than 3");
}
```


### Expressions
Ship supports all kind of expressions

#### Boolean Expressions
```javascript
const a = 4 > 3 -> true
const b = 5 == 5 -> true
const c = 4 < 1 -> false
```

#### Binary Expressions
```javascript
const a = 4 * 5 + 3
const b = 4 - 10 / 2
const c = 10 % 2
```

## Running the program
In order to run a ship program, you need to do a couple of steps.\
 - either build the project, or download the jar right away.
 - create a new file with the .sp extension
 - execute the jar with the new file path (java -jar ship.jar \<path\>)



## Roadmap
- Loops - while, for, foreach

