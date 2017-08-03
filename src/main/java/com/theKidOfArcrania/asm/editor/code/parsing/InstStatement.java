package com.theKidOfArcrania.asm.editor.code.parsing;

import com.theKidOfArcrania.asm.editor.code.parsing.inst.InstOpcodes;
import com.theKidOfArcrania.asm.editor.code.parsing.inst.InstSpec;
import org.objectweb.asm.MethodVisitor;

import static com.theKidOfArcrania.asm.editor.code.parsing.Range.tokenRange;

/**
 * This statement represents a single instruction.
 * @author Henry Wang
 */
public class InstStatement extends CodeStatement
{
    private final CodeTokenReader reader;
    private final ErrorLogger delegateLogger;

    private final InstSpec spec;
    private final int opcode;
    private final Argument[] args;

    /**
     * Reads in an instruction statement and parses it. The associated token-reader should be primed to the first token
     * (the instruction word) of that line. If an error occurred while parsing this instruction, this will return null.
     * @param reader the token reader.
     * @return the instruction parsed, or null.
     */
    public static InstStatement parseStatement(CodeTokenReader reader)
    {
        String instName = reader.getToken();
        InstOpcodes opcode = InstOpcodes.fetchOpcode(instName);
        if (opcode == null)
        {
            reader.error("Invalid instruction name.", tokenRange(reader.getLineNumber(), 0, reader.getLine().length()));
            return null;
        }

        InstSpec instSpec = opcode.getInstSpec();
        Argument[] args = instSpec.parseInstArgs(reader);
        if (args == null)
            return null;

        int end = reader.getTokenEndIndex();
        if (reader.nextToken(true))
        {
            Position start = reader.getTokenPos().getStart();
            reader.error("Expected end of statement.", tokenRange(start.getLineNumber(), end, reader.getLine()
                    .length()));
            return null;
        }

        InstStatement inst = new InstStatement(reader, instSpec, opcode.getOpcode(), args);
        if (!instSpec.verifyParse(inst.delegateLogger, inst))
            return null;
        return inst;
    }

    /**
     * Constructs a new instruction.
     * @param reader reader associated with instruction.
     * @param spec the instruction specification that this instruction derives from
     * @param opcode the opcode of this instruction
     * @param args the list of arguments.
     */
    private InstStatement(CodeTokenReader reader, InstSpec spec, int opcode, Argument[] args)
    {
        this.reader = reader;
        this.spec = spec;
        this.opcode = opcode;
        this.args = args.clone();

        this.delegateLogger = new ErrorLogger()
        {
            @Override
            public void logError(String description, Range highlight)
            {
                reader.error(description, highlight);
            }

            @Override
            public void logWarning(String description, Range highlight)
            {
                reader.warning(description, highlight);
            }
        };
    }

    public InstSpec getSpec()
    {
        return spec;
    }

    public int getOpcode()
    {
        return opcode;
    }

    /**
     * Obtains the argument descriptor at the particular index
     * @param ind the index of the argument.
     * @return the argument descriptor.
     */
    public Argument getArg(int ind)
    {
        return args[ind];
    }

    /**
     * Obtains the argument's exact type at the particular index.
     * @param ind the index of the argument.
     * @return the exact parameter type.
     * @see ParamType#getExactType(CodeTokenReader)
     * @see Argument#getExactType()
     */
    public ParamType getArgExactType(int ind)
    {
        return getArg(ind).getExactType();
    }

    /**
     * Obtains the parsed argument at the particular index.
     * @param ind the index of the argument.
     * @return a parsed object of the argument.
     */
    public Object getArgValue(int ind)
    {
        return getArg(ind).getValue();
    }

    /**
     * Obtains the parsed argument at the particular index, ensuring that the object is of a particular type.
     * @param ind the index of the argument.
     * @param type the required type
     * @param <T> generic that this object will automatically be cast to.
     * @return the casted object.
     * @throws IllegalArgumentException if object is not of correct type.
     */
    @SuppressWarnings("unchecked")
    public <T> T getArgValue(int ind, Class<T> type)
    {
        Object o = getArgValue(ind);
        if (!type.isInstance(o))
            throw new IllegalArgumentException();
        return (T)o;
    }

    /**
     * Obtains the parsed argument as am integer value.
     * @param ind the index of the argument
     * @return the integer argument.
     * @throws IllegalArgumentException if the argument cannot be converted into an integer without lossy conversion
     */
    public int getIntArgValue(int ind)
    {
        return getArgValue(ind, Integer.class);
    }

    /**
     * Obtains the token range of the particular argument
     * @param ind the index of the argument
     * @return a valid range that describes the position of the argument token.
     */
    public Range getArgPos(int ind)
    {
        return getArg(ind).getTokenPos();
    }

    /**
     * @return the number of arguments.
     */
    public int getArgSize()
    {
        return args.length;
    }

    @Override
    public boolean resolveSymbols()
    {
        return getSpec().verifySymbol(delegateLogger, this, reader.getResolvedSymbols());
    }

    @Override
    public void write(MethodVisitor writer)
    {
        getSpec().write(writer, this, reader.getResolvedSymbols());
    }

    /**
     * {@inheritDoc}
     *
     * This does nothing since there are no side effects while parsing an instruction.
     */
    @Override
    public void reset()
    {
        //Does nothing because there are no side-effects.
    }
}
