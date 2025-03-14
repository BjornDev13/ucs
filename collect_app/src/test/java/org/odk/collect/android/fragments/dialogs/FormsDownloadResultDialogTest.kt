package org.odk.collect.android.fragments.dialogs

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.odk.collect.android.R
import org.odk.collect.android.application.Collect
import org.odk.collect.android.formmanagement.FormDownloadException
import org.odk.collect.android.formmanagement.ServerFormDetails
import org.odk.collect.fragmentstest.DialogFragmentTest.onViewInDialog
import org.odk.collect.fragmentstest.FragmentScenarioLauncherRule
import org.odk.collect.testshared.RobolectricHelpers
import org.robolectric.Shadows

@RunWith(AndroidJUnit4::class)
class FormsDownloadResultDialogTest {
    private val resultItem = ServerFormDetails("Form 1", "", "1", "1", "", false, true, null)
    val listener = mock<FormsDownloadResultDialog.FormDownloadResultDialogListener>()

    @get:Rule
    val launcherRule = FragmentScenarioLauncherRule()

    @Test
    fun `The dialog should be dismissed after clicking out of it's area or on device back button`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, FormDownloadException>()
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            assertThat(it.isCancelable, `is`(true))
        }
    }

    @Test
    fun `The title of the 'POSITIVE BUTTON' should be 'OK'`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, FormDownloadException>()
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).text,
                `is`(ApplicationProvider.getApplicationContext<Collect>().getString(R.string.ok))
            )
        }
    }

    @Test
    fun `The dialog should be dismissed after clicking on the 'POSITIVE BUTTON'`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, FormDownloadException>()
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            it.listener = listener
            assertThat(it.dialog!!.isShowing, `is`(true))
            (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).performClick()
            RobolectricHelpers.runLooper()
            assertThat(it.dialog, `is`(nullValue()))
        }
    }

    @Test
    fun `onCloseDownloadingResult() should be called after clicking on the 'POSITIVE BUTTON'`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, FormDownloadException>()
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            it.listener = listener
            (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).performClick()
            RobolectricHelpers.runLooper()
            verify(listener).onCloseDownloadingResult()
        }
    }

    @Test
    fun `if there are errors onCloseDownloadingResult() should be called after clicking on the 'NEGATIVE BUTTON'`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf(resultItem to FormDownloadException.InvalidSubmission())
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            it.listener = listener
            (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).performClick()
            RobolectricHelpers.runLooper()
            verify(listener).onCloseDownloadingResult()
        }
    }

    @Test
    fun `If there are no errors an appropriate message should be displayed`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, String>()
        )

        launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        onViewInDialog(withText(R.string.all_downloads_succeeded)).check(matches(isDisplayed()))
    }

    @Test
    fun `If there are no errors 'SHOW DETAILS' button should be hidden`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, String>()
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).visibility,
                `is`(View.GONE)
            )
        }
    }

    @Test
    fun `If there are errors an appropriate message should be displayed`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf(resultItem to FormDownloadException.InvalidSubmission())
        )

        launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        onViewInDialog(
            withText(
                ApplicationProvider.getApplicationContext<Collect>()
                    .getString(R.string.some_downloads_failed, "1", "1")
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun `If there are errors 'SHOW DETAILS' button should be displayed`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf(resultItem to FormDownloadException.InvalidSubmission())
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).visibility,
                `is`(View.VISIBLE)
            )
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).text,
                `is`(
                    ApplicationProvider.getApplicationContext<Collect>()
                        .getString(R.string.show_details)
                )
            )
        }
    }

    @Test
    fun `The dialog should be dismissed after clicking on the 'NEGATIVE BUTTON'`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf<ServerFormDetails, String>()
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            it.listener = listener
            assertThat(it.dialog!!.isShowing, `is`(true))
            (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).performClick()
            RobolectricHelpers.runLooper()
            assertThat(it.dialog, `is`(nullValue()))
        }
    }

    @Test
    fun `Recreation should not change the state of dialog`() {
        val args = Bundle()
        args.putSerializable(
            FormsDownloadResultDialog.ARG_RESULT,
            hashMapOf(resultItem to FormDownloadException.InvalidSubmission())
        )

        val scenario =
            launcherRule.launchDialogFragment(FormsDownloadResultDialog::class.java, args)
        scenario.onFragment {
            assertThat(Shadows.shadowOf(it.dialog).title, `is`(""))
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).text,
                `is`(ApplicationProvider.getApplicationContext<Collect>().getString(R.string.ok))
            )
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).text,
                `is`(
                    ApplicationProvider.getApplicationContext<Collect>()
                        .getString(R.string.show_details)
                )
            )
        }

        scenario.recreate()

        scenario.onFragment {
            assertThat(Shadows.shadowOf(it.dialog).title, `is`(""))
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).text,
                `is`(ApplicationProvider.getApplicationContext<Collect>().getString(R.string.ok))
            )
            assertThat(
                (it.dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).text,
                `is`(
                    ApplicationProvider.getApplicationContext<Collect>()
                        .getString(R.string.show_details)
                )
            )
        }
    }
}
