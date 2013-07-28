# NeRPN: A Minimalist RPN Calculator

**NeRPN** is a simple Reverse-Polish Notation (RPN) calculator, designed with a minimalistic interface, but with many of
the same features as more complicated calculator software. It's designed for those users who want a simple, quick
calculator that's easy to use and won't clutter their desktop, and who enjoy using RPN.

If you are not familiar with RPN, you may wish to read [Understanding Reverse Polish Notation][] before proceeding with the
rest of this guide.

**Topics:**

* [Using NeRPN]
* [NeRPN Reference]
* [Understanding Reverse Polish Notation]

## Using NeRPN

### Prerequisites and Installation

NeRPN is a Java application, and is designed to run on any platform that supports the Java 1.6 Virtual Machine (VM) or better.

### Starting the Application

Starting NeRPN in an environment that contains Java Jar support is simply a matter of clicking (or double-clicking) the
NeRPN Jar-file.

From the command-line, NeRPN may be started using the following command:

    java -jar /path/to/nerpn/NeRPN.jar

where <parameter>/path/to/nerpn</parameter> represents the path to the jar-file, and is operating-system dependent.

### The User Interface

Once the program is running, you will be presented with NeRPN's user interface, which consists of a stack view, and
input box, a back (**&lt;**) button, and an **Enter** button.

      <figure>
        <title>NeRPN's User Interface</title>

        <screenshot>
          <screeninfo></screeninfo>

          <mediaobject>
            <imageobject>
              <imagedata fileref="Screenshot.png" />
            </imageobject>
          </mediaobject>
        </screenshot>
      </figure>
    </section>

### Entering and Removing Values and Operations on the Stack

**To enter values on the stack:**

* Enter a numeric value in the input box, and click the **Enter** button to enter the value on the stack.

    Alternatively, you can press the **Enter** key on the keyboard intead of clicking the **Enter** button.

    The value will be pushed onto the first position of the stack, designated by the label **x**.

**To remove values from the stack:**

* Enter a numeric value in the input box, and click the **Enter** button, or press the **Enter** key on the keyboard.

### Instant Operations

Some operations take effect immediately when typed. That is, they do not appear in the input box, but affect the stack
the moment the associated key is pressed.

The following operations take effect immediately:

> ! % * + - / ^

## NeRPN Reference

### Supported Operations

The following operations can be entered in NeRPNs input box.

> **Note:** Each operation requires a certain number of elements on the stack to function, or an error will result. For
> each of the operations presented here, the number of elements required is given in the *Values Required* column.

