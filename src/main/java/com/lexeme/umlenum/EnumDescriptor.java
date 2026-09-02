package com.lexeme.umlenum;

import java.util.ArrayList;
import java.util.List;

public class EnumDescriptor {
    public String name;
    public List<String> enumerators = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("enum ");
        stringBuilder.append(name);
        stringBuilder.append(" <<enumeration>> {\n");

        for (String enumerator : enumerators) {
            stringBuilder.append(enumerator);
            stringBuilder.append("\n");
        }

        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }
}
