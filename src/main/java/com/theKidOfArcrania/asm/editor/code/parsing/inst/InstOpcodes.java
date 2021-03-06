package com.theKidOfArcrania.asm.editor.code.parsing.inst;

import java.util.EnumSet;
import java.util.HashMap;

import static org.objectweb.asm.Opcodes.*;
import static com.theKidOfArcrania.asm.editor.code.parsing.inst.InstSpecs.*;

/**
 * Enumeration containing all the valid instruction opcodes.
 * @author Henry Wang
 */
public enum InstOpcodes
{

    INST_AALOAD(AALOAD, ZERO_OP_INST_SPEC), INST_AASTORE(AASTORE, ZERO_OP_INST_SPEC), INST_ACONST_NULL(ACONST_NULL,
        ZERO_OP_INST_SPEC), INST_ALOAD(ALOAD, VAR_INST_SPEC), INST_ANEWARRAY(ANEWARRAY, TYPE_INST_SPEC),
    INST_ARETURN(ARETURN, ZERO_OP_INST_SPEC), INST_ARRAYLENGTH(ARRAYLENGTH, ZERO_OP_INST_SPEC), INST_ASTORE(ASTORE,
        VAR_INST_SPEC), INST_ATHROW(ATHROW, ZERO_OP_INST_SPEC), INST_BALOAD(BALOAD, ZERO_OP_INST_SPEC),  INST_BASTORE
        (BASTORE, ZERO_OP_INST_SPEC), INST_BIPUSH(BIPUSH, INT_INST_SPEC), INST_CALOAD(CALOAD, ZERO_OP_INST_SPEC),
    INST_CASTORE(CASTORE, ZERO_OP_INST_SPEC), INST_CHECKCAST(CHECKCAST, TYPE_INST_SPEC), INST_D2F(D2F, ZERO_OP_INST_SPEC),
    INST_D2I(D2I, ZERO_OP_INST_SPEC), INST_D2L(D2L, ZERO_OP_INST_SPEC), INST_DADD(DADD, ZERO_OP_INST_SPEC),
    INST_DALOAD(DALOAD, ZERO_OP_INST_SPEC), INST_DASTORE(DASTORE, ZERO_OP_INST_SPEC),
    INST_DCMPG(DCMPG, ZERO_OP_INST_SPEC), INST_DCMPL(DCMPL, ZERO_OP_INST_SPEC), INST_DCONST_0(DCONST_0,
        ZERO_OP_INST_SPEC), INST_DCONST_1(DCONST_1, ZERO_OP_INST_SPEC), INST_DDIV(DDIV, ZERO_OP_INST_SPEC),
    INST_DLOAD(DLOAD, VAR_INST_SPEC), INST_DMUL(DMUL, ZERO_OP_INST_SPEC), INST_DNEG(DNEG, ZERO_OP_INST_SPEC),
    INST_DREM(DREM, ZERO_OP_INST_SPEC), INST_DRETURN(DRETURN, ZERO_OP_INST_SPEC), INST_DSTORE(DSTORE, VAR_INST_SPEC),
    INST_DSUB(DSUB, ZERO_OP_INST_SPEC), INST_DUP(DUP, ZERO_OP_INST_SPEC), INST_DUP_X1(DUP_X1, ZERO_OP_INST_SPEC),
    INST_DUP_X2(DUP_X2, ZERO_OP_INST_SPEC), INST_DUP2(DUP2, ZERO_OP_INST_SPEC), INST_DUP2_X1(DUP2_X1, ZERO_OP_INST_SPEC),
    INST_DUP2_X2(DUP2_X2, ZERO_OP_INST_SPEC), INST_F2D(F2D, ZERO_OP_INST_SPEC), INST_F2I(F2I, ZERO_OP_INST_SPEC),
    INST_F2L(F2L, ZERO_OP_INST_SPEC), INST_FADD(FADD, ZERO_OP_INST_SPEC), INST_FALOAD(FALOAD, ZERO_OP_INST_SPEC),
    INST_FASTORE(FASTORE, ZERO_OP_INST_SPEC), INST_FCMPG(FCMPG, ZERO_OP_INST_SPEC), INST_FCMPL(FCMPL, ZERO_OP_INST_SPEC),
    INST_FCONST_0(FCONST_0, ZERO_OP_INST_SPEC), INST_FCONST_1(FCONST_1, ZERO_OP_INST_SPEC), INST_FCONST_2(FCONST_2,
        ZERO_OP_INST_SPEC), INST_FDIV(FDIV, ZERO_OP_INST_SPEC), INST_FLOAD(FLOAD, VAR_INST_SPEC), INST_FMUL(FMUL,
        ZERO_OP_INST_SPEC), INST_FNEG(FNEG, ZERO_OP_INST_SPEC), INST_FREM(FREM, ZERO_OP_INST_SPEC),
    INST_FRETURN(FRETURN, ZERO_OP_INST_SPEC), INST_FSTORE(FSTORE, VAR_INST_SPEC), INST_FSUB(FSUB, ZERO_OP_INST_SPEC),
    INST_GETFIELD(GETFIELD, FIELD_INST_SPEC), INST_GETSTATIC(GETSTATIC, FIELD_INST_SPEC), INST_GOTO(GOTO, JMP_INST_SPEC),
    INST_I2B(I2B, ZERO_OP_INST_SPEC), INST_I2C(I2C, ZERO_OP_INST_SPEC), INST_I2D(I2D, ZERO_OP_INST_SPEC),
    INST_I2F(I2F, ZERO_OP_INST_SPEC), INST_I2L(I2L, ZERO_OP_INST_SPEC), INST_I2S(I2S, ZERO_OP_INST_SPEC),
    INST_IADD(IADD, ZERO_OP_INST_SPEC), INST_IALOAD(IALOAD, ZERO_OP_INST_SPEC), INST_IAND(IAND, ZERO_OP_INST_SPEC),
    INST_IASTORE(IASTORE, ZERO_OP_INST_SPEC), INST_ICONST_0(ICONST_0, ZERO_OP_INST_SPEC), INST_ICONST_1(ICONST_1,
        ZERO_OP_INST_SPEC), INST_ICONST_2(ICONST_2, ZERO_OP_INST_SPEC), INST_ICONST_3(ICONST_3, ZERO_OP_INST_SPEC),
    INST_ICONST_4(ICONST_4, ZERO_OP_INST_SPEC), INST_ICONST_5(ICONST_5, ZERO_OP_INST_SPEC), INST_ICONST_M1(ICONST_M1,
        ZERO_OP_INST_SPEC), INST_IDIV(IDIV, ZERO_OP_INST_SPEC), INST_IF_ACMPEQ(IF_ACMPEQ, JMP_INST_SPEC),
    INST_IF_ACMPNE(IF_ACMPNE, JMP_INST_SPEC), INST_IF_ICMPEQ(IF_ICMPEQ, JMP_INST_SPEC), INST_IF_ICMPGE(IF_ICMPGE,
        JMP_INST_SPEC), INST_IF_ICMPGT(IF_ICMPGT, JMP_INST_SPEC), INST_IF_ICMPLE(IF_ICMPLE, JMP_INST_SPEC),
    INST_IF_ICMPLT(IF_ICMPLT, JMP_INST_SPEC), INST_IF_ICMPNE(IF_ICMPNE, JMP_INST_SPEC), INST_IFEQ(IFEQ,
        JMP_INST_SPEC), INST_IFGE(IFGE, JMP_INST_SPEC), INST_IFGT(IFGT, JMP_INST_SPEC), INST_IFLE(IFLE,
        JMP_INST_SPEC), INST_IFLT(IFLT, JMP_INST_SPEC), INST_IFNE(IFNE, JMP_INST_SPEC), INST_IFNONNULL(IFNONNULL,
        JMP_INST_SPEC), INST_IFNULL(IFNULL, JMP_INST_SPEC), INST_IINC(IINC, IINC_INST_SPEC),
    INST_ILOAD(ILOAD, VAR_INST_SPEC), INST_IMUL(IMUL, ZERO_OP_INST_SPEC), INST_INEG(INEG, ZERO_OP_INST_SPEC),
    INST_INSTANCEOF(INSTANCEOF, TYPE_INST_SPEC), INST_INVOKEDYNAMIC(INVOKEDYNAMIC, INVOKE_DYN_INST_SPEC),
    INST_INVOKEINTERFACE(INVOKEINTERFACE, METHOD_INST_SPEC), INST_INVOKESPECIAL(INVOKESPECIAL, METHOD_INST_SPEC),
    INST_INVOKESTATIC(INVOKESTATIC, METHOD_INST_SPEC), INST_INVOKEVIRTUAL(INVOKEVIRTUAL, METHOD_INST_SPEC),
    INST_IOR(IOR, ZERO_OP_INST_SPEC), INST_IREM(IREM, ZERO_OP_INST_SPEC), INST_IRETURN(IRETURN, ZERO_OP_INST_SPEC),
    INST_ISHL(ISHL, ZERO_OP_INST_SPEC), INST_ISHR(ISHR, ZERO_OP_INST_SPEC), INST_ISTORE(ISTORE, VAR_INST_SPEC),
    INST_ISUB(ISUB, ZERO_OP_INST_SPEC), INST_IUSHR(IUSHR, ZERO_OP_INST_SPEC), INST_IXOR(IXOR, ZERO_OP_INST_SPEC),
    INST_L2D(L2D, ZERO_OP_INST_SPEC), INST_L2F(L2F, ZERO_OP_INST_SPEC), INST_L2I(L2I, ZERO_OP_INST_SPEC), INST_LADD
        (LADD, ZERO_OP_INST_SPEC), INST_LALOAD(LALOAD, ZERO_OP_INST_SPEC), INST_LAND(LAND, ZERO_OP_INST_SPEC),
    INST_LASTORE(LASTORE, ZERO_OP_INST_SPEC), INST_LCMP(LCMP, ZERO_OP_INST_SPEC), INST_LCONST_0(LCONST_0,
        ZERO_OP_INST_SPEC), INST_LCONST_1(LCONST_1, ZERO_OP_INST_SPEC), INST_LDC(LDC, LDC_INST_SPEC),
    INST_LDIV(LDIV, ZERO_OP_INST_SPEC), INST_LLOAD(LLOAD, VAR_INST_SPEC), INST_LMUL(LMUL, ZERO_OP_INST_SPEC),
    INST_LNEG(LNEG, ZERO_OP_INST_SPEC), INST_LOOKUPSWITCH(LOOKUPSWITCH, LOOKUP_SWITCH_INST_SPEC),
    INST_LOR(LOR, ZERO_OP_INST_SPEC), INST_LREM(LREM, ZERO_OP_INST_SPEC), INST_LRETURN(LRETURN, ZERO_OP_INST_SPEC),
    INST_LSHL(LSHL, ZERO_OP_INST_SPEC), INST_LSHR(LSHR, ZERO_OP_INST_SPEC), INST_LSTORE(LSTORE, VAR_INST_SPEC),
    INST_LSUB(LSUB, ZERO_OP_INST_SPEC), INST_LUSHR(LUSHR, ZERO_OP_INST_SPEC), INST_LXOR(LXOR, ZERO_OP_INST_SPEC),
    INST_MONITORENTER(MONITORENTER, ZERO_OP_INST_SPEC), INST_MONITOREXIT(MONITOREXIT, ZERO_OP_INST_SPEC),
    INST_MULTIANEWARRAY(MULTIANEWARRAY, MULTIANEW_INST_SPEC), INST_NEW(NEW, TYPE_INST_SPEC), INST_NEWARRAY(NEWARRAY,
        INT_NEWARRAY_INST_SPEC), INST_NOP(NOP, ZERO_OP_INST_SPEC), INST_POP(POP, ZERO_OP_INST_SPEC), INST_POP2(POP2,
        ZERO_OP_INST_SPEC), INST_PUTFIELD(PUTFIELD, FIELD_INST_SPEC), INST_PUTSTATIC(PUTSTATIC, FIELD_INST_SPEC),
    INST_RETURN(RETURN, ZERO_OP_INST_SPEC), INST_SALOAD(SALOAD, ZERO_OP_INST_SPEC),
    INST_SASTORE(SASTORE, ZERO_OP_INST_SPEC), INST_SIPUSH(SIPUSH, INT_INST_SPEC), INST_SWAP(SWAP, ZERO_OP_INST_SPEC),
    INST_TABLESWITCH(TABLESWITCH, TABLE_SWITCH_INST_SPEC);

