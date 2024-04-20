package parser;

public enum LiteralKind {

    INT(BaseKind.NUMBER),
    STRING(BaseKind.ARRAY),
    ARRAY(BaseKind.ARRAY),
    NULL(BaseKind.NONE),
    VOID(BaseKind.NONE),
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
