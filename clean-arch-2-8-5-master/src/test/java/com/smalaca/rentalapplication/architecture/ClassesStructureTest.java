package com.smalaca.rentalapplication.architecture;

import com.tngtech.archunit.library.GeneralCodingRules;
import org.junit.jupiter.api.Test;

class ClassesStructureTest {

    @Test
    void shouldHaveNoClassThatUsesFieldInjection(){
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
                .check(RentalApplicationClasses.get());
    }

    @Test
    void shouldHaveNoClassThatThrowsGenericException(){
        GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
                .check(RentalApplicationClasses.get());
    }

}
