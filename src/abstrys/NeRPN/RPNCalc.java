//  NeRPN: A minimalistic RPN Calculator in Java.
//  Copyright (C) 2007 Eron J. Hennessey
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
package abstrys.NeRPN;

import java.math.*;
import java.util.Stack;
import java.util.HashMap;

// ============================================================================
// RPNCalc.java
//
// Part of the Java RPN Calculator
// Written by Eron Hennessey
//
// Copyright (C) 2006, Witchspire
// ============================================================================
/**
 * A class that provides a complete RPN calculator
 * @author Eron Hennessey
 */
public class RPNCalc
{

    public enum RPNMode
    {

        RAD, DEG
    };

    public enum DispMode
    {

        STD, FIX, SCI, ENG
    };

    public enum Ops
    {

        ABS, // calculates |x|
        ACOS, // calculates acos(x)
        ADD, // calculates y+x
        ASIN, // calculates asin(x)
        ATAN, // calculates atan(x)
        CBRT, // calculates cbrt(x)
        CEIL, // calculates ceil(x)
        CLEAR, // clears stack
        COS, // calculates cos(x)
        COSH, // calculates cosh(x)
        DEG, // convert x to degrees
        DELETE, // discards last element
        DIV, // calculates y/x
        DUP, // duplicates the last element
        E, // calculates e^x
        EN1, // calculates e^(1/x)
        ENG, // selects engineering display mode
        EXP, // calculates 10^x
        EXPN1, // calculates 10^(1/x)
        FACT, // calculates x!
        FIX, // selects fixed-point display mode
        FLOOR, // calculates floor(x)
        HYP, // calculates hypot(x,y)
        INV, // calculates 1/x
        LN, // calculates ln(x);
        LOG, // calculates log(x);
        MAX, // calculates max(x)
        MIN, // calculates min(x)
        MOD, // calculates y%x
        MULT, // calculates y*x
        NEG, // calculates -x
        POW, // calculates y^x
        RAD, // convert x to radians
        RAND, // calculates random();
        ROOT, // calculates y^1/x
        ROT, // rotate stack by x
        SCI, // selects scientific display mode
        SIN, // calculates sin(x)
        SINH, // calculates sinh(x)
        SQRT, // calculates sqrt(x)
        STD, // selects standard display mode
        SUBT, // calculates y-x
        SWAP, // swap x and y
        TAN, // calculates tan(x)
        TANH,   // calculates tanh(x)
    }
    private final String ERR_NO_STACK = "Too few elements on stack!";
    private final String ERR_OVERFLOW = "Numeric overflow.";
    private final String ERR_UNKNOWN_OP = "Unknown operation.";
    private final String ERR_NEED_INT = "Last element must be an integer for this operation!";
    private final String ERR_NOT_IMPL = "Operation not implemented!";
    private final String ERR_DIV_BY_ZERO = "Division by zero.";
    private final String ERR_UNKNOWN_ERR = "Unknown Error.";
    private Stack<BigDecimal> rpnstack;
    private HashMap<String, Ops> cmdmap;
    private HashMap<String, BigDecimal> constmap;
    private DispMode disp_mode;
    private int disp_prec; // the display precision.

