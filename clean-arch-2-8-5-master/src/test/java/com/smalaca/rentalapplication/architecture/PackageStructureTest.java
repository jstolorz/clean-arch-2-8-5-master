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

    @Test
    void applicationShouldTalkOnlyWithDomainAndApplication(){
        final JavaClasses javaClasses = RentalApplicationClasses.get();

        ArchRuleDefinition.classes()
                .that().resideInAnyPackage("..application..")
                .should().onlyAccessClassesThat().resideInAnyPackage("..domain..","..application..","java..")
                .check(javaClasses);
    }

    @Test
    void queryShouldTalkOnlyWithQuery(){
        final JavaClasses javaClasses = RentalApplicationClasses.get();

        ArchRuleDefinition.classes()
                .that().resideInAnyPackage("..query..")
                .should().onlyAccessClassesThat().resideInAnyPackage("..query..","java..")
                .check(javaClasses);
    }

    @Test
    void  infrastructureShouldTalkOnlyWithInfrastructure(){
        final JavaClasses javaClasses = RentalApplicationClasses.get();

        ArchRuleDefinition.classes()
                .that().resideInAnyPackage("..infrastructure..")
                .should().onlyAccessClassesThat().resideOutsideOfPackage("..domain..")
                .orShould().haveSimpleNameContaining("Repository")
                .check(javaClasses);

    }


}
