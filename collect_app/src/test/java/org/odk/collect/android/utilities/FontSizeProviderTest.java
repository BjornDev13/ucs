package org.odk.collect.android.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odk.collect.android.preferences.GeneralSharedPreferences;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.odk.collect.android.preferences.GeneralKeys.KEY_FONT_SIZE;

@RunWith(RobolectricTestRunner.class)
public class FontSizeProviderTest {

    @Test
    public void whenFontSizeNotSpecified_shouldReturnDefaultValue() {
        assertThat(FontSizeProvider.getQuestionFontSize(), is(FontSizeProvider.DEFAULT_FONT_SIZE));
    }

    @Test
    public void whenFontSizeSpecified_shouldReturnSelectedValue() {
        GeneralSharedPreferences.getInstance().save(KEY_FONT_SIZE, "30");
        assertThat(FontSizeProvider.getQuestionFontSize(), is(30));
    }
}