    /**
     * Default Constructor
     */
    public RPNCalc()
    {
        rpnstack = new Stack<BigDecimal>();

        cmdmap = new HashMap<String, Ops>();
        cmdmap.put("!", Ops.FACT);
        cmdmap.put("%", Ops.MOD);
        cmdmap.put("*", Ops.MULT);
        cmdmap.put("+", Ops.ADD);
        cmdmap.put("-", Ops.SUBT);
        cmdmap.put("/", Ops.DIV);
        cmdmap.put("^", Ops.POW);
        cmdmap.put("abs", Ops.ABS);
        cmdmap.put("acos", Ops.ACOS);
        cmdmap.put("asin", Ops.ASIN);
        cmdmap.put("atan", Ops.ATAN);
        cmdmap.put("c", Ops.CLEAR);
        cmdmap.put("cbrt", Ops.CBRT);
        cmdmap.put("ceil", Ops.CEIL);
        cmdmap.put("cos", Ops.COS);
        cmdmap.put("cosh", Ops.COSH);
        cmdmap.put("deg", Ops.DEG);
        cmdmap.put("del", Ops.DELETE);
        cmdmap.put("dup", Ops.DUP);
        cmdmap.put("e", Ops.E);
        cmdmap.put("en1", Ops.EN1);
        cmdmap.put("eng", Ops.ENG);
        cmdmap.put("exp", Ops.EXP);
        cmdmap.put("expn1", Ops.EXPN1);
        cmdmap.put("fix", Ops.FIX);
        cmdmap.put("floor", Ops.FLOOR);
        cmdmap.put("hyp", Ops.HYP);
        cmdmap.put("inv", Ops.INV);
        cmdmap.put("ln", Ops.LN);
        cmdmap.put("log", Ops.LOG);
        cmdmap.put("max", Ops.MAX);
        cmdmap.put("min", Ops.MIN);
        cmdmap.put("neg", Ops.NEG);
        cmdmap.put("pow", Ops.POW);
        cmdmap.put("rad", Ops.RAD);
        cmdmap.put("rand", Ops.RAND);
        cmdmap.put("root", Ops.ROOT);
        cmdmap.put("rot", Ops.ROT);
        cmdmap.put("sci", Ops.SCI);
        cmdmap.put("sin", Ops.SIN);
        cmdmap.put("sinh", Ops.SINH);
        cmdmap.put("sqrt", Ops.SQRT);
        cmdmap.put("std", Ops.STD);
        cmdmap.put("swap", Ops.SWAP);
        cmdmap.put("tan", Ops.TAN);
        cmdmap.put("tanh", Ops.TANH);

        constmap = new HashMap<String, BigDecimal>();
        constmap.put("E", new BigDecimal(Math.E));
        constmap.put("PI", new BigDecimal(Math.PI));

        disp_mode = DispMode.STD;
    }

