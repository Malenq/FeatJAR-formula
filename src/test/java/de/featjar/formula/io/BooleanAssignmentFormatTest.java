package de.featjar.formula.io;

import de.featjar.base.FeatJAR;
import de.featjar.formula.VariableMap;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.helper.BooleanAssignmentHelper;
import de.featjar.formula.io.csv.BooleanAssignmentListCSVFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BooleanAssignmentFormatTest {

    @BeforeAll
    public static void printVariableMap() {
        FeatJAR.testConfiguration().initialize();

        List<String> variableNames = Arrays.asList("A", "B");
        VariableMap variableMap = new VariableMap(variableNames);

        variableMap.getVariableNames().forEach(key -> {
            variableMap.get(key).ifPresent(value -> {
                FeatJAR.log().info(value);
            });
        });
    }

    @Test
    public void printBooleanAssignmentList() {
        String expectedResult = buildExpectedResult(
                "Configuration;First;Second;Third\n",
                "0;+;-;0\n",
                "1;-;0;+\n",
                "2;+;+;0\n"
        );

        BooleanAssignmentList format = new BooleanAssignmentHelper(Arrays.asList("First", "Second", "Third"))
                .insertValues(true, false, null)
                .insertValues(false, null, true)
                .insertValues(true, true, null)
                .build();

        BooleanAssignmentListCSVFormat booleanAssignmentListCSVFormat = new BooleanAssignmentListCSVFormat();
        booleanAssignmentListCSVFormat.serialize(format).ifPresent(result -> {
            FeatJAR.log().info(result);
            Assertions.assertEquals(expectedResult, result);
        });
    }

    private String buildExpectedResult(String... values) {
        StringBuilder result = new StringBuilder();
        for (String value : values) {
            result.append(value);
        }
        return result.toString();
    }

}
