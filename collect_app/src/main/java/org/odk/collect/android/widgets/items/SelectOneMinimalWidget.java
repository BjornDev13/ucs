package org.odk.collect.android.widgets.items;

import static org.odk.collect.android.formentry.media.FormMediaUtils.getPlayColor;

import android.annotation.SuppressLint;
import android.content.Context;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormEntryActivity;
import org.odk.collect.android.formentry.questions.QuestionDetails;
import org.odk.collect.android.fragments.dialogs.SelectMinimalDialog;
import org.odk.collect.android.fragments.dialogs.SelectOneMinimalDialog;
import org.odk.collect.android.listeners.AdvanceToNextListener;
import org.odk.collect.android.utilities.Appearances;
import org.odk.collect.android.utilities.HtmlUtils;
import org.odk.collect.android.utilities.SelectOneWidgetUtils;
import org.odk.collect.android.widgets.utilities.WaitingForDataRegistry;
import org.odk.collect.androidshared.ui.DialogFragmentUtils;

import java.util.List;

@SuppressLint("ViewConstructor")
public class SelectOneMinimalWidget extends SelectMinimalWidget {
    private Selection selectedItem;
    private final boolean autoAdvance;
    private AdvanceToNextListener autoAdvanceListener;

    public SelectOneMinimalWidget(Context context, QuestionDetails prompt, boolean autoAdvance, WaitingForDataRegistry waitingForDataRegistry) {
        super(context, prompt, waitingForDataRegistry);
        selectedItem = SelectOneWidgetUtils.getSelectedItem(prompt.getPrompt(), items);
        this.autoAdvance = autoAdvance;
        if (context instanceof AdvanceToNextListener) {
            autoAdvanceListener = (AdvanceToNextListener) context;
        }
        updateAnswer();
    }

    @Override
    protected void showDialog() {
        int numColumns = Appearances.getNumberOfColumns(getFormEntryPrompt(), screenUtils);
        boolean noButtonsMode = Appearances.isCompactAppearance(getFormEntryPrompt()) || Appearances.isNoButtonsAppearance(getFormEntryPrompt());

        SelectOneMinimalDialog dialog = new SelectOneMinimalDialog(getSavedSelectedValue(),
                Appearances.isFlexAppearance(getFormEntryPrompt()),
                Appearances.isAutocomplete(getFormEntryPrompt()), getContext(), items,
                getFormEntryPrompt(), getReferenceManager(),
                getPlayColor(getFormEntryPrompt(), themeUtils), numColumns, noButtonsMode, mediaUtils);

        DialogFragmentUtils.showIfNotShowing(dialog, SelectMinimalDialog.class, ((FormEntryActivity) getContext()).getSupportFragmentManager());
    }

    @Override
    public IAnswerData getAnswer() {
        return selectedItem == null
                ? null
                : new SelectOneData(selectedItem);
    }

    @Override
    public void clearAnswer() {
        selectedItem = null;
        super.clearAnswer();
    }

    @Override
    public void setData(Object answer) {
        List<Selection> answers = (List<Selection>) answer;
        selectedItem = answers.isEmpty() ? null : answers.get(0);
        updateAnswer();
        widgetValueChanged();

        if (autoAdvance && autoAdvanceListener != null) {
            autoAdvanceListener.advance();
        }
    }

    @Override
    public void setChoiceSelected(int choiceIndex, boolean isSelected) {
        selectedItem = isSelected
                ? items.get(choiceIndex).selection()
                : null;
    }

    private void updateAnswer() {
        if (selectedItem == null) {
            binding.answer.setText(R.string.select_answer);
        } else {
            binding.answer.setText(HtmlUtils.textToHtml(getFormEntryPrompt().getSelectItemText(selectedItem)));
        }
    }

    private String getSavedSelectedValue() {
        return selectedItem == null
                ? null
                : selectedItem.getValue();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
    }
}
