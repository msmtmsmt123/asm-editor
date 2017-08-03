package com.theKidOfArcrania.asm.editor.code.parsing.inst;

import com.theKidOfArcrania.asm.editor.code.parsing.ParamType;
import com.theKidOfArcrania.asm.editor.context.ClassContext;
import com.theKidOfArcrania.asm.editor.context.TypeSignature;
import com.theKidOfArcrania.asm.editor.context.TypeSort;
import com.theKidOfArcrania.asm.editor.code.parsing.CodeTokenReader;
import com.theKidOfArcrania.asm.editor.code.parsing.TokenType;

import static com.theKidOfArcrania.asm.editor.code.parsing.Range.*;

/**
 * Represents the basic parameter types
 * @author Henry
 */
public enum BasicParamType implements ParamType
{
    INTEGER("integer number", TokenType.INTEGER),
    LONG("long integer number", TokenType.LONG),
    FLOAT("floating number", TokenType.FLOAT),
    DOUBLE("double-precision floating number", TokenType.DOUBLE),
    STRING("string", TokenType.STRING){
        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            return reader.getTokenValue() != null;
        }
    }, METHOD_SIGNATURE("method signature", TokenType.TYPE_SIGNATURE) {
        @Override
        public boolean matches(CodeTokenReader reader)
        {
            return super.matches(reader) && reader.getToken().contains("(");
        }

        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            return super.matches(reader) && validTypeSig(reader, true);
        }
    }, FIELD_SIGNATURE("type signature", TokenType.TYPE_SIGNATURE) {
        @Override
        public boolean matches(CodeTokenReader reader)
        {
            return super.matches(reader) && !reader.getToken().contains("(");
        }

        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            return super.matches(reader) && validTypeSig(reader, false);
        }
    }, ARRAY_SIGNATURE("array descriptor", TokenType.TYPE_SIGNATURE) {
        @Override
        public boolean matches(CodeTokenReader reader)
        {
            return super.matches(reader) && !reader.getToken().contains("(") && reader.getToken().contains("[");
        }

        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            if (!super.matches(reader))
                return false;

            String token = reader.getToken();
            TypeSignature sig = TypeSignature.parseTypeSig(token);
            if (sig == null)
            {
                reader.error("Cannot resolve type signature '" + token + "'.", reader.getTokenPos());
                return false;
            }

            if (sig.getSort() != TypeSort.ARRAY)
            {
                reader.errorExpected(getName());
                return false;
            }
            return true;
        }
    }, CLASS_NAME("class identifier", TokenType.IDENTIFIER) {
        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            if (!super.checkToken(reader))
                return false;
            String className = reader.getToken();
            return ClassContext.verifyClassNameFormat(className);
        }
    }, IDENTIFIER("identifier", TokenType.IDENTIFIER) {
        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            if (!super.checkToken(reader))
                return false;
            String token = reader.getToken();

            //Let the special method initializer slide by because we don't know if this is a method or field identifier.
            if (token.equals("<init>") || token.equals("<clinit>"))
                return true;

            for (int i = 0; i < token.length(); i++)
            {
                if (i == 0 ? !Character.isJavaIdentifierStart(token.charAt(i)) : !Character.isJavaIdentifierPart(token
                    .charAt(i)))
                {
                    reader.error("Illegal character.", characterRange(reader.getLineNumber(), reader
                            .getTokenStartIndex() + i));
                    return false;
                }
            }
            return true;
        }
    }, METHOD_HANDLE("handle", TokenType.HANDLE) {
        @Override
        public boolean checkToken(CodeTokenReader reader)
        {
            if (!super.checkToken(reader))
                return false;
            String token = reader.getToken();
            for (int i = 1; i < token.length(); i++)
            {
                if (i == 1 ? !Character.isJavaIdentifierStart(token.charAt(i)) : !Character.isJavaIdentifierPart(token
                        .charAt(i)))
                {
                    reader.error("Illegal character.", characterRange(reader.getLineNumber(), reader
                            .getTokenStartIndex() + i));
                    return false;
                }
            }
            return true;
        }
    };

    /**
     * Determines whether if this token contains a valid signature (method or field).
     * @param reader the token-reader
     * @param methodType whether to expect a method or field signature.
     * @return true if valid, false if invalid
     */
    private static boolean validTypeSig(CodeTokenReader reader, boolean methodType)
    {
        String token = reader.getToken();
        if (TypeSignature.parseTypeSig(token) == null)
        {
            reader.error("Cannot resolve type signature '" + token + "'.", reader.getTokenPos());
            return false;
        }

        boolean methodSig = token.contains("(");
        if (methodType && !methodSig)
            reader.errorExpected("method signature");
        else if (!methodType && methodSig)
            reader.errorExpected("type signature");
        else
            return true;
        return false;
    }

    private final String name;
    private final TokenType reqTokenType;

    /**
     * Constructs a basic param type.
     * @param name the extended user-friendly name.
     * @param reqTokenType the required token type for parameter.
     */
    BasicParamType(String name, TokenType reqTokenType)
    {
        this.name = name;
        this.reqTokenType = reqTokenType;
    }


    @Override
    public boolean matches(CodeTokenReader reader)
    {
        return reader.getTokenType() == reqTokenType;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
