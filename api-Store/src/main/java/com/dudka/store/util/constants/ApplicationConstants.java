package com.dudka.store.util.constants;


import lombok.experimental.UtilityClass;

/**
 * Contains various constants used in the messenger application.
 */
@UtilityClass
public final class ApplicationConstants {

    /**
     * Inner utility class for web-related constants.
     */
    @UtilityClass
    public static final class Web {

        /**
         * Inner utility class for dto validation.
         */
        @UtilityClass
        public static final class DataValidation {

            public final int MIN_SIZE_OF_EMAIL = 4;
            public final int MAX_SIZE_OF_EMAIL = 100;
            public final int MIN_SIZE_OF_SURNAME = 1;
            public final int MAX_SIZE_OF_FIRSTNAME = 255;
            public final int MIN_SIZE_OF_FIRSTNAME = 1;
            public final int MAX_SIZE_OF_SURNAME = 255;

            public final int MIN_SIZE_OF_PHONE_NUMBER = 4;

            public final int MAX_SIZE_OF_PHONE_NUMBER = 15;

        }
    }
}
