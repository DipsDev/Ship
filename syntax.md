# Ship Syntax Document

### Variables:
`type variable_name = value;`

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
fn function_name ~ type parameter_name -> int { ... }
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









