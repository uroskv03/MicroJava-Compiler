// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class PrintStmt extends Matched {

    private Printer Printer;

    public PrintStmt (Printer Printer) {
        this.Printer=Printer;
        if(Printer!=null) Printer.setParent(this);
    }

    public Printer getPrinter() {
        return Printer;
    }

    public void setPrinter(Printer Printer) {
        this.Printer=Printer;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Printer!=null) Printer.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Printer!=null) Printer.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Printer!=null) Printer.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStmt(\n");

        if(Printer!=null)
            buffer.append(Printer.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStmt]");
        return buffer.toString();
    }
}
