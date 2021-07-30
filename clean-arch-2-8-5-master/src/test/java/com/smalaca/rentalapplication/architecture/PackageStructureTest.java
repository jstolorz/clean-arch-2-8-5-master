package com.smalaca.rentalapplication.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class PackageStructureTest {

    @Test
    void domainShouldTalkOnlyWithDomain(){
        final JavaClasses javaClasses = RentalApplicationClasses.get();

        ArchRuleDefinition.classes()
                .that().resideInAnyPackage("..domain..")
                .should().onlyAccessClassesThat().resideInAnyPackage("..domain..","java..")
                .check(javaClasses);

    }

}