    /**
     * Executes an operation on the stack.
     * @param op the operation to perform
     * @return true if the operation affects the stack; false otherwise.
     */
    public boolean doOperation(Ops op) throws ArithmeticException
    {
        switch (op)
        {
        case ABS:
            requireStack(1);
            push(pop().abs());
            return true;

        case ACOS:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.acos(dvalue)));
        }
        return true;

        case ADD:
            requireStack(2);
            push(pop().add(pop()));
            return true;

        case ASIN:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.asin(dvalue)));
        }
        return true;

        case ATAN:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.atan(dvalue)));
        }
        return true;

        case CBRT:
            requireStack(1);
        {
            double dvaluex = pop().doubleValue();
            push(new BigDecimal(Math.cbrt(dvaluex)));
        }
        return true;

        case CEIL:
            requireStack(1);
            push(pop().setScale(0, RoundingMode.CEILING));
            return true;

        case CLEAR:
            requireStack(1);
            rpnstack.removeAllElements();
            return true;

        case COS:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.cos(dvalue)));
        }
        return true;

        case COSH:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.cosh(dvalue)));
        }
        return true;

        case DEG:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.toDegrees(dvalue)));
        }
        return true;

        case DELETE:
            if (rpnstack.size() >= 1)
            {
                rpnstack.pop();
                return true;
            }
            return false;

        case DIV:
            requireStack(2);
        {
            BigDecimal x = pop();
            if (x.equals(BigDecimal.ZERO))
            {
                throw new ArithmeticException(ERR_DIV_BY_ZERO);
            }
            push(pop().divide(x, MathContext.DECIMAL128));
        }
        return true;

        case DUP:
            requireStack(1);
            push(new BigDecimal(peek().toString()));
            return true;

        case E:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.exp(dvalue)));
        }
        return true;

        case EN1:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.expm1(dvalue)));
        }
        return true;

        case ENG:
            disp_mode = DispMode.ENG;
            return true;

        case EXP:
            requireStack(1);
        {
            push(new BigDecimal(10));
            swap(1);
            doOperation(Ops.POW);
        }
        return true;

        case EXPN1:
            requireStack(1);
        {
            push(new BigDecimal(10));
            doOperation(Ops.INV);
            swap(1);
            doOperation(Ops.POW);
        }
        return true;

        case FACT:
            requireStack(1);
            requireInteger();
        {
            BigDecimal x = pop();
            long i = x.intValueExact() - 1;
            while (i > 0)
            {
                x = x.multiply(new BigDecimal(i--));
            }
            push(x);
        }
        return true;

        case FIX:
            disp_mode = DispMode.FIX;
            return true;

        case FLOOR:
            requireStack(1);
            push(pop().setScale(0, RoundingMode.FLOOR));
            return true;

        case HYP:
            requireStack(2);
        {
            double dvaluex = pop().doubleValue();
            double dvaluey = pop().doubleValue();
            push(new BigDecimal(Math.hypot(dvaluex, dvaluey)));
        }
        return true;

        case INV:
            requireStack(1);
            push(new BigDecimal(1));
            swap(1);
            doOperation(Ops.DIV);
            return true;

        case LN:
            requireStack(1);
        {
            double dvaluex = pop().doubleValue();
            push(new BigDecimal(Math.log(dvaluex)));
        }
        return true;

        case LOG:
            requireStack(1);
        {
            double dvaluex = pop().doubleValue();
            push(new BigDecimal(Math.log10(dvaluex)));
        }
        return true;

        case MAX:
            requireStack(2);
            push(pop().max(pop()));
            return true;

        case MIN:
            requireStack(2);
            push(pop().min(pop()));
            return true;

        case MOD:
            requireStack(2);
            push(pop().remainder(pop()));
            return true;

        case MULT:
            requireStack(2);
            push(pop().multiply(pop()));
            return true;

        case NEG:
            requireStack(1);
            push(pop().negate());
            return true;

        case POW:
            requireStack(2);
            // if the x value is an integer, use the BigDecimal.pow()
            // function, otherwise use Math.pow for non-integer x.
            if (peekInteger())
            {
                int ivaluex = pop().intValueExact();
                push(pop().pow(ivaluex));
            }
            else
            {
                double dvaluex = pop().doubleValue();
                double dvaluey = pop().doubleValue();
                push(new BigDecimal(Math.pow(dvaluey, dvaluex)));
            }
            return true;

        case RAD:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.toRadians(dvalue)));
        }
        return true;

        case RAND:
            push(new BigDecimal(Math.random()));
            return true;

        case ROOT:
            requireStack(2);
            doOperation(Ops.INV); // x -> 1/x
            doOperation(Ops.POW); // y ^ 1/x
            return true;

        case ROT:
            // TODO: make this rot(x) instead of rot3.
            // pop the third number off the stack, then push it back on.
            requireStack(3);
            // the value of interest is the third from the end of the stack.
            BigDecimal value = rpnstack.remove(rpnstack.size() - 3);
            push(value);
            return true;

        case SCI:
            disp_mode = DispMode.SCI;
            return true;

        case SIN:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.sin(dvalue)));
        }
        return true;

        case SINH:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.sinh(dvalue)));
        }
        return true;

        case SQRT:
            requireStack(1);
        {
            double dvaluex = pop().doubleValue();
            push(new BigDecimal(Math.sqrt(dvaluex)));
        }
        return true;

        case STD:
            disp_mode = DispMode.STD;
            return true;

        case SUBT:
            requireStack(2);
        {
            BigDecimal x = pop();
            push(pop().subtract(x));
        }
        return true;

        case SWAP:
            // pop two numbers off the stack, then put them back on in the
            // reverse order.
            requireStack(2);
            swap(1);
            return true;

        case TAN:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.tan(dvalue)));
        }
        return true;

        case TANH:
            requireStack(1);
        {
            double dvalue = pop().doubleValue();
            push(new BigDecimal(Math.tanh(dvalue)));
        }
        return true;

        default:
            throw new ArithmeticException(ERR_UNKNOWN_OP);
        }
    }

    // ------------------------------------------------------------------------
    // STACK MANIPULATION OPERATIONS
    // ------------------------------------------------------------------------
    /**
     * Swaps the top element with the element at the position idx.
     */
    public void swap(int idx) throws ArithmeticException
    {
        requireStack(idx);

        // can't swap an item with itself!
        if (idx == 0)
        {
            return;
        }

        BigDecimal nv = rpnstack.remove(idx);
        BigDecimal xv = rpnstack.pop();

        rpnstack.push(nv);
        rpnstack.insertElementAt(xv, idx);
    }

    public void push(BigDecimal val)
    {
        rpnstack.push(val);
    }

    public void push(long val)
    {
        rpnstack.push(new BigDecimal(val));
    }

    public void push(double val)
    {
        rpnstack.push(new BigDecimal(val));
    }

    public void push(String val) throws NumberFormatException
    {
        // first check to see if this is a text operation or
        // constant value.
        if (pushTextOp(val) || pushConstant(val))
        {
            return;
        }

        // if neither, try interpreting this as a number.
        rpnstack.push(new BigDecimal(val));
    }

    public boolean pushTextOp(String val)
    {
        Ops op = cmdmap.get(val);
        if (op != null)
        {
            doOperation(op);
            return true;
        }
        return false;
    }

    public boolean pushConstant(String val)
    {
        BigDecimal con = constmap.get(val);
        if (con != null)
        {
            push(con);
            return true;
        }
        return false;
    }

    /**
     * Pop the most recent element off the stack.
     * @return the value of the most recent element.
     */
    public BigDecimal pop()
    {
        return rpnstack.pop();
    }

    public BigDecimal peek()
    {
        return rpnstack.peek();
    }

    // ------------------------------------------------------------------------
    // ACCESS METHODS
    // ------------------------------------------------------------------------
    /**
     * Get the number of elements on the stack.
     * @return the number of elements on the stack.
     */
    public int getStackHeight()
    {
        return rpnstack.size();
    }

    /**
     * Get the value of a particular stack entry.
     * @return A string with the value of the given stack entry.
     */
    public String getStackEntry(int i)
    {
        if (i >= rpnstack.size())
        {
            return null;
        }

        BigDecimal val = rpnstack.elementAt(i).round(MathContext.DECIMAL64);
        int scale = val.scale();
        int digits = val.precision();

        switch (disp_mode)
        {
        case STD:
            return val.toString();

        case FIX:
            return val.toPlainString();

        case SCI:
            return val.setScale(-1, RoundingMode.HALF_EVEN).toString();

        case ENG:
            return val.toEngineeringString();

        default:
            return null;
        }
    }

    public Stack getStack()
    {
        return rpnstack;
    }

    /**
     * Returns the stack as a string, one entry per line
     */
    @Override
    public String toString()
    {
        String output = "";
        // print the stack, from the last element to the first
        for (int idx = 0; idx < rpnstack.size(); idx++)
        {
            output += new String((rpnstack.size() - idx) + ": " + rpnstack.elementAt(idx) + "\n");
        }

        return output;
    }

    private void requireStack(int i) throws ArithmeticException
    {
        if (rpnstack.size() < i)
        {
            throw new ArithmeticException(ERR_NO_STACK);
        }
    }

    private void requireInteger() throws ArithmeticException
    {
        if (peek().scale() > 0)
        {
            throw new ArithmeticException(ERR_NEED_INT);
        }
    }

    private boolean peekInteger()
    {
        return (peek().scale() <= 0);
    }
}