| Command | Values Required | Description |
|:-------:|:---------------:|:------------|
| `!`     | 1               | computes the factorial of *x*
| `%`     | 2               | computes *y* modulus *x*
| `*`     | 2               | multiplies *y* and *x*
| `+`     | 2               | adds *y* and *x*
| `-`     | 2               | subtracts *x* from *y*
| `/`     | 2               | divides *y* by *x*
| `^`     | 2               | raises *y* to *x*'s power
| `abs`   | 1               | returns the absolute value of *x*
| `acos`  | 1               | computes the arccosine of *x*
| `asin`  | 1               | computes the arcsine of *x*
| `atan`  | 1               | computes the arctangent of *x*
| `c`     | 0               | clears all entries from the stack
| `cbrt`  | 1               | computes the cube root of *x*
| `ceil`  | 1               | returns the ceiling of (the next whole number above) *x*
| `cos`   | 1               | computes the cosine of *x*
| `cosh`  | 1               | computes the hyperbolic cosine of *x*
| `deg`   | 1               | converts *x*, assumed to be in radians, to degrees
| `del`   | 1               | deletes *x* from the stack
| `dup`   | 1               | duplicates the value of *x* and adds it to the stack
| `e`     | 1               | raises the constant *e* to the power of *x*
| `en1`   | 1               | raises the constant *e* to the power of *1/x*
| `eng`   | 0               | displays results in engineering notation.
| `exp`   | 1               | raises 10 to the power of *x*
| `expn1` | 1               | raises 10 to the power of *1/x*
| `fix`   | 0               | displays results in fixed-point notation.
| `floor` | 1               | returns the floor of *x*.
| `hyp`   | 2               | computes the hypoteneuse (sqrt(*x*<sup>2</sup> + *y*<sup>2</sup>)) of *y* and *x*.
| `inv`   | 1               | computes the inverse of (the next whole number below) *x*
| `ln`    | 1               | computes the natural (*e*-based) log of *x*
| `log`   | 1               | computes the 10-based log of *x*
| `max`   | 2               | returns the maximum (greatest) of *y* and *x*
| `min`   | 2               | returns the minimum (lesser) of *y* and *x*
| `neg`   | 1               | returns the negative of *x*: if *x* is negative, it is made positive. If *x* is positive, it is made negative.
| `pow`   | 2               | returns *y* to *x*'s power
| `rad`   | 1               | converts *x*, assumed to be in degrees, to radians.
| `rand`  | 0               | pushes a random number between 0 and 1 on the stack
| `root`  | 2               | computes the *x* root of *y*.
| `rot`   | 3               | rotates the first 3 elements on the stack: bringing *z* to *x*'s position, while pushing *y* to *z*'s position and *x* to *y*'s position.
| `sci`   | 0               | displays results in scientific notation
| `sin`   | 1               | computes the sine of *x*
| `sinh`  | 1               | computes the hyperbolic sine of *x*
| `sqrt`  | 1               | computes the square root of *x*
| `std`   | 0               | displays results in standard notation
| `swap`  | 2               | swaps the positions of *x* and *y*
| `tan`   | 1               | computes the tangent of *x*
| `tanh`  | 1               | computes the hyperbolic tangent of *x*

## Understanding Reverse Polish Notation

Reverse Polish Notation is also referred to as *postfix* notation, meaning that the operator follows the values being
used in the calculation. For instance, many children are taught to add in the following way:

> "3 plus 5 equals 8"

which is usually written as:

> 3 + 5 =

With a result of 8.

In RPN Notation, this will be written with the add operator, `+`, following the numbers. For example:

> 3 5 +

The result, as in the previous example, will be 8.

> **Note**: there is no equals (=) sign here. In RPN notation, such devices are unnecessary, since operators take effect
immediately.

RPN calculators frequently make use of a *stack* to store values. A stack, like its name implies, works like a stack of
playing cards, books, or whatever else you might imagine that can be stacked. You can add cards (values) on the top of
a stack, or take them off the top. They will always come off in the opposite order in which they were put on. This way
of handling values is also commonly called Last-in, First-out, or LIFO.

When used in conjunction with a stack, there is no need for another common device used in calculation: parentheses. Take
the following calculation, for instance.

> 8 * (3 + 5)

Without the parentheses in this notation, 8 and 3 would be multiplied first, resulting in an incorrect answer. In RPN
notation, and making use of a stack to store values, the calculation would work as follows:

> 8 3 5 + *

meaning that 8, 3, and 5 would be added to the stack in order. Remember that a stack is Last-in, First-out. When an
operator is used, the values will come off the stack in the opposite order as they were added. This causes 5 to be added
to 3, and then multiplied by 8. Exactly what we wanted!

In this example, 3 and 5 could have been placed before 8 to eliminate the need for parentheses, such as:

> 3 + 5 * 8

Yet, this is still confusing, and requires the user to state an order of operations – are the values calculated
left-to-right, or do some values “bind” more tightly than others? Many programming language parsers, for instance, will
still interpret this as 5 multiplied by 8, and then added to 3, since the multiplication operator binds more tightly
than the addition operator.

With RPN and a stack, there is no question as to what is meant, and it is never necessary to use parentheses, or to
specify an order of operations.

Here's another example. One in which parentheses are required by traditional notation.

> 3 / (5 / 8)

This could not be written as 5 / 8 / 3, nor as 3 / 5 / 8. Both would give incorrect answers.

With RPN, you'll write:

> 3 5 8 / /

There is no need for parentheses: the calculator will first divide 5 by 8, and then divide 3 by the result of that
calculation, resulting in a correct answer.


