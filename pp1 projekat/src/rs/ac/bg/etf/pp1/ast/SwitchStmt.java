// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class SwitchStmt extends Matched {

    private SwitchHelp SwitchHelp;
    private Expr Expr;
    private CaseList CaseList;
    private Rbrace Rbrace;

    public SwitchStmt (SwitchHelp SwitchHelp, Expr Expr, CaseList CaseList, Rbrace Rbrace) {
        this.SwitchHelp=SwitchHelp;
        if(SwitchHelp!=null) SwitchHelp.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
        this.Rbrace=Rbrace;
        if(Rbrace!=null) Rbrace.setParent(this);
    }

    public SwitchHelp getSwitchHelp() {
        return SwitchHelp;
    }

    public void setSwitchHelp(SwitchHelp SwitchHelp) {
        this.SwitchHelp=SwitchHelp;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public Rbrace getRbrace() {
        return Rbrace;
    }

    public void setRbrace(Rbrace Rbrace) {
        this.Rbrace=Rbrace;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchHelp!=null) SwitchHelp.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(CaseList!=null) CaseList.accept(visitor);
        if(Rbrace!=null) Rbrace.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchHelp!=null) SwitchHelp.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
        if(Rbrace!=null) Rbrace.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchHelp!=null) SwitchHelp.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        if(Rbrace!=null) Rbrace.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwitchStmt(\n");

        if(SwitchHelp!=null)
            buffer.append(SwitchHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Rbrace!=null)
            buffer.append(Rbrace.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwitchStmt]");
        return buffer.toString();
    }
}
