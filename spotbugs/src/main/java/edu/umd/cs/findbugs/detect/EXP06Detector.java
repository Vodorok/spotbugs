
package edu.umd.cs.findbugs.detect;

import org.apache.bcel.Const;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.classfile.Global;

import edu.umd.cs.findbugs.detect.FindNoSideEffectMethods.MethodSideEffectStatus;
import edu.umd.cs.findbugs.detect.FindNoSideEffectMethods.NoSideEffectMethodsDatabase;

import java.util.List;
import java.util.ArrayList;

/**
 * This detector can find assertions that violates the EXP06 rule.
 */
public class EXP06Detector extends AbstractAssertDetector {
    //private static final boolean CACHE = SystemProperties.getBoolean("da.se.usecache");

    private static final List<String> SideEffectList;
    static {
        SideEffectList = new ArrayList<String>();
        SideEffectList.add("add");
        SideEffectList.add("addAll");
        SideEffectList.add("remove");
        SideEffectList.add("removeAll");
        SideEffectList.add("removeElement"); // Vector
        SideEffectList.add("retainAll"); // Vector
        SideEffectList.add("offer"); // Queue
        SideEffectList.add("offerFirst"); // Dequeue
        SideEffectList.add("offerLast"); // Dequeue
        SideEffectList.add("removeFirstOccurence"); // Dequeue
        SideEffectList.add("removeLastOccurence"); // Dequeue
        SideEffectList.add("addIfAbsent"); // CopyOnWriteArraylist
    }

    private final NoSideEffectMethodsDatabase noSideEffectMethods;

    public EXP06Detector(BugReporter bugReporter) {
        super(bugReporter);
        this.noSideEffectMethods = Global.getAnalysisCache().getDatabase(NoSideEffectMethodsDatabase.class);
    }

    private boolean isSideEffectMethod(String method) {
        if (SideEffectList.contains(method)) {
            return true;
        }
        //if (CACHE) {
        if (noSideEffectMethods.is(getMethodDescriptorOperand(), /* MethodSideEffectStatus.OBJ,*/ MethodSideEffectStatus.SE)) {
            return true;
        }
        //}
        return false;
    }

    /**
     * Returns true if the opcode is a method invocation false otherwise
     */
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

    /**
     * Returns true if the opcode is a side effect producing instruction
     */
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

    /**
     * Finds assertion which violates the EXP06 rule
     */
    @Override
    protected void detect(int seen) {
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
