package com.smalaca.rentalapplication.architecture;

import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

class DependenciesTest {

    @Test
    void shouldHaveNocycle(){
        SlicesRuleDefinition.slices()
                .matching("com.smalaca.rentalapplication(*)..")
                .should().beFreeOfCycles();
    }

}
