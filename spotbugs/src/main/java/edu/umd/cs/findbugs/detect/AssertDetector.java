package edu.umd.cs.findbugs.detect;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.OpcodeStack;
import edu.umd.cs.findbugs.OpcodeStack.Item;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.ba.XMethod;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * This detector can find constructors that throw exception.
 */
public abstract class AssertDetector extends OpcodeStackDetector {

    private final BugReporter bugReporter;

    protected boolean inAssert = false;

    public AssertDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }
    
    abstract void detect(int seen);

    /**
     * If the class is final, we are fine, no finalizer attack can happen.
     */
    @Override
    public void sawOpcode(int seen) {
        if (inAssert) {
            detect(seen);
        }
        if (seen == Const.GETSTATIC && "$assertionsDisabled".equals(getNameConstantOperand())) {
            inAssert = true;
        }
        if (seen == Const.INVOKESPECIAL && getClassConstantOperand().equals("java/lang/AssertionError")) {
            resetState();
        }
    }

    protected void resetState() {
        inAssert = false;
    }

    protected void reportBug(BugInstance bug) {
        bugReporter.reportBug(bug);
    }
}
