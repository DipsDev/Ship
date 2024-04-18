# Ship Syntax Document

### Variables:
`(var | const) (name): [type] = (value);`

Although variables are strictly typed,\
the interpreter can infer the type from the value.\
\
In order to modify a variable, use this syntax.\
`(name) = (value)`

Note that the interpreter will throw an error if the types don't match.
\
\
Available types are below:

| Type    | Name   |
|---------|--------|
| Integer | int    |
| Double  | double |
| String  | string |


### Functions:
`
(return type) (name)([a]: (type), [b]: (type)) { ... }
`



### If statements:

`if (boolean condition) { ... }`\
`else if (boolean condition) { ... }`\
`else { ... }`


### Loops:
usual syntax;

`for (i: int = 0; i<6; i++) {
    ...
}`









