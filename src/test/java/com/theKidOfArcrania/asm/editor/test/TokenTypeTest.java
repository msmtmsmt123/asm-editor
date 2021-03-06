package com.theKidOfArcrania.asm.editor.test;


import com.theKidOfArcrania.asm.editor.code.parsing.*;
import com.theKidOfArcrania.asm.editor.context.ClassContext;
import com.theKidOfArcrania.asm.editor.context.MethodContext;
import com.theKidOfArcrania.asm.editor.context.TypeSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Modifier;

import static com.theKidOfArcrania.asm.editor.code.parsing.TokenType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings({"JavaDoc", "MagicNumber"})
@RunWith(Parameterized.class)
public class TokenTypeTest
{
    private static ClassContext classContext;
    private static MethodContext mthContext;

    static
    {
        classContext = ClassContext.findContext("TestClass");
        if (classContext == null)
        {
            classContext = ClassContext.createContext("TestClass", false);
            mthContext = classContext.addMethod(Modifier.PUBLIC, "TestMethod", TypeSignature.parseTypeSig("()V"));
        }
        else
            mthContext = classContext.findMethod("TestMethod", TypeSignature.parseTypeSig("()V"), false);
    }

    @Parameterized.Parameters(name="{0}")
    public static Object[][] params()
    {
        String[] tokens = {"1234", "5678L", "9012.23F", "324.643D", "324.4", "  \"Text_\\nhere\"", "Identifier",
                "@(BB)" + "V", "&h4ndle$"};
        TokenType[] types = {INTEGER, LONG, FLOAT, DOUBLE, DOUBLE, STRING, IDENTIFIER, TYPE_SIGNATURE, HANDLE};
        Object[] vals = {1234, 5678L, 9012.23F, 324.643, 324.4, "Text_\nhere", "Identifier", "(BB)V", "h4ndle$"};

        Object[][] params = new Object[vals.length][];
        for (int i = 0; i < params.length; i++)
            params[i] = new Object[] {tokens[i], types[i], vals[i]};
        return params;
    }

    public static CodeTokenReader initReader(CodeSymbols globalSymbols, String token)
    {
        CodeTokenReader reader = new CodeTokenReader(globalSymbols, mthContext, token);
        reader.nextLine();
        reader.addErrorLogger(new ErrorLogger()
        {
            @Override
            public void logError(String description, Range highlight)
            {
                fail(description);
            }

            @Override
            public void logWarning(String description, Range highlight)
            {
                fail(description);
            }
        });
        return reader;
    }

    private CodeTokenReader reader;
    private String token;
    private TokenType type;
    private Object value;

    public TokenTypeTest(String token, TokenType type, Object value)
    {
        this.token = token;
        this.type = type;
        this.value = value;
    }

    @Before
    public void setup()
    {
        CodeSymbols globalSymbols = new CodeSymbols(null, classContext);
        reader = initReader(globalSymbols, token);
        reader.nextToken();
    }

    @Test
    public void testType()
    {
        assertEquals("Not same types", type, reader.getTokenType());
    }

    @Test
    public void testValue()
    {
        assertEquals("Not same value", value, reader.getTokenValue());
    }



}
