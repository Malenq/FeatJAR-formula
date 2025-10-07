package de.featjar.formula.helper;

import de.featjar.formula.VariableMap;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BooleanAssignmentHelper {

    private final VariableMap variableMap;
    private final BooleanAssignmentList booleanAssignmentList;

    public BooleanAssignmentHelper(Collection<String> values) {
        this.variableMap = new VariableMap(values);
        this.booleanAssignmentList = new BooleanAssignmentList(variableMap);
    }

    public BooleanAssignmentHelper(VariableMap variableMap) {
        this.variableMap = variableMap;
        this.booleanAssignmentList = new BooleanAssignmentList(variableMap);
    }

    private boolean isValid() {
        for (String key : variableMap.getVariableNames()) {
            if (key.contains(";")) {
                throw new IllegalArgumentException(key + " contains invalid characters");
            }
        }
        return true;
    }

    public BooleanAssignmentHelper insertValues(Object... objects) {
        List<Integer> values = new ArrayList<>();
        int pos = 0;
        for (String keys : variableMap.getVariableNames()) {
            final int currentId = pos;
            variableMap.get(keys).ifPresent(value -> {
                Object toTransform = objects[currentId];
                if (toTransform instanceof Boolean) {
                    Boolean bool = (Boolean) toTransform;
                    values.add(bool ? value : -value);
                }
                if (toTransform == null) {
                    values.add(0);
                }
            });
            pos++;
        }
        booleanAssignmentList.add(new BooleanAssignment(values));
        return this;
    }

    public BooleanAssignmentList build() {
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid booleanAssignmentList format");
        }
        return booleanAssignmentList;
    }

}
