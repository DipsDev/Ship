package parser.nodes;

public enum LiteralKind {

    INT("int"),
    STRING("string");

    public final String name;
    LiteralKind(String name) {
        this.name = name;
    }

    public String asString() {
        return name;
    }
}
