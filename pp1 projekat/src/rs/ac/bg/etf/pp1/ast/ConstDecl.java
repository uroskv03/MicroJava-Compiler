// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ConstFirst ConstFirst;
    private ConstNext ConstNext;

    public ConstDecl (Type Type, ConstFirst ConstFirst, ConstNext ConstNext) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstFirst=ConstFirst;
        if(ConstFirst!=null) ConstFirst.setParent(this);
        this.ConstNext=ConstNext;
        if(ConstNext!=null) ConstNext.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstFirst getConstFirst() {
        return ConstFirst;
    }

    public void setConstFirst(ConstFirst ConstFirst) {
        this.ConstFirst=ConstFirst;
    }

    public ConstNext getConstNext() {
        return ConstNext;
    }

    public void setConstNext(ConstNext ConstNext) {
        this.ConstNext=ConstNext;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstFirst!=null) ConstFirst.accept(visitor);
        if(ConstNext!=null) ConstNext.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstFirst!=null) ConstFirst.traverseTopDown(visitor);
        if(ConstNext!=null) ConstNext.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstFirst!=null) ConstFirst.traverseBottomUp(visitor);
        if(ConstNext!=null) ConstNext.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstFirst!=null)
            buffer.append(ConstFirst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstNext!=null)
            buffer.append(ConstNext.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
