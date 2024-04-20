package parser;

public enum LiteralKind {

    INT(BaseKind.NUMBER),
    STRING(BaseKind.ARRAY),
    NULL(BaseKind.NONE),
    BOOLEAN(BaseKind.NONE),
    FLOAT(BaseKind.NUMBER);

    private final BaseKind base;
    LiteralKind(BaseKind base) {
        this.base = base;
    }

    public BaseKind getBase() {
        return base;
    }
}
