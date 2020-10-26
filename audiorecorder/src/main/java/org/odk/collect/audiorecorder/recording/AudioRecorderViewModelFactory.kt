package org.odk.collect.audiorecorder.recording

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.odk.collect.audiorecorder.getComponent
import org.odk.collect.audiorecorder.recording.internal.RealAudioRecorderViewModel
import org.odk.collect.audiorecorder.recording.internal.RecordingSession
import javax.inject.Inject

class AudioRecorderViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Inject
    internal lateinit var recordingSession: RecordingSession

    init {
        application.getComponent().inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RealAudioRecorderViewModel(application, recordingSession) as T
    }
}
