// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class ForDesignatorStmt implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private DesignatorStmt DesignatorStmt;

    public ForDesignatorStmt (DesignatorStmt DesignatorStmt) {
        this.DesignatorStmt=DesignatorStmt;
        if(DesignatorStmt!=null) DesignatorStmt.setParent(this);
    }

    public DesignatorStmt getDesignatorStmt() {
        return DesignatorStmt;
    }

    public void setDesignatorStmt(DesignatorStmt DesignatorStmt) {
        this.DesignatorStmt=DesignatorStmt;
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
        if(DesignatorStmt!=null) DesignatorStmt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStmt!=null) DesignatorStmt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStmt!=null) DesignatorStmt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDesignatorStmt(\n");

        if(DesignatorStmt!=null)
            buffer.append(DesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForDesignatorStmt]");
        return buffer.toString();
    }
}
