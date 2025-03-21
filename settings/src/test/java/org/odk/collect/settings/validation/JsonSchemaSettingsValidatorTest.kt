package org.odk.collect.settings.validation

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class JsonSchemaSettingsValidatorTest {
    @Test
    fun `returns true when json is valid based on schema`() {
        val validator = JsonSchemaSettingsValidator {
            SCHEMA.byteInputStream()
        }

        assertThat(
            validator.isValid(
                """
                {
                    "foo": "option1"
                }
                """
            ),
            equalTo(true)
        )
    }

    @Test
    fun `returns false when json is invalid based on schema`() {
        val validator = JsonSchemaSettingsValidator {
            SCHEMA.byteInputStream()
        }

        assertThat(
            validator.isValid(
                """
                {
                    "foo": false
                }
                """
            ),
            equalTo(false)
        )
    }

    @Test
    fun `returns false when json is invalid`() {
        val validator = JsonSchemaSettingsValidator {
            SCHEMA.byteInputStream()
        }

        assertThat(
            validator.isValid("*"),
            equalTo(false)
        )
    }

    @Test
    fun `returns true when json contains values different than those specified in a corresponding enum`() {
        val validator = JsonSchemaSettingsValidator {
            SCHEMA.byteInputStream()
        }

        assertThat(
            validator.isValid(
                """
                {
                    "foo": "option3"
                }
                """
            ),
            equalTo(true)
        )
    }
}

private const val SCHEMA = """
            {
                "${'$'}schema": "https://json-schema.org/draft/2019-09/schema",
                "${'$'}id": "https://example.com/example.schema.json",
                "title": "Schema",
                "type": "object",
                "properties": {
                    "foo": {
                        "type": "string",
                        "enum": [
                            "option1",
                            "option2"
                        ]
                    }
                }
            }
            """