    //Not suppported: INST_RET(RET, VAR_INST_SPEC), INST_JSR(JSR, JMP_INST_SPEC)
    private static final HashMap<String, InstOpcodes> nameMappings;
    private static final InstOpcodes[] intOpcodes;
    private static final EnumSet<InstOpcodes> staticOps;

    static
    {
        nameMappings = new HashMap<>();
        intOpcodes = new InstOpcodes[200];
        for (InstOpcodes opcode : InstOpcodes.values())
        {
            nameMappings.put(opcode.getInstName(), opcode);
            intOpcodes[opcode.getNumber()] = opcode;
        }

        staticOps = EnumSet.of(INST_GETSTATIC, INST_PUTSTATIC, INST_INVOKESTATIC);
    }

    /**
     * Finds the opcode corresponding to the particular opcode number.
     * @param opcode the opcode number.
     * @return the corresponding opcode.
     */
    public static InstOpcodes fromNumber(int opcode)
    {
        if (opcode < 0 || opcode >= intOpcodes.length || intOpcodes[opcode] == null)
            throw new IllegalArgumentException();
        return intOpcodes[opcode];
    }

    /**
     * Fetches the corresponding opcode from the instruction name.
     * @param instName the instruction name to lookup
     * @return the corresponding opcode, or null if instruction name does not exist.
     */
    public static InstOpcodes fetchOpcode(String instName)
    {
        return nameMappings.get(instName.toUpperCase());
    }

    private final int opcode;
    private final InstSpec spec;

    /**
     * Creates a InstOpcode enum
     * @param opcode the opcode this InstOpcode refers to.
     * @param spec the instruction spec associated with opcode.
     */
    InstOpcodes(int opcode, InstSpec spec)
    {
        this.opcode = opcode;
        this.spec = spec;
    }

    public boolean isStatic()
    {
        return staticOps.contains(this);
    }

    /**
     * Obtains the instruction name (in all caps)
     * @return the instruction name.
     */
    public String getInstName()
    {
        return name().substring(5);
    }

    public int getNumber()
    {
        return opcode;
    }

    public InstSpec getInstSpec()
    {
        return spec;
    }
}
