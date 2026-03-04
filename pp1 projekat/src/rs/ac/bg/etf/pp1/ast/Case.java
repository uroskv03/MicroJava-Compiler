// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class Case implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private CaseHelp CaseHelp;
    private Number1 Number1;
    private Colon Colon;
    private StatementListHelp StatementListHelp;

    public Case (CaseHelp CaseHelp, Number1 Number1, Colon Colon, StatementListHelp StatementListHelp) {
        this.CaseHelp=CaseHelp;
        if(CaseHelp!=null) CaseHelp.setParent(this);
        this.Number1=Number1;
        if(Number1!=null) Number1.setParent(this);
        this.Colon=Colon;
        if(Colon!=null) Colon.setParent(this);
        this.StatementListHelp=StatementListHelp;
        if(StatementListHelp!=null) StatementListHelp.setParent(this);
    }

    public CaseHelp getCaseHelp() {
        return CaseHelp;
    }

    public void setCaseHelp(CaseHelp CaseHelp) {
        this.CaseHelp=CaseHelp;
    }

    public Number1 getNumber1() {
        return Number1;
    }

    public void setNumber1(Number1 Number1) {
        this.Number1=Number1;
    }

    public Colon getColon() {
        return Colon;
    }

    public void setColon(Colon Colon) {
        this.Colon=Colon;
    }

    public StatementListHelp getStatementListHelp() {
        return StatementListHelp;
    }

    public void setStatementListHelp(StatementListHelp StatementListHelp) {
        this.StatementListHelp=StatementListHelp;
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
        if(CaseHelp!=null) CaseHelp.accept(visitor);
        if(Number1!=null) Number1.accept(visitor);
        if(Colon!=null) Colon.accept(visitor);
        if(StatementListHelp!=null) StatementListHelp.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseHelp!=null) CaseHelp.traverseTopDown(visitor);
        if(Number1!=null) Number1.traverseTopDown(visitor);
        if(Colon!=null) Colon.traverseTopDown(visitor);
        if(StatementListHelp!=null) StatementListHelp.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseHelp!=null) CaseHelp.traverseBottomUp(visitor);
        if(Number1!=null) Number1.traverseBottomUp(visitor);
        if(Colon!=null) Colon.traverseBottomUp(visitor);
        if(StatementListHelp!=null) StatementListHelp.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Case(\n");

        if(CaseHelp!=null)
            buffer.append(CaseHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Number1!=null)
            buffer.append(Number1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Colon!=null)
            buffer.append(Colon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementListHelp!=null)
            buffer.append(StatementListHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Case]");
        return buffer.toString();
    }
}
