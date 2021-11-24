
package edu.umd.cs.findbugs.detect;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.classfile.Global;

import edu.umd.cs.findbugs.detect.FindNoSideEffectMethods.MethodSideEffectStatus;
import edu.umd.cs.findbugs.detect.FindNoSideEffectMethods.NoSideEffectMethodsDatabase;

import java.util.List;
import java.util.ArrayList;

/**
 * This detector can find constructors that throw exception.
 */
public class AssertSideEffect extends AssertDetector {

    private static List<String> SideEffectList;
    static {
        SideEffectList = new ArrayList<String>();
        SideEffectList.add("add");
        SideEffectList.add("addAll");
        SideEffectList.add("remove");
        SideEffectList.add("removeAll");
        SideEffectList.add("removeElement");    // Vector
        SideEffectList.add("retainAll");        // Vector
        SideEffectList.add("offer");            // Queue
        SideEffectList.add("offerFirst");       // Dequeue
        SideEffectList.add("offerLast");        // Dequeue
        SideEffectList.add("removeFirstOccurence");        // Dequeue
        SideEffectList.add("removeLastOccurence");        // Dequeue
        SideEffectList.add("addIfAbsent");        // CopyOnWriteArraylist
    }

    private final NoSideEffectMethodsDatabase noSideEffectMethods;

    public AssertSideEffect(BugReporter bugReporter) {
        super(bugReporter);
        this.noSideEffectMethods = Global.getAnalysisCache().getDatabase(NoSideEffectMethodsDatabase.class);
    }

    /**
     * Visit a class to find the constructor, then collect all the methods that gets called in it.
     * Also we are checking for final declaration on the class, or a final finalizer, as if present
     * no finalizer attack can happen.
     */
    @Override
    public void visit(Method obj) {
        if (!obj.isPublic())
            return;
    }

    @Override
    public void visitClassContext(ClassContext classContext) {
        JavaClass ctx = classContext.getJavaClass();
        // Break out of analyzing this class if not public
        if (!ctx.isPublic())
            return;
        super.visitClassContext(classContext);
    }

    private boolean isSideEffectMethod(String method) {
        if (SideEffectList.contains(method))
            return true;
        if (noSideEffectMethods.is(getMethodDescriptorOperand(), MethodSideEffectStatus.OBJ, MethodSideEffectStatus.SE)) {
            return true;
        }
        return false;
    }

    private boolean isMethodCall(int seen) {
        boolean methodCall = false;
        if (seen == Const.INVOKESTATIC ||
                seen == Const.INVOKEVIRTUAL ||
                seen == Const.INVOKEINTERFACE ||
                seen == Const.INVOKESPECIAL) {
            methodCall = true;
        }
        return methodCall;
    }

    private boolean checkSeen(int seen) {
        switch (seen) {
        case Const.IINC:
        case Const.ISTORE:
        case Const.ISTORE_0:
        case Const.ISTORE_1:
        case Const.ISTORE_2:
        case Const.ISTORE_3:
            return true;
        default:
            return false;
        }
    }

    @Override
    void detect(int seen) {
        if (isMethodCall(seen)) {
            StringBuilder sb = new StringBuilder(getClassConstantOperand());
            sb.append(":");
            sb.append(getNameConstantOperand());
            if (isSideEffectMethod(getNameConstantOperand())) {
                BugInstance bug = new BugInstance(this, "DA_DONT_ASSERT_SIDE_EFFECT_METHOD", NORMAL_PRIORITY)
                        .addClassAndMethod(this)
                        .addSourceLine(this, getPC());
                reportBug(bug);
            }
        } else if (checkSeen(seen)) {
            BugInstance bug = new BugInstance(this, "DA_DONT_ASSERT_SIDE_EFFECT", NORMAL_PRIORITY)
                    .addClassAndMethod(this)
                    .addSourceLine(this, getPC());
            reportBug(bug);
        }
    }
}
